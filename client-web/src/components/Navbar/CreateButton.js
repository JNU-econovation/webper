import React from 'react';
import history from '../../history';

const CreateButton = ({ id }) => {
    let parentId = id;
    if (!id) parentId = 0;
    const toNew = () => {
        history.push(`/new/${parentId}`);
    }

    return (
        <button onClick={toNew} className="rightend">+</button>
    )
}

export default CreateButton;