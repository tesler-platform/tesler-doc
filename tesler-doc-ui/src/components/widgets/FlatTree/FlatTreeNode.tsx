/*
 * TESLER-UI
 * Copyright (C) 2018-2020 Tesler Contributors
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
import styles from './FlatTreeNode.less'
import {Icon} from 'antd'


export interface bek {
    items: any,
    fields: string[],
    searchExpression: string,
    expandedItems: string[],
    onToggle: (id: string) => void,
}

export interface pek {
    data: bek,
    index: number,
    style: any
}

/**
 *
 * @param props
 */
export function TreeVirtualizedNode(props: pek) {
    const data = props.data
    const item = data.items[props.index]
    const expanded = data.expandedItems.includes(item.id)
    return <div
        className={styles.row}
        style={{
            ...props.style
        }}
    >
        <div className={styles.controls}>
            { item.children?.length &&
                <button
                    className={styles.button}
                    onClick={() => data.onToggle(item.id)}
                >
                    <Icon
                        className={styles.icon}
                        type={expanded ? 'minus-square' : 'plus-square'}
                    />
                </button>
            }
        </div>
        { data.fields?.map(key => {
            const content = item[key]
            return <div
                key={key as string}
                className={styles.column}
                style={{
                    marginLeft: `${item.level * 40}px`
                }}
            >
                <div className={styles.content} onClick={() => console.warn('3')}>
                    { content }
                </div>
            </div>
        })}

    </div>
}

export default React.memo(TreeVirtualizedNode)
