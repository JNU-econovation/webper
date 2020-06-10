import React from 'react';
import { connect } from 'react-redux';
import history from '../../history';

class Main extends React.Component {
    render() {
        if (!this.props.isSignedIn)
            history.push('/user_login')

        // const { cookies } = this.props;
        // cookies.set('name', 'Young', { path: '/' });

        return (
            <div>
                Webper 로그인 후 보이는 Main페이지 입니다
            </div>
        );
    };
};

const mapStateToProps = (state, ownProps) => {
    // console.log(ownProps.cookies);
    return {
        isSignedIn: state.auth.isSignedIn,
        cookies: ownProps.cookies
    };
};

export default connect(mapStateToProps)(Main);