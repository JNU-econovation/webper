import React from 'react';
import LoginButton from './LoginButton';
import { connect } from 'react-redux';

class ProfileButton extends React.Component {

    render() {
        return (
            <div className="profile-container">
                <img className="profile-image" src={this.props.userImage || "/images/profile.png"} alt="profile image" />
                <div className="profile-content">
                    <div>Setting</div>
                    <LoginButton />
                </div>
            </div>
        )
    }
}

const mapStateToProps = (state) => {
    return { userImage: state.auth.userImage };
}

export default connect(mapStateToProps)(ProfileButton);