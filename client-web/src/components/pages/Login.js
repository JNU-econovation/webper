import React from 'react';
import LoginButton from '../Header/LoginButton';
import { connect } from 'react-redux';

class Login extends React.Component {
    render() {
        return (
            <div className="center">
                <div className="webperLogo centered"><img src="images/logo.png" alt="logo" /></div>
                <LoginButton cookies={this.props.cookies} />
            </div>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    console.log("cookies:", ownProps.cookies);
    return {
        cookies: ownProps.cookies
    };
}

export default connect(mapStateToProps, null)(Login);