import history from '../history';
import { connect } from 'react-redux';

export const signIn = (userId) => (dispatch) => {
    // return {
    //     type: "SIGN_IN",
    //     payload: userId
    // };
    dispatch({ type: "SIGN_IN", payload: userId });
    history.push('/')
};

export const signOut = () => {
    return {
        type: "SIGN_OUT"
    }
}