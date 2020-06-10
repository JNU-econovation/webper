import React from 'react';
import history from '../../history';

const CreateButton = ({ id }) => {
    let parentId = id;
    if (!id) parentId = 0;
    const toNew = () => {
        history.push(`/new/${parentId}`);
    }

    return (
        <img onClick={toNew} src={window.location.origin + "/images/plus.png"} alt="create button" className="button" />
    )
}

export default CreateButton;