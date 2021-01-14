# Custom Components

Presentation layer of Tesler UI application is build from widgets (e.g. tables and forms) and [fields](#/screen/components/view/fields/) (e.g. inputs, numbers, checkboxes).

Aside of those built-in types, you can define your own widgets and fields in form of React component.

# <View />

`<View />` component is used to display currently active [view](#/screen/getting-started/view/view/) and is also an entry point for your customization.

# Fields

To add your own field type, write a React component and pass it to `customFields` property of the `<View />` component.

Let's create a field for displaying phone numbers that will transofrm input like `555123456` to `+7 555 123 45 67`:
```tsx
// PhoneInput.tsx
import React from 'react'

export const Phone = (props) => {
  const [value, ...rest] = props
  return <input
    value={formatter(value)}
    readOnly
  />
}
          
function formatter(src) {
  const mask = '0000000000'.split('').map((_, index) => src[index] || '')
  return `+7 ${mask[0]}${mask[1]}${mask[2]} ${mask[3]}${mask[4]} ${mask[5]}${mask[6]}`
}
```
Now we need to pass this component to the `<View />` component:

```tsx
// AppLayout.tsx
import React from 'react'
import {Phone} from './PhoneInput'

const customFields = {
    'phone': Phone        
}

export const AppLayout = (props) => {
    // ...
    return <View
        customFields={customFields}
    >
}
```

And that's it. Now, if our widget description contains a field with a `phone` type, our `<Phone />` React component will be used:

```json
{
  "name": "custom-fields",
  "title": "Custom Fields Example",
  "type": "List",
  "showCondition": [],
  "bc": "customFieldsExample",
  "fields": [
    {
      "title": "Phone Number",
      "key": "phoneNumber",
      "type": "phone"
    }
  ]
}

```
