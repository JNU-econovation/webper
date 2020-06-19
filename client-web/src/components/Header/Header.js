import React from 'react';
import { Link } from 'react-router-dom';
import ProfileButton from './ProfileButton';

const Header = () => {
    const openNav = () => {
        const nav = document.querySelector("nav");
        const main_body = document.querySelector(".main-body");
        nav.style.width = "250px";
        main_body.style.marginLeft = "260px";
    }

    return (
        <div className="header-container">
            <img onClick={openNav} alt="menubar-icon" src={window.location.origin + "/images/menuicon.png"} className="menu-icon button" />
            <Link to='/'>
                <img alt="main" src={window.location.origin + "/images/logo.png"} className="main-link" />
            </Link>
            <ProfileButton />
        </div>
    )
}

export default Header;