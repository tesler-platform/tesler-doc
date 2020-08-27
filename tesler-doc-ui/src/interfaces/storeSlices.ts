import {Store} from '@tesler-ui/core/interfaces/store'
import {DataState} from '@tesler-ui/core/interfaces/data'
import {TeslerSessionState} from 'reducers/session'
import {TeslerScreenState} from './screen'
import {TeslerViewState} from './view'

/**
 * You can change typings or add new store slices here
 */
export interface AppReducers extends Partial<Store> {
    screen: TeslerScreenState,
    data: DataState,
    view: TeslerViewState,
    session: TeslerSessionState
}

export type AppState = Store & AppReducers
