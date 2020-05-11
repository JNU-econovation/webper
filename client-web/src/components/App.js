import React from 'react';
import { Router, Route } from 'react-router-dom';
import history from '../history';

import Login from './pages/Login';
import Main from './pages/Main';

const App = () => {
    return (
        <div>
            <Router history={history}>
                <div>
                    App.js 페이지입니다
                    <Route path="/user_login" exact component={Login} />
                    <Route path="/user_main" exact component={Main} />
                </div>
            </Router>
        </div>
    );
};

export default App;