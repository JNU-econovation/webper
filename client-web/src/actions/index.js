import server from '../apis/server';
import history from '../history';

export const signIn = (userId) => (dispatch) => {
    dispatch({ type: "SIGN_IN", payload: userId });
    history.push('/');
};

export const signOut = () => {
    return {
        type: "SIGN_OUT"
    }
}

export const createScrap = video_detail => async (dispatch, getState) => {
    const { userId } = getState().auth;
    // console.log({ video_detail, userId });
    const response = await server.post('/videos', { ...video_detail, userId });
    // /video는 나중에 카테고리 타입을 받아와 ``신택스로 바꿔서 재사용할 것;

    dispatch({ type: "CREATE_SCRAP", payload: response.data });
    history.push('/detail');
}