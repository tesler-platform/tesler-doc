## Front-end implementation (AssocListPopup component)

`AssocListPopup` component is a wrapper of `@tesler-ui/core`'s `Popup` widget, which provides to select one or more records from table.  
Its body can render some `@tesler-ui/core`'s table widgets. It behavior controls by conditions from meta data.  

## API

### AssocListPopup properties

|  Property |  Description | Type  |
|:---|:---|:---|
| **meta***  | Meta data of widget `AssocListPopup`  | `WidgetTableMeta` |
| **components**  | Object which provides customisation `title`, `table` and `footer` of `Popup` | <code>{<br/>&nbsp;&nbsp;title?:&nbsp;ReactNode,<br/>&nbsp;&nbsp;table?:&nbsp;ReactNode,<br/>&nbsp;&nbsp;footer?:&nbsp;ReactNode<br/>}</code>  |
 
 Also `AssocListPopup` extends `Popup`'s own properties (`PopupProps`) except `bcName`, `children` and `showed`. 
 
 ### Customisation
 
 For purposes of extending `AssocListPopup` logic you can override it inside your project.
 
 For example:  
 `your-ui-project/src/components/widgets/AssocListPopup/AssocListPopup.tsx`:
 ```typescript
 import {AssocListPopup as CoreAssocListPopup} from '@tesler-ui/core'
 export interface AssocListPopupActions {
    // result of map dispatch to props
 }
 
 export interface AssocListPopupOwnProps {
     meta: WidgetTableMeta
 }
 
 export interface AssocListPopupProps extends AssocListPopupOwnProps {
    // result of map state to props

 }
 
 function AssocListPopup(props: AssocListPopupProps & AssocListPopupActions) {
    // here you can implement your logic
    
    // example of usage PopupProps
    const modalWidth = 1000 
    
    // example of usage `components` prop
    const customFooter = React.useMemo(() => {
       return <div>{/*Your implementation*/}</div>
    }, [])

    const customComponents: {
            title?: React.ReactNode,
            table?: React.ReactNode,
            footer?: React.ReactNode
        } = {
            title: <h2>Here you can set custom title</h2>,
            table: <div><p>Here you can set custom table</p>
            <p>{`Also you can notice, that Popup's props
             closable={false} getContainer={false} width={modalWidth} are passed to CoreAssocListPopup`}</p>
             </div>,
            footer: customFooter
        }
    
    return <CoreAssocListPopup
        closable={false}
        getContainer={false}
        width={modalWidth}
        widget={props.meta as WidgetTableMeta}
        components={customComponents}
    />
 }

```
### Live example

You can check out example of customisation below.  
Full code of example is available here:  
`/tesler-doc/tesler-doc-ui/src/components/widgets/AssocListPopup/AssocListPopup.tsx`  
<details>
<summary>How to interact with example?</summary>
<br>
Click on 'Create', then click on 'folder' icon.
</details>
<br/>

**NB** Do not forget to pass your `AssocListPopup` implementation to `customWidgets` list of `View` properties.
