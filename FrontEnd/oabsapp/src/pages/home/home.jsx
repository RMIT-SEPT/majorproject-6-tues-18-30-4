import React from "react";
import './home.css'

export default class Home extends React.Component {

    render() {
        const user = JSON.parse(localStorage.getItem("user"))
        return (
            <div>
                <h1>name: {user.username}</h1>
                <h1>address: {user.address}</h1>
                <h1>email: {user.email}</h1>
            </div>
        );
    }
}
