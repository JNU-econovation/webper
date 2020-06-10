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
    const { userId } = getState().auth;
    const response = await server.post('/dirs', { ...directory_detail, userId });
    dispatch({ type: "CREATE_DIR", payload: response.data });
    history.goBack();
}

export const fetchDirs = parentId => async dispatch => {
    const response = await server.get(`/dirs?parentId=${parentId}`);

    dispatch({ type: "FETCH_DIRS", payload: response.data });
}

export const fetchDir = id => async dispatch => {
    const response = await server.get(`/dirs/${id}`);
    dispatch({ type: "FETCH_DIR", payload: response.data });
}

export const deleteDir = id => async dispatch => {
    const response = await server.delete(`/dirs/${id}`);

    dispatch({ type: "DELETE_DIR", payload: id });
    history.goBack();
}

export const editDir = (id, formValues) => async dispatch => {
    const response = await server.patch(`/dirs/${id}`, formValues);

    dispatch({ type: "EDIT_DIR", payload: response.data });
    history.goBack();
}

export const createScrap = (video_detail, categoryId) => async (dispatch, getState) => {
    const { userId } = getState().auth;
    const response = await server.post('/videos', { ...video_detail, userId, categoryId });
    // /video는 나중에 카테고리 타입을 받아와 ``신택스로 바꿔서 재사용할 것;

    dispatch({ type: "CREATE_SCRAP", payload: response.data });
    history.push(`/detail/${categoryId}`);
}

export const fetchScraps = (categoryId) => async (dispatch) => {
    const response = await server.get(`/videos?categoryId=${categoryId}`);

    dispatch({ type: "FETCH_SCRAPS", payload: response.data });
    history.push(`/detail/${categoryId}`);
}

export const editScrap = (id, formValues, categoryId) => async dispatch => {
    const response = await server.patch(`/videos/${id}`, formValues);
    // /video는 나중에 카테고리 타입을 받아와 ``신택스로 바꿔서 재사용할 것;

    dispatch({ type: "EDIT_SCRAP", payload: response.data });
    history.push(`/detail/${categoryId}`);
}