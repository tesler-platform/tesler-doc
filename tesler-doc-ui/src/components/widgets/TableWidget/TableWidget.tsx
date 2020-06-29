import React from 'react'
import {ColumnTitle, TableWidget as CoreTableWidget} from '@tesler-ui/core'
import {WidgetTableMeta, WidgetListField} from '@tesler-ui/core/interfaces/widget'
import {RowMetaField} from '@tesler-ui/core/interfaces/rowMeta'
import {TableWidgetProps} from '@tesler-ui/core/components/widgets/TableWidget/TableWidget'
import styles from './TableWidget.less'

export interface TableWidgetOwnProps extends TableWidgetProps {
    meta: WidgetTableMeta
}
export interface DocTableWidgetProps extends TableWidgetOwnProps {}
export const TableWidget: React.FunctionComponent<DocTableWidgetProps> = props => {

    const renderDocColumnTitle = React.useCallback(
        (pr: {widgetName: string, widgetMeta: WidgetListField, rowMeta: RowMetaField}) => {
            return <div>
                <ColumnTitle
                    widgetName={pr.widgetName}
                    widgetMeta={pr.widgetMeta}
                    rowMeta={pr.rowMeta}
                />
            </div>
        },
        []
    )
    if (props.meta.name === 'tableWidgetDoc') {
        return <div className={styles.columnWrapper}>
            <CoreTableWidget
                {...props}
                columnTitleComponent={renderDocColumnTitle}
            />
        </div>
    }

    return <CoreTableWidget {...props}/>
}

export default React.memo(TableWidget)
