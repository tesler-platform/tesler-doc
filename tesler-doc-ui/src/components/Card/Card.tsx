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
import {connect, buildBcUrl} from '@tesler-ui/core'
import Operations from 'components/Operations/Operations'
import {WidgetMeta, WidgetTypes} from '@tesler-ui/core/interfaces/widget'
import {Operation, OperationGroup} from '@tesler-ui/core/interfaces/operation'
import {AppState} from 'interfaces/storeSlices'
import * as styles from './Card.less'
import ReactMarkdown from 'react-markdown'
import CodeBlock from './CodeBlock'

export interface CardOwnProps {
    children: React.ReactNode,
    meta: WidgetMeta
}

export interface CardStateProps {
    operations: Array<Operation | OperationGroup>,
    viewName: string,
}

const showOperations = [WidgetTypes.List, WidgetTypes.DataGrid, WidgetTypes.Form]

export function Card(props: CardOwnProps & CardStateProps) {

    const description = props.meta.description

    return <div className={styles.container}>
        {description &&
            <div className={styles.markdown}>
                <ReactMarkdown
                    source={description}
                    escapeHtml={false}
                    renderers={{code: CodeBlock}}
                />
            </div>
        }
        <div>
            {props.meta.type === WidgetTypes.Form && props.children}
            { showOperations.includes(props.meta.type as WidgetTypes)
                && <Operations
                    operations={props.operations}
                    bcName={props.meta.bcName}
                    widgetMeta={props.meta}
                    hiddenGroups={props.meta.options && props.meta.options.hideActionGroups}
                    formStyle={props.meta.type === WidgetTypes.Form}
                />
            }
            {props.meta.type !== WidgetTypes.Form && props.children}
        </div>
    </div>
}

function mapStateToProps(store: AppState, ownProps: CardOwnProps): CardStateProps {
    const bcName = ownProps.meta.bcName
    const bcUrl = store.screen.bo.bc[bcName] && buildBcUrl(bcName, true)
    const operations = store.view.rowMeta[bcName]
        && store.view.rowMeta[bcName][bcUrl]
        && store.view.rowMeta[bcName][bcUrl].actions
    return {
        operations,
        viewName: store.view.name
    }
}

export default connect(mapStateToProps)(Card)
