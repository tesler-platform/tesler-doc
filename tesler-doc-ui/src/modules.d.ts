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

declare module '*.less' {
    const styles: { [className: string]: string }
    export = styles
}

declare module '*.json' {
    const json: any
    export = json
}

declare module '*.svg' {
    const base64: string
    export = base64
}

declare var require: any

declare module 'file-saver' {
    const module: any
    export = module
}
