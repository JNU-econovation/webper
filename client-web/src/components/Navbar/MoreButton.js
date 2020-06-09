import React, { useState } from 'react';
import { Link } from 'react-router-dom';

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

    return (
        <div className="more-wrapper">
            <img src={window.location.origin + "/images/more.png"} alt="more button" className={`${getClassName()} button`} onClick={onClick} />
            <div className={`dropdown-content option${id}`}>
                <Link to={`/edit/${id}`}>Rename Title</Link>
                <Link to={`/delete/${id}`}>Delete</Link>
            </div>
        </div>
    )
}

export default MoreButton;