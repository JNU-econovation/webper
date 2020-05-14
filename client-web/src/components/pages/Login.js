import React from 'react';

class Login extends React.Component {
    state = { isSignedIn: null };

    componentDidMount() {
        window.gapi.load('client:auth2', () => {
            window.gapi.client.init({
                clientId: '456504453062-kt1r0d72cqb53tthmfasbasejvsguql3.apps.googleusercontent.com',
                scope: 'email'
            }).then(() => {
                this.auth = window.gapi.auth2.getAuthInstance();
                this.setState({ isSignedIn: this.auth.isSignedIn.get() })
                this.auth.isSignedIn.listen(this.onAuthChange);
            });
        })
    }

    onAuthChange = () => {
        this.setState({ isSignedIn: this.auth.isSignedIn.get() });
    }

    onSignInClick = () => {
        this.auth.signIn();
    };

    onSignOutClick = () => {
        this.auth.signOut();
    }

    renderAuthButton() {
        if (this.state.isSignedIn === null) {
            return <div>I dont know if we are signed in</div>;
        } else if (this.state.isSignedIn) {
            return (
                <button onClick={this.onSignOutClick}>
                    I am signed In!
                </button>
            )
        } else {
            console.log("로그인 해야함")
            return (
                <button onClick={this.onSignInClick}>
                    I am not signed In!
                </button>
            )
        }
    }


    render() {
        return (
            <div>
                <div>{this.renderAuthButton()}</div>
            </div>
        )
    }
}

export default Login;