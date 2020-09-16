import React from "react";
import { BrowserRouter , Route ,Switch} from 'react-router-dom';

import Login from'./pages/login/login'
import Register from'./pages/register/register'
import Admin from'./pages/admin/admin'
import Home from'./pages/home/home'

export default class App extends React.Component{
    render() {
        return(
            <BrowserRouter>
                <Switch>
                    <Route path='/register' component={Register}></Route>
                    <Route path='/login' component={Login}></Route>
                    <Route path='/home' component={Home}></Route>
                    <Route path='/' component={Login}></Route>
                </Switch>
            </BrowserRouter>
        )
    }
}