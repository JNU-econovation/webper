import React from "react";
import { Router, Route, Switch } from "react-router-dom";
import { withCookies } from "react-cookie";
import history from "../history";

import Login from "./pages/Login";
import Header from "./Header/Header";
import Main from "./pages/Main";
import DirectoryDetail from "./pages/DirectoryDetail";
import Navbar from "../components/Navbar/Navbar";
import CreateDirectory from "../components/Navbar/CreateDirectory";
import DeleteDirectory from "../components/Navbar/DeleteDirectory";
import EditDirectory from "../components/Navbar/EditDirectory";
import Setting from "./Header/Setting";
import Footer from "./Footer/Footer";

const App = (props) => {
  return (
    <div>
      <Router history={history}>
        <Header />
        <Navbar />
        <Switch>
          <div className="main-body">
            <Route path="/" exact component={Main} />
            <Route path="/user_login" render={() => (<Login cookies={props.cookies} />)} />
            <Route path="/detail/:id/:category" exact component={DirectoryDetail} />
            <Route path="/new/:id" exact component={CreateDirectory} />
            <Route path="/delete/:id" exact component={DeleteDirectory} />
            <Route path="/edit/:id" exact component={EditDirectory} />
            <Route path="/setting" exact component={Setting} />
          </div>
        </Switch>
        <hr />
        <Footer />
      </Router>
    </div>
  );
};

export default withCookies(App);
