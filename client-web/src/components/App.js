import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import history from '../history';

import Login from './pages/Login';
import Header from './Header';
import Main from './pages/Main';
import DirectoryDetail from './pages/DirectoryDetail';

const App = () => {
    return (
        <div>
            <Router history={history}>
                <div>
                    <Header />
                    <Switch>
                        <Route path="/" exact component={Main} />
                        <Route path="/user_login" exact component={Login} />
                        <Route path="/detail" exact component={DirectoryDetail} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
};

export default App;