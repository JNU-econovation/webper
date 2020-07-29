import React from 'react';

const DragAndDrop = props => {

    const { data, dispatch } = props;

    const handleDragEnter = e => {
        e.preventDefault();
        e.stopPropagation();

        dispatch({ type: 'SET_DROP_DEPTH', dropDepth: data.dropDepth + 1 });
        console.log("handleDragEnter", data.dropDepth);
    };

    const handleDragLeave = e => {
        e.preventDefault();
        e.stopPropagation();

        dispatch({ type: 'SET_DROP_DEPTH', dropDepth: data.dropDepth - 1 });
        if (data.dropDepth > 0) return
        dispatch({ type: 'SET_IN_DROP_ZONE', inDropZone: false })
        console.log("handleDragLeave", data.inDropZone, data.dropDepth);
    };

    const handleDragOver = e => {
        e.preventDefault();
        e.stopPropagation();

        e.dataTransfer.dropEffect = 'copy';
        dispatch({ type: 'SET_IN_DROP_ZONE', inDropZone: true });
        console.log("handleDragOver", data.inDropZone, data.dropDepth);
    };

    const handleDrop = e => {
        e.preventDefault();
        e.stopPropagation();

        let url = e.dataTransfer.getData('text/uri-list');
        console.log(url);
        if (url) {
            props.onSubmit({ 'inputURL': url })
            dispatch({ type: 'SET_DROP_DEPTH', dropDepth: 0 });
            dispatch({ type: 'SET_IN_DROP_ZONE', inDropZone: false });
            console.log("handleDrop", data.inDropZone);
        } else
            alert('유효한 URL이 아닙니다');
    };

    return (
        <div className={data.inDropZone ? 'drag-drop-zone inside-drag-area' : 'drag-drop-zone'}
            onDrop={e => handleDrop(e)}
            onDragOver={e => handleDragOver(e)}
            onDragEnter={e => handleDragEnter(e)}
            onDragLeave={e => handleDragLeave(e)}
        >
            <p>Drag URL here!</p>
        </div>
    );
};

export default DragAndDrop;
