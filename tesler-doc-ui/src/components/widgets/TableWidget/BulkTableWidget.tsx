import React from 'react'
import {useDispatch, useSelector} from 'react-redux'
import {TableWidget} from '@tesler-ui/core'
import {TableWidgetProps} from '@tesler-ui/core/components/widgets/TableWidget/TableWidget'
import {$do} from '../../../actions/types'
import {AppState} from '../../../interfaces/storeSlices'
import {DataItem} from '@tesler-ui/core/interfaces/data'

// Our HOC will receive the same props as a regular `<TableWidget />`
export const BulkTableWidget: React.FC<TableWidgetProps> = (props) => {
    // Get currently selected items from the store
    const selectedItems = useSelector((state: AppState) => state.view.selectedItems)
    const dispatch = useDispatch()

    const rowReselection = React.useMemo(() => {
        return {
            // This will control checked records
            selectedRowKeys: selectedItems,
            // This callback will be called when the record is checked
            onSelect: (record: DataItem, selected: boolean) => {
                // Dispatch an action with id of selected record
                dispatch($do.selectRecord({ ids: [record.id], selected }))
            },
            onSelectAll: (selected: boolean, selectedRows: DataItem[], changedRows: DataItem[]) => {
                // Dispatch the same action for `select all` checkbox
                dispatch($do.selectRecord({ ids: changedRows.map(item => item.id), selected }))
            }
        }
    }, [selectedItems, props.meta.options])

    return <TableWidget
        {...props}
        rowSelection={rowReselection}
    />
}

export default React.memo(BulkTableWidget)
