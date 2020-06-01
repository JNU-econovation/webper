import server from '../apis/server';
import history from '../history';

export const signIn = (userId) => (dispatch) => {
    dispatch({ type: "SIGN_IN", payload: userId });
    history.goBack();
};

export const signOut = () => {
    return {
        type: "SIGN_OUT"
    }
}

export const createDir = directory_detail => async (dispatch, getState) => {
    console.log(directory_detail);
    const { userId } = getState().auth;
    const response = await server.post('/dirs', { ...directory_detail, userId });
    dispatch({ type: "CREATE_DIR", payload: response.data });
    history.goBack();
}

export const fetchDirs = directory_id => async (dispatch) => {
    const response = await server.get('/dirs');

    dispatch({ type: "FETCH_DIRS", payload: response.data });
    history.goBack();
}

export const createScrap = video_detail => async (dispatch, getState) => {
    const { userId } = getState().auth;
    // console.log({ video_detail, userId });
    const response = await server.post('/videos', { ...video_detail, userId });
    // /video는 나중에 카테고리 타입을 받아와 ``신택스로 바꿔서 재사용할 것;

    dispatch({ type: "CREATE_SCRAP", payload: response.data });
    history.push('/detail');
}

export const fetchScraps = () => async (dispatch) => {
    const response = await server.get(`/videos`);

    dispatch({ type: "FETCH_SCRAPS", payload: response.data });
    history.push('/detail');
}