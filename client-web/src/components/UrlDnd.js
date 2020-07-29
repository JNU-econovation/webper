import React from 'react';
import DragAndDrop from './DragAndDrop';

const UrlDnd = (props) => {
    const reducer = (state, action) => {
        switch (action.type) {
            case 'SET_DROP_DEPTH':
                return { ...state, dropDepth: action.dropDepth }
            case 'SET_IN_DROP_ZONE':
                return { ...state, inDropZone: action.inDropZone }
            default:
                return state;
        }
    };

    const [data, dispatch] = React.useReducer(
        reducer, { dropDepth: 0, inDropZone: false }
    )

    return (
        <DragAndDrop data={data} dispatch={dispatch} onSubmit={props.onSubmit} />
    );
}

export default UrlDnd; 