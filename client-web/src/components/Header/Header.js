import React from 'react';
import { Link } from 'react-router-dom';
import Loginbutton from './LoginButton';

const Header = () => {
    const openNav = () => {
        const nav = document.querySelector("nav");
        const main_body = document.querySelector(".main-body");
        nav.style.width = "250px";
        main_body.style.marginLeft = "260px";
    }

    return (
        <div className="header-container">
            <img onClick={openNav} alt="menubar icon" src="images/menuicon.png" className="menu-icon" />
            <Link to='/'>
                <img alt="main" src="images/logo.png" className="main-link" />
            </Link>
            <Loginbutton />
        </div>
    )
}

export default Header;