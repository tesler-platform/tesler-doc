import React from 'react'
import deps from './deps.json'
import SearchHighlight from 'components/ui/SearchHighlight/SearchHighlight'
import {FixedSizeList, ListChildComponentProps} from 'react-window'
import styles from './test.less'
import { breadthFirstSearch } from 'utils/breadthFirst'
interface DataNode {
    id: string,
    parentId: string,
    level: number,
    parent: DataNode,
    name: string,
    children: DataNode[],
}

interface VirtualizedFlatTreeProps {
    matchCase?: boolean
}

interface VirtualizedDataItem {
    items: DataNode[],
    searchExpression: string,
    expandedItems: string[],
    onToggle: (id: string) => void
}

const initialExpanded: Record<string, boolean> = { '0': true }

export const Test: React.FC<VirtualizedFlatTreeProps> = (props) => {
    const [withParents, setWithParents] = React.useState<DataNode[]>()
    const [tree, setTree] = React.useState<DataNode[]>()
    const [exp, setExp] = React.useState<string[]>(Object.keys(initialExpanded))
    const [foundNodes, setFoundNodes] = React.useState<string[]>()
    const [searchExpression, setSearchExpression] = React.useState<string>(null)

    React.useEffect(() => {
        const withP = assignNodeParent(deps.data)
        const asTree = arrayToTree(withP)
        setWithParents(withP)
        setTree(asTree)
    }, [])

    const handleExp = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        const key = event.currentTarget.dataset.id
        setExp(exp.includes(key)
            ? exp.filter(item => item !== key)
            : [...exp, key]
        )
    }

    const handleBlur = (event: React.FocusEvent<HTMLInputElement>) => {
        setSearchExpression(event.currentTarget.value)
    }

    const handleSearch = () => {
        if (!searchExpression) {
            setFoundNodes(null)
            setExp(Object.keys(initialExpanded))
            return
        }
        const found = withParents.filter(item => {
            if (props.matchCase) {
                return item.name.includes(searchExpression)
            } else {
                return item.name.toLowerCase().includes(searchExpression.toLowerCase())
            }
        })
        const newExpandedNodes = { ...initialExpanded }
        found.forEach(item => {
            let parent = item.parent
            while (parent) {
                newExpandedNodes[parent.id] = true
                parent = parent.parent
            }
        })
        setExp(Object.keys(newExpandedNodes))
        setFoundNodes(found.map(item => item.id ))

    }

    const flat = React.useMemo(() => {
        if (!withParents) {
            return []
        }
        const searchResultTree = foundNodes
            ? buildSearchResultTree(withParents, foundNodes)
            : withParents
        const mSearchResult = searchResultTree.map(item => {
            let _expanded = exp.includes(item.parentId)
            if (foundNodes && item.children) {
                _expanded = exp.includes(item.parentId) && !!breadthFirstSearch(item, (data: DataNode) => {
                    return foundNodes.includes(data.id)
                }, 0)
            }
            return {
                ...item,
                _expanded
            }
        })
        return mSearchResult.filter(item => item._expanded)
    }, [withParents, foundNodes, exp])
 
    const handleToggle = React.useCallback((id: string) => {
        if (exp.includes(id)) {
            const exclude = [id]
            const index = withParents.findIndex(item => item.id === id)
            if (withParents[index].children) {
                hui(withParents[index].children, exclude)
            }
            setExp(exp.filter(item => !exclude.includes(item)))
        } else {
            setExp([ ...exp, id ])
        }
    }, [exp, withParents])

    const memoizedData = React.useMemo(() => {
        return { items: flat, searchExpression, expandedItems: exp, onToggle: handleToggle }
    }, [flat, handleToggle, exp, searchExpression])

    return <div>
        <input onBlur={handleBlur} />
        <button onClick={handleSearch}>Search</button>
        <div>
            <FixedSizeList
              width={808}
              height={445}
              itemSize={60}
              itemData={memoizedData}
              itemCount={flat.length}
            >
                {Row}
            </FixedSizeList>
            {
                // getChildren(tree, handleExp, exp)
            }
        </div>

    </div>
}

export default Test
const F = (formatString: string) => <b>{formatString}</b>


function hui(nodes: DataNode[], result: string[]) {
    nodes.forEach(child => {
        result.push(child.id)
        if (child.children) {
            hui(child.children, result)
        }
    })
}

const Row: React.FC<ListChildComponentProps> = (props) => {
    const data = props.data as VirtualizedDataItem
    const item = data.items[props.index]
    const expanded = data.expandedItems.includes(item.id)
    return <div className={styles.row} style={props.style}>
        <div>
            { item.children?.length &&
                <button onClick={() => data.onToggle(item.id)}>
                    {expanded ? '-' : '+'}
                </button>
            }

        </div>
        <div style={{ marginLeft: `${item.level * 20}px` }}>
            { data.searchExpression
                ? <SearchHighlight
                    source={item.name}
                    search={escapedSrc(data.searchExpression)}
                    match={F}
                />
                : item.name
            }
            
        </div>
    </div>
}

function buildSearchResultTree(nodes: DataNode[], matchingNodes: string[]) {
    const result: Record<string, boolean> = {}
    nodes.forEach(item => {
        if (!matchingNodes.includes(item.id)) {
            return false
        }
        let parent = item.parent
        while (parent) {
            result[parent.id] = true
            parent = parent.parent
        }
        result[item.id] = true
        item.children?.forEach(child => {
            result[child.id] = true
        })
    })
    return nodes.filter(item => result[item.id] === true)
}

/**
 * Assigns a reference to parent based on parentId property
 *
 * @param flat 
 */
function assignNodeParent(flat: DataNode[]) {
    const map: Record<string, number> = {}
    flat.forEach(item => {
        let parentIndex = map[item.parentId]
        if (!parentIndex) {
            parentIndex = flat.findIndex(el => el.id === item.parentId)
            map[item.parentId] = parentIndex 
        }
        item.parent = flat[parentIndex]
    })
    return flat
}


/**
 * Build tree structure from 
 * @param flat
 */
function arrayToTree(flat: DataNode[]) {
    const root: DataNode[] = []
    const map: Record<string, number> = {}

    flat.forEach(node => {
        if (!node.parentId || node.parentId === '0') {
            return root.push(node)
        }
        let parentIndex = map[node.parentId]
        if (!parentIndex) {
            parentIndex = flat.findIndex(el => el.id === node.parentId)
            map[node.parentId] = parentIndex
        }
        
        if (!flat[parentIndex].children) {
            return flat[parentIndex].children = [node]
        }
        
        flat[parentIndex].children.push(node)
    })
    return root
}

export function escapedSrc(str: string) {
    return new RegExp(`(${str?.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, '\\$&')})`, 'gi')
}

