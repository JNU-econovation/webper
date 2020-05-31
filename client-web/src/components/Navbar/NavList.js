import React from 'react'

const NavList = (props) => {
    return (
        <li>
            <a herf="#">{props.title}</a>
            <button className="rightend">...</button>
            <button className="rightend">+</button>
        </li>
    )
}

export default NavList;