import {Store} from '@tesler-ui/core/interfaces/store'
import {DataState} from '@tesler-ui/core/interfaces/data'
import {ViewState} from '@tesler-ui/core/interfaces/view'
import {TeslerSessionState} from 'reducers/session'
import {TeslerScreenState} from './screen'

/**
 * You can change typings or add new store slices here
 */
export interface AppReducers extends Partial<Store> {
    screen: TeslerScreenState,
    data: DataState,
    view: ViewState,
    session: TeslerSessionState
}

export type AppState = Store & AppReducers
