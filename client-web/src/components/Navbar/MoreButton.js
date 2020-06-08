import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import history from '../../history';

const MoreButton = ({ id }) => {

    const [showOptions, setShowOptions] = useState(false);
    const onClick = () => {
        if (showOptions === false) {
            setShowOptions(true);
            document.querySelector(`.option${id}`).style.display = "block";
        } else {
            setShowOptions(false);
            document.querySelector(`.option${id}`).style.display = "none";
        }
    }

    const getClassName = () => {
        return (showOptions ? "down" : "");
    }

    const toDelete = () => {
        history.push(`delete/${id}`);
    }

    return (
        <div className="more-wrapper">
            <img src={window.location.origin + "/images/more.png"} alt="more button" className={`${getClassName()} button`} onClick={onClick} />
            <div className={`dropdown-content option${id}`}>
                <Link to="">Edit Title</Link>
                <Link to={`/delete/${id}`}>Delete</Link>
            </div>
        </div>
    )
}

export default MoreButton;