# Middlewares

Tesler UI provides several middlewares which performs before epics.

Here is a list of middlewares in order of occurrences:

### Middleware of automatic saving

It performs in next cases:

- If the action is `sendOperation` of core type which called for another BC. But saving action and BCs having pending `_associate` are ignored;
- If the action is `selectTableCellInit` called for another row or another widget.

**Addition:** If widget has only custom actions, then `defaultSave` option of meta-data will be executed instead of default saving action.  
Also if widget has `defaultSave` action, then automatic saving will be performed by `changeLocation` action.

### Middleware of checking required fields

Handles validation of "required fields" for widget operations.  

Provides next logic:  
If action type is `sendOperation`:
- If operation marked as validation-sensetive, mark all showed 'required' fields which haven't been filled as dirty and invalid
- If operation is not validation-sensetive and validation failed, offer to drop pending changes

### Middleware of preInvoke action

If action type is `sendOperation` containing `preInvoke` action, Process `preInvoke` operation before action `sendOperation`.


## Customisation

Tesler UI provides mechanism of customisation middlewares.  
 It allows to set custom middlewares before, after or instead of core middlewares. Also core middlewares can be disabled.  
 For example:
 ```typescript
import {
 CustomMiddlewares,
 CoreMiddlewares
} from '@tesler-ui/core/interfaces/customMiddlewares'

// Describe an interface which extends `CoreMiddlewares`
interface MyMiddlewares extends Partial<CoreMiddlewares> {
    myFirstMiddleware: CustomMiddleware
    myLastMiddleware: CustomMiddleware
}

// Pass recently created interface to generic `CustomMiddlewares`
const myAwesomeMiddlewares: CustomMiddlewares<MyMiddlewares> = {

    // will be placed before tesler's middlewares
    myFirstMiddleware: { implementation: myFirstMiddlewareImpl, priority: 'BEFORE' },

    // will override tesler's autosave middelware
    autosave: myAutosaweMiddlewareOverriding,

    // will be placed after tesler's middlewares
    myLastMiddleware: { implementation: myLastMiddlewareImpl, priority: 'AFTER' },

    // teslers `requiredFields` middleware will be disabled
    requiredFields: null
}
```
Custom middlewares can be passed to tesler as customMiddlewares prop of tesler's Provider:  
```typescript
import {Provider} from '@tesler-ui/core'
const App = <Provider
    customMiddlewares={myAwesomeMiddlewares}
    ... // other `Provider`'s props
>...</Provider>
```
