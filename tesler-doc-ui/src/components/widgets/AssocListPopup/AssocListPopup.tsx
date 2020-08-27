import React from 'react'
import {$do, AssocListPopup as CoreAssocListPopup, connect, Pagination} from '@tesler-ui/core'
import {PaginationMode, WidgetTableMeta} from '@tesler-ui/core/interfaces/widget'
import styles from './AssocListPopup.less'
import {Button} from 'antd'
import {AppState} from 'interfaces/storeSlices'
import {Dispatch} from 'redux'
import {BcFilter, FilterType} from '@tesler-ui/core/interfaces/filters'
import {PendingDataItem} from '@tesler-ui/core/interfaces/data'

export interface AssocListPopupActions {
    onSave: (bcName: string, bcNames: string[], isFullHierarchy: boolean) => void,
    onFilter: (bcName: string, filter: BcFilter) => void,
    onCancel: (bcName: string, isFullHierarchy: boolean) => void,
    onClose: (bcName: string, isFullHierarchy: boolean) => void
}

export interface AssocListPopupOwnProps {
    meta: WidgetTableMeta
}

export interface AssocListPopupProps extends AssocListPopupOwnProps {
    showed: boolean,
    associateFieldKey?: string,
    pendingDataChanges?: {
        [bcName: string]: {
            [cursor: string]: PendingDataItem
        }
    },
    isFilter?: boolean,
    calleeBCName?: string
}

export function AssocListPopup(props: AssocListPopupProps & AssocListPopupActions) {
    if (!props.showed) {
        return null
    }

    const isFullHierarchy = !!props.meta.options?.hierarchyFull

    const handleClose = React.useCallback(
        () => props.onClose(props.meta.bcName, isFullHierarchy),
        [props.onClose, props.meta.bcName, isFullHierarchy]
    )

    const pendingBcNames = props.meta.options?.hierarchy
        ? [props.meta.bcName, ...props.meta.options?.hierarchy.map((item: { bcName: any }) => item.bcName)]
        : [props.meta.bcName]

    const saveData = React.useCallback(() => {
        props.onSave(props.meta.bcName, pendingBcNames, isFullHierarchy)
        handleClose()
    }, [props.onSave, handleClose])

    const pendingDataValue = props.pendingDataChanges[props.meta.bcName]
    const filterData = React.useCallback(() => {
        const filterValue: string[] = []
        for (const value in pendingDataValue) {
            if (pendingDataValue[value]?.id && pendingDataValue[value]?._associate === true) {
                filterValue.push(pendingDataValue[value].id.toString())
            }
        }
        props.onFilter(props.calleeBCName, {
            type: FilterType.equalsOneOf,
            fieldName: props.associateFieldKey,
            value: filterValue
        })
        handleClose()
    }, [props.onFilter, handleClose, props.calleeBCName, props.associateFieldKey, pendingDataValue])

    const cancelData = React.useCallback(() => {
        props.onCancel(props.meta.bcName, isFullHierarchy)
        handleClose()
    }, [props.onCancel, handleClose])

    const customFooter = React.useMemo(() => {
        return <div className={styles.footerContainer}>
            <div className={styles.pagination}>
                <Pagination bcName={props.meta.bcName} mode={PaginationMode.page} widgetName={props.meta.name}/>
            </div>
            <div className={styles.actions}>
                <Button onClick={props.isFilter ? filterData : saveData} className={styles.buttonSave}>
                    Save
                </Button>
                <Button onClick={cancelData} className={styles.buttonCancel}>
                    Cancel
                </Button>
            </div>
            It's a custom footer
        </div>
    }, [props.meta.bcName, props.meta.name, props.isFilter, filterData, saveData, cancelData])
    const customComponents: {
        title?: React.ReactNode,
        table?: React.ReactNode,
        footer?: React.ReactNode
    } = {
        title: <h2>Here you can set custom title</h2>,
        table: <div><p>Here you can set custom table</p><p>{`Also you can notice, that Popup's props
         closable={false} getContainer={false} width={modalWidth} are passed to CoreAssocListPopup`}</p></div>,
        footer: customFooter
    }

    const modalWidth = 1000

    if (props.meta.name === 'assocListPopupDocCustomAssoc') {
        return <CoreAssocListPopup
            closable={false}
            getContainer={false}
            width={modalWidth}
            widget={props.meta as WidgetTableMeta}
            components={customComponents}
        />
    }
    return <CoreAssocListPopup
        widget={props.meta as WidgetTableMeta}
    />
}

function mapStateToProps(store: AppState, ownProps: AssocListPopupOwnProps) {
    const bcName = ownProps.meta.bcName
    const isFilter = store.view.popupData.isFilter
    const calleeBCName = store.view.popupData.calleeBCName
    return {
        showed: store.view.popupData.bcName === bcName,
        associateFieldKey: store.view.popupData.associateFieldKey,
        pendingDataChanges: store.view.pendingDataChanges,
        isFilter,
        calleeBCName
    }
}

function mapDispatchToProps(dispatch: Dispatch) {
    return {
        onCancel: (bcName: string, isFullHierarchy: boolean) => {
            dispatch($do.closeViewPopup({bcName}))
            if (isFullHierarchy) {
                dispatch($do.bcCancelPendingChanges({bcNames: [bcName]}))
            }
        },
        onClose: (bcName: string, isFullHierarchy: boolean) => {
            dispatch($do.closeViewPopup({bcName}))
            if (isFullHierarchy) {
                dispatch($do.bcCancelPendingChanges({bcNames: [bcName]}))
            }
        },
        onSave: (bcName: string, bcNames: string[], isFullHierarchy: boolean) => {
            dispatch($do.saveAssociations({bcNames}))
            if (isFullHierarchy) {
                dispatch($do.bcCancelPendingChanges({bcNames: [bcName]}))
            }
        },
        onFilter: (bcName: string, filter: BcFilter) => {
            dispatch($do.bcAddFilter({bcName, filter}))
            dispatch($do.bcForceUpdate({bcName}))
        },
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(AssocListPopup)
