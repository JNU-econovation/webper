import React from 'react';
import { Router, Route, Switch } from 'react-router-dom';
import history from '../history';

import Login from './pages/Login';
import Main from './pages/Main';
import DirectoryDetail from './pages/DirectoryDetail';
import Video from './Video';

const App = () => {
    return (
        <div>
            <Router history={history}>
                <div>
                    <Switch>
                        <Route path="/" exact component={Main} />
                        <Route path="/user_login" exact component={Login} />
                        <Route path="/detail" exact component={DirectoryDetail} />
                        <Route path="/video_form" exact component={Video} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
};

export default App;