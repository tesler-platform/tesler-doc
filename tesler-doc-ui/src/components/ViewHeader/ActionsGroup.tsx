/*
 * TESLERDOC - UI
 * Copyright (C) 2020 Tesler Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react'
import {Dispatch} from 'redux'
import {AppState} from 'interfaces/storeSlices'
import {$do, connect, buildBcUrl, useWidgetOperations} from '@tesler-ui/core'
import {Button, Dropdown, Icon, Menu} from 'antd'
import {Operation, OperationGroup} from '@tesler-ui/core/interfaces/operation'
import {WidgetMeta} from '@tesler-ui/core/interfaces/widget'
import * as actionsOpenIcon from './images/new-doc.svg'
import styles from './ActionsGroup.less'

interface ActionsGroupOwnProps {
    meta: WidgetMeta
}

interface ActionsGroupProps extends ActionsGroupOwnProps {
    cursor: string,
    bcName: string
    operations: Array<Operation | OperationGroup>,
    onClick: (bcName: string, operationType: string, widgetName: string) => void,
}

export function ActionsGroup(props: ActionsGroupProps) {
    const skipGroup = props.meta.options && props.meta.options.hideActionGroups
    const operations = useWidgetOperations(props.operations, props.meta)
    const operationsGroup = operations.find(
        operation => !!(operation as OperationGroup).actions
            && (!skipGroup || !operation.type || !skipGroup.includes(operation.type))
    ) as OperationGroup

    return <div>
    {(operationsGroup) &&
            <Dropdown
                placement="bottomCenter"
                trigger={['click']}
                getPopupContainer={trigger => trigger.parentElement}
                overlay={<Menu>
                    {operationsGroup.actions.map(operation => {
                        return <Menu.Item
                            key={operation.type}
                            className={styles.subOperation}
                            onClick={() => {
                                props.onClick(props.bcName, operation.type, props.meta.name)
                            }}
                        >
                            {operation.icon && <Icon type={operation.icon} className={styles.icon} />}
                            {operation.text}
                        </Menu.Item>
                    })}
                </Menu>}
            >
                <Button className={styles.actionsOpenButton}>
                    <img className={styles.actionsOpenIcon} src={actionsOpenIcon} alt="openActions" />
                    <span>Actions</span>
                </Button>
            </Dropdown>
        }
    </div>
}

function mapStateToProps(store: AppState, ownProps: ActionsGroupOwnProps) {
    const bcName = ownProps.meta.bcName
    const bcUrl = buildBcUrl(bcName, true)
    const bc = store.screen.bo.bc[bcName]
    const bcCursor = bc && bc.cursor
    const rowMeta = bcUrl
        && store.view.rowMeta[bcName]
        && store.view.rowMeta[bcName][bcUrl]
        && store.view.rowMeta[bcName][bcUrl]
    return {
        bcName,
        operations: rowMeta && rowMeta.actions,
        cursor: bcCursor
    }
}

function mapDispatchToProps(dispatch: Dispatch) {
    return {
        onClick: (bcName: string, operationType: string, widgetName: string) => {
            dispatch($do.sendOperation({ bcName, operationType, widgetName }))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ActionsGroup)
