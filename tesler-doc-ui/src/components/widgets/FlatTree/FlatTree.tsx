import React from 'react'
import {FlatTree as CoreFlatTree} from '@tesler-ui/core'
import FlatTreeNode from './FlatTreeNode'

interface FlatTreeProps {
    meta: any
}

export const FlatTree: React.FC<FlatTreeProps> = (props) => {
    return <CoreFlatTree {...props}>
            {FlatTreeNode}
    </CoreFlatTree>
}

export default React.memo(FlatTree)
