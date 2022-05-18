import React from 'react'

export interface PhoneInputProps {
    value: string
}

export const PhoneInput: React.FC<PhoneInputProps> = (props) => {
    const { value } = props
    return <input
        value={formatter(value)}
        readOnly
    />
}

function formatter(src: string) {
    const mask = '0000000000'.split('').map((_, index) => src[index] || '')
    return `+7 ${mask[0]}${mask[1]}${mask[2]} ${mask[3]}${mask[4]} ${mask[5]}${mask[6]}`
}
