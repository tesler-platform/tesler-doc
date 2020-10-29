# Custom Fields

To add your own field type, you need to write a React component and pass it to `customFields` property of the `<View />` component.  
You'll also need to register a class describing meta data for new field type in Tesler application.

## Write a component

Let's create a field for displaying phone numbers that will transofrm input like `555123456` to `+7 555 123 45 67`:
```tsx
// PhoneInput.tsx
import React from 'react'

export interface PhoneInputProps {
    value: string
}

export const PhoneInput: React.FC<PhoneInputProps> = (props) => {
    const { value, ...rest } = props
    return <input
        value={formatter(value)}
        readOnly
    />
}
          
function formatter(src: string) {
    const mask = '0000000000'.split('').map((_, index) => src[index] || '')
    return `+7 ${mask[0]}${mask[1]}${mask[2]} ${mask[3]}${mask[4]} ${mask[5]}${mask[6]}`
}
```
Now we need to pass this component to the `<View />` component:

```tsx
// AppLayout.tsx
import React from 'react'
import {PhoneInput} from './PhoneInput'

const customFields = {
    'phone': PhoneInput        
}

export const AppLayout = (props) => {
    // ...
    return <View
        customFields={customFields}
    >
}
```

## Write `FieldMeta` class

Suppose our new `phone` type field is used by some widget with following description:

```js
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
      "type": "phone",
      // "customParameter": "exampleParameter"
    }
  ]
}
```

Aside of data itself fields may often contain some additional info or configuration (text color for the field, or perhaps an url to open when the field is clicked); such additional info is obtained from field description in widget json file, and for a new field you need to write a class describing this additional info.

Field meta class is extended from [FieldMetaBase](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/ui/model/json/field/FieldMeta.java) and annotated with [TeslerWidgetField](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/ui/field/TeslerWidgetField.java) annotation; annotation parameters includes field types which will use this class as its field meta description:

```java
package io.tesler.core.field.meta;

import io.tesler.core.ui.field.TeslerWidgetField;
import io.tesler.core.ui.model.json.field.FieldMeta;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TeslerWidgetField({"phone"})
public class PhoneFieldMeta extends FieldMeta.FieldMetaBase {

    // String customParameter;

}

```

As our new `phone` field has no additional info aside of its value, this class entirely relies on fields from its superclass and `customParameter` property is commented out.

One last thing is to register a package with your class in application properties: by default, Tesler only scans for `TeslerWidgetField` annotation in `io.tesler.core.ui.model.json.field.subtypes` package, though it's configurable with `tesler.widget.fields.include-packages` property in your `application.properties` file.  
`PhoneFieldMeta` is declared in `io.tesler.core.field.meta`, so the configuration will look like this:

```js
// application.properties
tesler.widget.fields.include-packages=io.tesler.core.ui.model.json.field.subtypes,io.tesler.core.field.meta
```

And that's it. Now, if our widget description contains a field with a `phone` type, our `<PhoneInput />` React component will be used:
