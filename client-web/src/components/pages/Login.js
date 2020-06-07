import React from 'react';
import LoginButton from '../Header/LoginButton';

class Login extends React.Component {
    render() {
        return (
            <div className="center">
                <div className="webperLogo centered"><img src="images/logo.png" alt="logo" /></div>
                <LoginButton />
            </div>
        )
    }
}

export default Login;