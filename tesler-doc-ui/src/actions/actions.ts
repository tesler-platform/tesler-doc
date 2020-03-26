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

export const INCREMENT_BUTTON = 'INCREMENT_BUTTON'
export const DECREMENT_BUTTON = 'DECREMENT_BUTTON'
export const MENU_VISIBLE = 'MENU_VISIBLE'
export const MOBILE_MENU = 'MOBILE_MENU'

export function setMenuVisible(menuVisible: boolean) {
    return {
        type: 'MENU_VISIBLE',
        payload: menuVisible
    }
}

export function setMobileMenu(mobileMenu: boolean) {
    return {
        type: 'MOBILE_MENU',
        payload: mobileMenu
    }
}
