import React from "react";
import {Form, Input, Button} from 'antd';
import {message} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import { Redirect } from "react-router-dom";

import './login.css';
import {reqLogin} from '../../api/index'

export default class Login extends React.Component {
    constructor(props){
        super(props);
        this.state={
            login:false
        }
    }

    formRef = React.createRef();
    handleSubmit = (event) => {
        event.preventDefault()
        const form = this.formRef;
        const values = form.current.getFieldValue()
        const {username, password} = values;
        reqLogin(username,password).then(response =>{
            const data = response.data
            if (data.code === '1'){
                message.success("login success");
                localStorage.setItem("user",JSON.stringify(data.data));
                this.setState({
                    login :true
                })
            }else{
                message.error(data.msg)
            }
        })
    }
    render() {
        if(this.state.login){
            return  <Redirect to="/home"/>
        }
        return (
            <div className="login">
                <header className="login-header">
                    <h1>Online Appointment Booking System</h1>
                </header>
                <section className="login-content">
                    <h2>login</h2>
                    <Form
                        ref={this.formRef}
                        onSubmitCapture={this.handleSubmit}
                        name="normal_login"
                        className="login-form"
                        initialValues={{remember: true}}
                    >
                        <Form.Item
                            name="username"
                            rules={[{required: true, message: 'Please input your Username!'}]}
                        >
                            <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
                        </Form.Item>
                        <Form.Item
                            name="password"
                            rules={[{required: true, message: 'Please input your Password!'}]}
                        >
                            <Input
                                prefix={<LockOutlined className="site-form-item-icon"/>}
                                type="password"
                                placeholder="Password"
                            />
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit" className="login-form-button">
                                Log in
                            </Button>
                            Or <a href="/register">register now!</a>
                        </Form.Item>
                    </Form>
                </section>
            </div>
        );
    }
}
