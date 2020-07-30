import React from 'react';
import { connect } from 'react-redux';
import { signIn, signOut } from '../../actions';

class LoginButton extends React.Component {

    componentDidMount() {
        window.gapi.load('client:auth2', () => {
            window.gapi.client.init({
                clientId: process.env.REACT_APP_API_KEY,
                scope: 'email'
            }).then(() => {
                this.auth = window.gapi.auth2.getAuthInstance();
                this.onAuthChange(this.auth.isSignedIn.get());
                this.auth.isSignedIn.listen(this.onAuthChange);
            });
        })
    }

    onAuthChange = (isSignedIn) => {
        if (isSignedIn) {
            this.props.signIn(
                this.auth.currentUser.get().getId(),
                this.auth.currentUser.get().getBasicProfile().getImageUrl(),
                this.auth.currentUser.get().getBasicProfile().getGivenName(),
                this.auth.currentUser.get().wc.access_token
            );
        } else {
            this.props.signOut();
        }
    }

    onSignInClick = () => {
        this.auth.signIn();
    };

    onSignOutClick = () => {
        this.auth.signOut();
    }

    renderAuthButton() {
        if (this.props.isSignedIn === null) {
            return null;
        } else if (this.props.isSignedIn) {
            return (
                <div onClick={this.onSignOutClick}><img className="signout-icon" src={window.location.origin + "/images/signouticon.png"} alt="signout" />Sign Out</div>

            )
        } else {
            return (
                <div onClick={this.onSignInClick}><img className="google-icon" src={window.location.origin + "/images/googleicon.png"} alt="google-icon" />Sign in</div>
            )
        }
    }


    render() {
        return this.renderAuthButton()
    }
}

const mapStateToProps = (state) => {
    return {
        authorization: state.auth.authorization,
        isSignedIn: state.auth.isSignedIn
    };
}

export default connect(mapStateToProps, { signIn, signOut })(LoginButton);
