import { combineReducers } from 'redux';
import authReducer from './authReducers';
// import urlFormReducer from './urlFormReducer';
import { reducer as formReducer } from 'redux-form';
import scrapReducer from './scrapReducer';

export default combineReducers({
    auth: authReducer,
    form: formReducer,
    scraps: scrapReducer
    // newURL: urlFormReducer
})