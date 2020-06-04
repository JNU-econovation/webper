import { combineReducers } from 'redux';
import authReducer from './authReducers';
// import urlFormReducer from './urlFormReducer';
import { reducer as formReducer } from 'redux-form';
import scrapReducer from './scrapReducer';
import dirReducer from './dirReducer';

export default combineReducers({
    auth: authReducer,
    form: formReducer,
    scraps: scrapReducer,
    dirs: dirReducer
})