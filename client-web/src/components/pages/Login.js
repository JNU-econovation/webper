import React from "react";
import LoginButton from "../Header/LoginButton";

class Login extends React.Component {
  render() {
    return (
      <div className="center">
        <div className="webperLogo centered">
          <img className="webperLogo" src="images/logo.png" alt="logo" />
        </div>
        <div>
          <button className="google-login-button">
            <LoginButton className="google-login-content" />
          </button>
        </div>
      </div>
    );
  }
}

export default Login;
