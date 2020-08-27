import {LoginResponse} from '@tesler-ui/core/interfaces/session'

export interface TeslerLoginResponse extends LoginResponse {
    fullName: string,
    login: string
}
