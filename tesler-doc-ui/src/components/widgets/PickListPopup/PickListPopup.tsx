import React from 'react'
import {PickListPopup as CorePickListPopup, buildBcUrl} from '@tesler-ui/core'
import {WidgetTableMeta} from '@tesler-ui/core/interfaces/widget'
import {connect} from '@tesler-ui/core'
import {DataItem, PickMap} from '@tesler-ui/core/interfaces/data'
import {RowMetaField} from '@tesler-ui/core/interfaces/rowMeta'
import {ChangeDataItemPayload} from '@tesler-ui/core/components/Field/Field'
import {Dispatch} from 'redux'
import {AppState} from 'interfaces/storeSlices'
import {$do} from 'actions/types'

interface PickListPopupOwnProps {
    meta: WidgetTableMeta
}
interface PickListPopupProps extends PickListPopupOwnProps {
    onChange: (payload: ChangeDataItemPayload) => void,
    onClose: (bcName: string) => void,
    data: DataItem[],
    showed: boolean,
    pickMap: PickMap,
    cursor: string,
    parentBCName: string,
    bcLoading: boolean,
    rowMetaFields: RowMetaField[]
}
export const PickListPopup: React.FunctionComponent<PickListPopupProps> = (props) => {

    const customTable = <div><h3>Here is Custom component instead Table </h3>
        <p>{`Also you can notice, that Popup's props
         closable={false} bodyStyle={modalBodyStyle} width={modalWidth} are passed to CoreAssocListPopup`}</p>
    </div>
    const customComponents: {
        title?: React.ReactNode;
        table?: React.ReactNode;
        footer?: React.ReactNode;
    } = {
        title: <h2>custom Title</h2>,
        table: customTable,
        footer: <h2>Custom foooooooooter</h2>
    }
    const modalWidth = 300
    const modalBodyStyle = {
        height: 300
    }
    if (props.meta.name === 'pickListPopupDocCustomPick') {
        return <div>
            <CorePickListPopup
                closable={false}
                components={customComponents}
                widget={props.meta}
                bodyStyle={modalBodyStyle}
                width={modalWidth}
            />
        </div>
    }

    return <CorePickListPopup
        widget={props.meta}
    />
}

function mapStateToProps(store: AppState, props: PickListPopupOwnProps) {
    const bcName = props.meta.bcName
    const bcUrl = buildBcUrl(bcName, true)
    const fields = bcUrl && store.view.rowMeta[bcName]?.[bcUrl]?.fields
    const bc = store.screen.bo.bc[bcName]
    const parentBCName = bc?.parentName
    return {
        pickMap: store.view.pickMap,
        showed: store.view.popupData.bcName === props.meta.bcName,
        data: store.data[props.meta.bcName],
        cursor: store.screen.bo.bc[parentBCName]?.cursor,
        parentBCName: bc?.parentName,
        bcLoading: bc?.loading,
        rowMetaFields: fields
    }
}
function mapDispatchToProps(dispatch: Dispatch) {
    return {
        onChange: (payload: ChangeDataItemPayload) => dispatch($do.changeDataItem(payload)),
        onClose: (bcName: string) => {
            dispatch($do.viewClearPickMap(null))
            dispatch($do.closeViewPopup({bcName}))
        }
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(PickListPopup)
