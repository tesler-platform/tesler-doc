import React from 'react'

interface SearchHighlightProps {
    source: string,
    search: string | RegExp,
    match: (substring: string) => React.ReactNode,
    notMatch?: (substring: string) => React.ReactNode
}

const SearchHighlight: React.FC<SearchHighlightProps> = (props) => {
    const tokens = splitIntoTokens(props.source, props.search)
    return <>
        { tokens
            .filter(item => !!item)
            .map((item, index) => {
                const isMatch = props.search instanceof RegExp
                    ? props.search.test(item)
                    : item === props.search
                if (isMatch) {
                    return <React.Fragment key={index}>
                        {props.match(item) || item}
                    </React.Fragment>
                }
                return <React.Fragment key={index}>
                    {props.notMatch?.(item) || item}
                </React.Fragment>
            })}
    </>
}

export function splitIntoTokens(source: string, search: string | RegExp) {
    const tokenizer = search instanceof RegExp
        ? search
        : escapedSrc(search)
    return source.split(tokenizer)
}

/**
 * Convert string to RegExp
 *
 * @param str Source string
 */
export function escapedSrc(str: string) {
    return new RegExp(`(${str?.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, '\\$&')})`, 'gi')
}


export default React.memo(SearchHighlight)
