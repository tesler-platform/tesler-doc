import React from 'react'
import deps from './deps.json'
import SearchHighlight from 'components/ui/SearchHighlight/SearchHighlight'
interface pek {
    id: string,
    parentId: string,
    level: number,
    parent: pek,
    name: string,
    children: pek[]
}

let s: string = null

export const Test: React.FC = () => {
    const [tree, setTree] = React.useState<pek[]>()
    const [exp, setExp] = React.useState<string[]>([])
    const [searchExpression, setSearchExpression] = React.useState(null)
    const bbb = React.useRef<pek[]>()

    React.useEffect(() => {
        bbb.current = ppp(deps.data)
        setTree(arrayToTree(bbb.current))
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
        s = event.currentTarget.value
    }

    const handleSearch = () => {
        const expanded: Record<string, boolean> = {}
        bbb.current.forEach(item => {
            if (item.name.includes(searchExpression)) {
                expanded[item.id] = true
                let parent = item.parent
                while (parent) {
                    expanded[parent.id] = true
                    parent = parent.parent
                }
            }
        })
        setExp(Object.keys(expanded))
    }
 
    return <div>
        <input onBlur={handleBlur} />
        <button onClick={handleSearch}>Search</button>
        <table>
                {getChildren(tree, handleExp, exp)}
        </table>

    </div>
}

export default Test
const F = (formatString: string) => <b>{formatString}</b>

function getChildren(nodes: pek[], h: (e: any) => void, exp: string[]): React.ReactNode {
    return nodes?.map((item, index: number) => {
        return <>
            <tr key={item.id}>
                <td>
                    { item.children &&
                        <button data-id={item.id} onClick={h}>
                            { exp.includes(item.id) ? '-' : '+' }
                        </button>
                    }
                </td>
                <td>
                    <SearchHighlight
                        source={item.name}
                        search={escapedSrc(s)}
                        match={F}
                    />
                </td>
            </tr>
            {exp.includes(item.id) && getChildren(item.children, h, exp)}
        </>  
    })
}

function ppp(flat: pek[]) {
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

function arrayToTree(flat: pek[]) {
    const root: pek[] = []
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

