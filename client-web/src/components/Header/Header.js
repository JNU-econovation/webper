import React from 'react';
import { Link } from 'react-router-dom';
import Loginbutton from './LoginButton';

const Header = () => {
    return (
        <div style={{ backgroundColor: "beige" }}>
            <Link to='/'>
                <img alt="main" src="images/logo.png" style={{ width: "100px" }} />
            </Link>
            <Loginbutton />
        </div>
    )
}

export default Header;