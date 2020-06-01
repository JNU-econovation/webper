import React from 'react';
import history from '../../history';

const CreateButton = () => {
    const toNew = (parent_id = 0) => {
        history.push(`/new/${parent_id}`);
    }
    return (
        <button onClick={toNew(props.parent_id)}>+</button>
    )
}