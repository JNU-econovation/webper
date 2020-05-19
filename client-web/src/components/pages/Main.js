import React from 'react';
import { connect } from 'react-redux';
import history from '../../history';
import { Link } from 'react-router-dom';
import LoginButton from '../buttons/LoginButton';

class Main extends React.Component {
    render() {
        if (!this.props.isSignedIn)
            history.push('/user_login')

        return (
            <div>
                Webper 로그인 후 보이는 Main페이지 입니다
                <div>
                    <Link to="/detail">to Detail</Link>
                </div>
            </div>
        );
    };
};

const mapStateToProps = (state) => {
    return { isSignedIn: state.auth.isSignedIn };
};

export default connect(mapStateToProps)(Main);