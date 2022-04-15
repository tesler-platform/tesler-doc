## Front-end implementation (PickListPopup component)

`PickListPopup` component is a wrapper of `@tesler-ui/core`'s `Popup` widget, which provides to select single record from table.  
Its body can render some `@tesler-ui/core`'s table widgets. It behavior controls by conditions from meta data.

## API

### PickListPopup properties

|  Property |  Description | Type  |
|:---|:---|:---|
| **meta***  | Meta data of widget `PickListPopup`  | `WidgetTableMeta` |
| **components**  | Object which provides customisation `title`, `table` and `footer` of `Popup` | <code>{<br/>&nbsp;&nbsp;title?:&nbsp;ReactNode,<br/>&nbsp;&nbsp;table?:&nbsp;ReactNode,<br/>&nbsp;&nbsp;footer?:&nbsp;ReactNode<br/>}</code>  |
 
 Also `PickListPopup` extends `Popup`'s own properties (`PopupProps`) except `bcName`, `children` and `showed`. 
 
 ### Customisation
 
 For purposes of extending `PickListPopup` logic you can override it inside your project.
 
 For example:  
 `your-ui-project/src/components/widgets/PickListPopup/PickListPopup.tsx`:
```typescript
import React from 'react'
import {WidgetTableMeta} from '@tesler-ui/core/interfaces/widget'
interface PickListPopupOwnProps {
    meta: WidgetTableMeta
}
interface PickListPopupProps extends PickListPopupOwnProps {
// props from mapStateToProps and mapDispatchToProps
}
export const PickListPopup: React.FunctionComponent<PickListPopupProps> = (props) => {

    // implement custom logic here
    
    // examole of customisation
    const customTable = <div><h3>Here is Custom component instead Table </h3>
        <p>{`Also you can notice, that Popup's props
         closable={false} bodyStyle={modalBodyStyle} width={modalWidth} are passed to CoreAssocListPopup`}</p>
    </div>
    const customComponents: {
        title?: React.ReactNode;
        table?: React.ReactNode;
        footer?: React.ReactNode;
    } = {
        title: <h2>custom Title</h2>,
        table: customTable,
        footer: <h2>Custom foooooooooter</h2>
    }
    
    // example of PopupProps usage
    const modalWidth = 300
    const modalBodyStyle = {
        height: 300
    }
    
    return <div>
        <CorePickListPopup
            closable={false}
            components={customComponents}
            widget={props.meta}
            bodyStyle={modalBodyStyle}
            width={modalWidth}
        />
    </div>

}
```
### Live example

You can check out example of customisation below.  
Full code of example is available here:  
`/tesler-doc/tesler-doc-ui/src/components/widgets/PickListPopup/PickListPopup.tsx`  
<details>
<summary>How to interact with example?</summary>
<br>
Click on 'Create', then click on 'paperclip' icon.
</details>
<br/>

**NB** Do not forget to pass your `PickListPopup` implementation to `customWidgets` list of `View` properties.
