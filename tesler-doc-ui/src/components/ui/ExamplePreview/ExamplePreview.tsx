import React from 'react'

interface ExamplePreviewProps {
    title?: React.ReactNode
}

export const ExamplePreview: React.FC<ExamplePreviewProps> = (props) => {
    return <div style={{
        border: '1px solid rgb(204, 204, 204)',
        paddingTop: '8px',
        paddingLeft: '8px'
    }}>
        { props.title ?? <h3>Example</h3> }
        {props.children}
    </div>
}

export default ExamplePreview
