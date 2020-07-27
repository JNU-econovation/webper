import server from '../apis/server';
import history from '../history';
import { formValues } from 'redux-form';

export const signIn = (userId, userImage, username, token) => async (dispatch, getState) => {
    console.log(userId, userImage, username, token);
    const response = await server.post('login/google', { access_token: token });
    console.log("response:", response);
    // dispatch({ type: "SIGN_IN", payload: { userId, userImage, username } });
    dispatch({ type: "SIGN_IN", payload: { userId, userImage, username, authorization: response.Authrization } });
    history.push('/');
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

export const fetchAllDirs = () => async dispatch => {
    const response = await server.get('/dirs');

    dispatch({ type: "FETCH_ALL_DIRS", payload: response.data });
}

export const fetchDirs = parentId => async dispatch => {
    //if (parentId === 0)
    //   parentId = null;
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
    history.push('/');
}

export const editDir = (id, formValues) => async dispatch => {
    const response = await server.patch(`/dirs/${id}`, formValues);

    dispatch({ type: "EDIT_DIR", payload: response.data });
    history.goBack();
}

export const createScrap = (video_detail, directoryId, category) => async (dispatch, getState) => {
    const { userId } = getState().auth;
    const response = await server.post(`/${category}s`, { ...video_detail, userId, directoryId });

    dispatch({ type: "CREATE_SCRAP", payload: response.data });
    history.push(`/detail/${directoryId}/${category}`);
}

export const fetchScraps = (directoryId, category) => async (dispatch) => {
    const response = await server.get(`/${category}s?directoryId=${directoryId}`);

    dispatch({ type: "FETCH_SCRAPS", payload: response.data });
    // history.push(`/detail/${directoryId}/${category}`);
}

export const editScrap = (id, formValues, directoryId, category) => async dispatch => {
    const response = await server.patch(`/${category}s/${id}`, formValues);

    dispatch({ type: "EDIT_SCRAP", payload: response.data });
    history.push(`/detail/${directoryId}/${category}`);
}

export const setting = formValues => async (dispatch, getState) => {
    const { webperTitle, ...mainDirs } = formValues;
    console.log(webperTitle, mainDirs);

    // const { userId } = getState().auth;
    // let response = await server.patch(`/setting?userId=${userId}`, webperTitle, mainDirs);

    dispatch({ type: "SETTING", payload: { webperTitle, mainDirs } });
    history.goBack();
}