import React from "react";
import { BrowserRouter , Route ,Switch} from 'react-router-dom';

import Login from'./pages/login/login'
import Register from'./pages/register/register'
import Home from'./pages/home/home'
import PersonInfo from'./pages/person-info'
import Order from "./pages/order";
import AddOrUpdateOrder from "./pages/order/addOrUpdateOrder";

export default class App extends React.Component{
    render() {
        return(
            <BrowserRouter>
                <Switch>
                    <Route path='/personinfo' component={PersonInfo}></Route>
                    <Route path='/register' component={Register}></Route>
                    <Route path='/order' component={Order}></Route>
                    <Route path='/addOrUpdateOrder' component={AddOrUpdateOrder}></Route>
                    <Route path='/login' component={Login}></Route>
                    <Route path='/home' component={Home}></Route>
                    <Route path='/' component={Login}></Route>
                </Switch>
            </BrowserRouter>
        )
    }
}