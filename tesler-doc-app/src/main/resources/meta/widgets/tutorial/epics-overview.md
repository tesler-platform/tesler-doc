# Epics

Tesler UI relies on [redux-observable](https://github.com/redux-observable/redux-observable) peer depencdency for handling asynchronous interactions and chaining actions.  

Though our application maven archetype comes with a preconfigured `redux-observable` boilerplate, it is not mandatory for your application to use it: [redux-thunk](https://github.com/reduxjs/redux-thunk) and [redux-saga](https://redux-saga.js.org) both ara valid choices.  

Still, some info about `redux-observable` might be useful if you need to customize some of Tesler UI internals or if you choose to stick with preconfigured data flow.

# Writing an epic

Overall the process is pretty similiar with what you can read from [Epics](https://redux-observable.js.org/docs/basics/Epics.html) documentation, but with two additions:
* Application in archetype comes with a `CustomEpic` type that should be used instead of `Epic`: it generally follows the same signature:
```ts
function (action$: Observable<Action>, state$: StateObservable<State>): Observable<Action>
```
but with better typings for `Action` and `State` to help with autocomplete.
* Tesler UI provides its own `combineEpics` implementation allowing to disable or override our internal epics from your application.

So, how to write an epic for Tesler UI application?

## Step 1. Create a module for your epics:

```ts
// src\epics\data.ts
```

Internally we keep epics in one slice per module, the same as reducers, so for our `data` slice we use `epics\data.ts` and for `view` slice we use `epics\view.ts`.  
You can name your modules correspondinly or just introduce another module.

## Step 2. Create an epic in your module:

```ts
// src\epics\data.ts
import {$do, coreActions} from '@tesler-ui/core'
import {Observable} from 'rxjs/Observable'
import {CustomEpic} from 'actions/actions'

const customSendOperation: CustomEpic = (action$, store) =>
action$.ofType(coreActions.sendOperation)
.mergeMap((action) => {
    // Ajax call or perhaps some conditional fork can go here 
    return Observable.of($do.logout(null))
})
```

For example, in the snippet above we've declared an epic named `customSendOperation` that is triggered by build-in `sendOperation` action which should react with build-in action `logout`.  

`$do` here is a convinient helper to property type payload for [action creators](https://redux.js.org/basics/actions#action-creators) of all build-in action types. For your own application action types you can create a different helper or use the prefixed version from the maven archetype.

## Step 3. Export your epic:
```ts
// src\epics\data.ts
// ...
import {CustomEpicSlice} from '@tesler-ui/core/interfaces/customEpics'

// ...

export const screenEpics: CustomEpicSlice<'dataEpics'> = {
    customSendOperation
}

```

Now here we export the epic we've created in step 2 as a field of `CustomEpicSlice` generic type.  

### Customization

Generic parameter is a typed union of all build-in root epic slices. You can omit the generic parameter for your module, but it's required when you want to disable or override one of our build-in epics.  
So, for example when you specify `dataEpics` generic parameter your application will know, which build-in epics are there for this particular slice:  
```ts
export const screenEpics: CustomEpicSlice<'dataEpics'> = {
    /**
     * Just a regular new epic
     */
    customSendOperation,
    /**
     * Disable build-in `bcLoadMore' epic, so for example when somewhere in Tesler UI internals an action dispatched
     * that should be handled by this epic, nothing will happen, allowing you to handle this action yourself.
     */
    bcLoadMore: null,
    /**
     * Override build-in `selectView' epic with your own implementation like `customSendOperation`,
     * which is a more convinient form of the above when your implementation handles the same action type.
     */
    selectView: customSendOperation
}
```

## Step 4. Combine your slices into one big epic:

```ts
import CustomEpics from '@tesler-ui/core/interfaces/customEpics'
import {dataEpics} from 'epics/data'
// import {screenEpics} from 'epics/screen'
// import {viewEpics} from 'epics/view'
// ... import any number of your root epic slices

export const epics: CustomEpics = {
    dataEpics,
    // screenEpics,
    // viewEpics
}
```

You can also use a default [combineEpics](https://redux-observable.js.org/docs/api/combineEpics.html) helper here if you do not need to disable or override any of the build-in epics.

## Step 5. Pass your epics into a `customEpics` property of our `<Provider />` component:

```tsx
import React from 'react'
import {render} from 'react-dom'
import {Provider} from '@tesler-ui/core'
import {epics} from './epics'
import App from './components/App'

const App = <Provider customEpics={epics}>
    <Layout/>
</Provider>

render(App, document.getElementById('root'))
```

Now Tesler UI will combine the epics you wrote and its built-in epics with respect to disable/override configuration.

After that your epics will be triggered right after the monitored action dispatch (and after appropriate reducer, if any).
