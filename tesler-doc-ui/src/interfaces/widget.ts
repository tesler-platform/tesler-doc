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

import {WidgetMeta} from '@tesler-ui/core/interfaces/widget'
import {ObjectMap} from '@tesler-ui/core/interfaces/objectMap'
import React from 'react'
import {MultivalueSingleValue} from '@tesler-ui/core/interfaces/data'

export const emptyMultivalueField: MultivalueSingleValue[] = []

export const enum TeslerFieldKey {
    newVersion = 'newVersion'
}

/**
 * TODO: JSDOC
 */
export function usePositionedWidgets(widgets: WidgetMeta[], skipWidgetTypes: string[]) {
    return React.useMemo(
        () => {
            const byRow: ObjectMap<WidgetMeta[]> = {}

            if (!widgets) {
                return byRow
            }

            widgets.forEach(item => {
                if (skipWidgetTypes && skipWidgetTypes.includes(item.type)) {
                    return
                }

                if (!byRow[item.position]) {
                    byRow[item.position] = []
                }
                byRow[item.position].push(item)
            })

            return byRow
        },
        [widgets, skipWidgetTypes]
    )
}
