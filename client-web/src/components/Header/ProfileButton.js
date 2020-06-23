import React from 'react';
import LoginButton from './LoginButton';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

class ProfileButton extends React.Component {

    render() {
        return (
            <div className="profile-container">
                <img className="profile-image" src={this.props.userImage || window.location.origin + "/images/profile.png"} alt="profile image" />
                <div className="profile-content">
                    <div>
                        <Link to="/setting" className="setting">
                            <img className="setting-icon" src={window.location.origin + '/images/setting.png'} alt="setting" />
                            <span>Setting</span>
                        </Link>
                    </div>
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