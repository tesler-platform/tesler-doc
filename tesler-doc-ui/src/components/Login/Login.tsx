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

import React, {FunctionComponent} from 'react'
import {connect, $do} from '@tesler-ui/core'
import {Form, Input, Button, Icon} from 'antd'
import {AppState} from 'interfaces/storeSlices'
import {Dispatch} from 'redux'
import styles from './Login.less'

export interface LoginProps {
    spin: boolean,
    errorMsg: string,
    onLogin: (login: string, password: string) => void
}

export const Login: FunctionComponent<LoginProps> = (props) => {
    const [login, setLogin] = React.useState('vanilla')
    const [password, setPassword] = React.useState('vanilla')

    const handleLogin = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLogin(event.target.value)
    }

    const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value)
    }

    const handleClick = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        props.onLogin(login, password)
    }

    return <Form onSubmit={handleClick}>
        <Form.Item>
            <Input
                prefix={<Icon type="user" />}
                placeholder="Username"
                value={login}
                onChange={handleLogin}
            />
        </Form.Item>
        <Form.Item>
            <Input.Password
                prefix={<Icon type="lock" />}
                placeholder="Password"
                value={password}
                onChange={handlePassword}
            />
        </Form.Item>
        <Form.Item>
            <Button
                block
                autoFocus
                loading={props.spin}
                type="primary"
                htmlType="submit"
                disabled={!login}
            >
                Sign in
            </Button>
            <span className={styles.error}>
                {props.errorMsg}
            </span>
        </Form.Item>
    </Form>
}

function mapStateToProps(store: AppState) {
    return {
        spin: store.session.loginSpin,
        errorMsg: store.session.errorMsg
    }
}

function mapDispatchToProps(dispatch: Dispatch) {
    return {
        onLogin: (login: string, password: string) => {
            dispatch($do.login({ login, password }))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login)
