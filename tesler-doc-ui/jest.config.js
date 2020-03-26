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

const { compilerOptions } = require('./tsconfig');

compilerOptions.noUnusedLocals = false

module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'jsdom',
    setupFiles: ['./src/tests/setup.ts'],
    transform: {
        '^.+\\.tsx?$': 'ts-jest'
    },
    moduleNameMapper: {
        '^antd(.*)$': '<rootDir>/src/tests/assetsMock.js',
        /* webpack externals */
        '^react$': '<rootDir>/node_modules/react',
        '^react-dom(.*)$': '<rootDir>/node_modules/react-dom$1',
        '^react-redux(.*)$': '<rootDir>/node_modules/react-redux$1',
        /* assets stubs */
        '\\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2)$': '<rootDir>/src/tests/assetsMock.js',
        /* css stubs */
        '\\.(css|less)$': 'identity-obj-proxy',
    },
    moduleDirectories: ['node_modules', 'src'],
    globals: {
        'ts-jest': {
            tsConfig: compilerOptions
        }
    }
}
