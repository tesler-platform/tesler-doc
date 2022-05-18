import React from 'react'
import {ColumnTitle, TableWidget as CoreTableWidget} from '@tesler-ui/core'
import {WidgetTableMeta, WidgetListField} from '@tesler-ui/core/interfaces/widget'
import {RowMetaField} from '@tesler-ui/core/interfaces/rowMeta'
import {TableWidgetProps} from '@tesler-ui/core/components/widgets/TableWidget/TableWidget'
import styles from './TableWidget.less'
import {ColumnProps} from 'antd/es/table'
import {DataItem} from '@tesler-ui/core/interfaces/data'
import {Icon} from 'antd'
import {WidgetOptions} from '@tesler-ui/core/interfaces/widget'
import { BulkTableWidget } from './BulkTableWidget'

// New `selectable` flag for widget meta description
type CustomWidgetOptions = WidgetOptions & { selectable: true }

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
    const yourColumn: ColumnProps<DataItem> = {
        title: '',
        key: '_yourColumn',
        width: '10px',
        render: (text, dataItem): React.ReactNode => {
            return <Icon type="menu" />
        }
    }

    if (props.meta.name === 'tableWidgetDoc') {
        return <div className={styles.columnWrapper}>
            <CoreTableWidget
                {...props}
                columnTitleComponent={renderDocColumnTitle}
                controlColumns={[{column: yourColumn, position: 'left'}]}
            />
        </div>
    }

    if ((props.meta.options as CustomWidgetOptions)?.selectable) {
        return <BulkTableWidget {...props} />
    }

    return <CoreTableWidget {...props} showRowActions />
}

export default React.memo(TableWidget)
