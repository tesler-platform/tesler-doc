/**
 * This is an utility class for typing payload of redux actions
 */
import {ActionPayloadTypes as TeslerActionPayloadTypes, createActionCreators} from '@tesler-ui/core'
import {TeslerLoginResponse} from 'interfaces/session'

const z = null as any

/**
 * Declare your redux actions here with action name and payload type
 *
 * Assign every action an empty value (`z`) to prevent Typescript from erasing it in runtime
 * @see https://github.com/microsoft/TypeScript/issues/12437
 */
export class CustomActionTypes extends TeslerActionPayloadTypes {

    /**
     * An example of action and payload declaration
     */
    customAction: {
        customMessage: string
    } = z

     /**
      * You can expand payload of internal tesler-ui actions:
      */
    changeLocation: TeslerActionPayloadTypes['changeLocation'] & {
        customPayloadField?: number
    } = z

    setMenuVisible: boolean = z
    setMobileMenu: boolean = z

    loginDone: TeslerLoginResponse = z

    /**
     * Toggle selection for specified records
     */
    selectRecord: {
        /**
         * Ids of toggled records
         */
        ids: string[],
        /**
         * Selected or not
         */
        selected: boolean
    } = z

}

/**
 * Action creator helper allowing to create action typed actions:
 * 
 * $do.customAction({ customMessage: 'test '}) will result in:
 * { type: 'customAction', payload: { customMessage: 'test' } }
 */
export const $do = createActionCreators(new CustomActionTypes())
