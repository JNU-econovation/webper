import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import history from '../history';

import Login from './pages/Login';
import Main from './pages/Main';

const App = () => {
    return (
        <div>
            <Router history={history}>
                <div>
                    <Switch>
                        <Route path="/" exact component={Main} />
                        <Route path="/user_login" exact component={Login} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
};

export default App;