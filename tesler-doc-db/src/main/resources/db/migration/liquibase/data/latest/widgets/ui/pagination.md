# Pagination

For each business component Tesler UI stores only a subset of data: the *page*. Every data fetch request to Tesler API has two query parameters applied: `_page` for the page number and `_limit` for the size of page.

Example of the data fetch request with applied query params:
```sh
http://idocs.tesler.io/api/v1/data/tutorial/bulkUpdateExample?_limit=5&_page=1
```

Built-in `<Pagination />` control comes in two modes that can change the `_page` parameter: a simple forward/backward traversal (`PaginationMode.page`) and a `Load More` button that appends new chunk to already fetched data (`PadinationMode.page`).  

Let's see some examples:

## `Page` mode

<!-- Widget 1 -->

## `Load More` mode

<!-- Widget 2 -->

## Usage in custom widgets

You need to import the component and provide it with mode and widget name:

```tsx
import React from 'react'
import {Pagination} from '@tesler-ui/core'

export const customWidget: React.FC = (props) => {
    return <div>
        <Pagination
            mode={PaginationMode.page}
            widgetName="widget-name"
        />
    </div>
}
```

Changing page will fire `bcChangePage` or `bcLoadMore` actions depending on the mode.

## Props

<!-- import props from API reference -->

## Page size

One last thing to know about pagination is how page size (`_limit`) is configured.  
There are three places where you can define it.

### Widget meta data

You may configure page size per widget by specifying the `limit` parameter in json file for your widget:

```json
{
  "name": "pagination-example-page",
  "title": "`Page` mode example",
  "type": "List",
  "bc": "paginationExample",
  "limit": 5
}
```

### Business component

You may configure page size per business component. Widget limit will take precedence if present.

<!-- Example needed -->

### Service layer

Additionally you may override getList method in the service for your business component.

<!-- Example needed -->
