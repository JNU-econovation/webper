const INITIAL_STATE = {
    isSignedIn: null,
    userId: null,
    userImage: null,
    userName: null,
    authorization: null
};

export default (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'SIGN_IN':
            return { ...state, isSignedIn: true, userId: action.payload.userId, userImage: action.payload.userImage, userName: action.payload.userName, authorization: action.payload.authorization };
        case 'SIGN_OUT':
            return { ...state, isSignedIn: false, userId: null };
        default:
            return state;
    }
}
