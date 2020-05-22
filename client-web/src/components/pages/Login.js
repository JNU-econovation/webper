import React from 'react';
import LoginButton from '../buttons/LoginButton';

class Login extends React.Component {
    render() {
        return (
            <div className="center">
                <div className="webperLogo centered"><img src="images/logo.png" /></div>
                <LoginButton />
            </div>
        );
    }
}

export default Login;