import React from 'react';
import ReactDOM from 'react-dom';
import { CookiesProvider } from 'react-cookie'
import { Provider } from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';
import ReduxThunk from 'redux-thunk';

import App from './components/App';

ReactDOM.render(
    <CookiesProvider>
        <Provider store={store}>
            <App />
        </Provider>
    </CookiesProvider>,
    document.querySelector('#root')
);