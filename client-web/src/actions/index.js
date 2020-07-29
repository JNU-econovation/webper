import server from '../apis/server';
import history from '../history';
import { formValues } from 'redux-form';

const getHeader = () => {
	let Authorization = localStorage.getItem('Authorization');
	return {
		headers: {
			'Authorization': Authorization
		}
	}
}

export const signIn = (userId, userImage, username, token) => async (dispatch, getState) => {
	const response = await server.post('login/google', { access_token: token });
    	localStorage.setItem('Authorization', response.data.Authorization);
	dispatch({ type: "SIGN_IN", payload: { userId, userImage, username } });
    history.push('/');
};

export const signOut = () => {
    	localStorage.removeItem('Authorization');
    return {
        type: "SIGN_OUT"
    }
	history.push('/');
}

export const createDir = directory_detail => async (dispatch) => {
    if (directory_detail.parentDirectoryId == 0)
	directory_detail.parentDirectoryId = null;
	const response = await server.post('/directory', { ...directory_detail }, getHeader());
	dispatch({ type: "CREATE_DIR", payload: response.data });
    history.push('/');
}

export const fetchAllDirs = () => async dispatch => {
    const response = await server.get('/directory', getHeader());

    dispatch({ type: "FETCH_ALL_DIRS", payload: response.data });
}

export const fetchRootDirs = () => async dispatch => {
	const response = await server.get('/root-directory', getHeader());
	dispatch({ type: "FETCH_DIRS", payload: response.data });
	history.goBack();
}

export const fetchDirs = parentDirectoryId => async dispatch => {
    if (parentDirectoryId == 0)
       parentDirectoryId = null;
    const response = await server.get(`/directory?id=${parentDirectoryId}`, getHeader());
    dispatch({ type: "FETCH_DIRS", payload: response.data.childDirectories });
}

export const fetchDir = id => async dispatch => {
    const response = await server.get(`/directory?id=${id}`, getHeader());
    dispatch({ type: "FETCH_DIR", payload: response.data });
}

export const deleteDir = id => async dispatch => {
	console.log("deleteDir!!!!");
    const response = await server.delete(`/directory/${id}`, getHeader());

    dispatch({ type: "DELETE_DIR", payload: id });
    history.push('/');
}

export const editDir = (id, formValues) => async dispatch => {
    const response = await server.patch(`/directory/${id}`, formValues);

    dispatch({ type: "EDIT_DIR", payload: response.data });
    history.goBack();
}

export const createScrap = (video_detail, directoryId, category) => async dispatch => {
    const response = await server.post(`/component/${category.toLowerCase()}`, { ...video_detail, directoryId, category: category }, getHeader());
	console.log("in CreateScrap", response.data);
    dispatch({ type: "CREATE_SCRAP", payload: response.data });
    history.push(`/detail/${directoryId}/${category}`);
}

export const fetchScraps = (directoryId) => async dispatch => {
    const response = await server.get(`/directory/${directoryId}/components`);

	console.log("in fetchScraps", response.data);
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
