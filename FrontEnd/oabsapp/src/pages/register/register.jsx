import React from "react";
import {Form, Input, Button} from 'antd';
import {message} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';

import './register.css';
import {reqRegister} from '../../api/index'

export default class Register extends React.Component {
    formRef = React.createRef();
    handleSubmit = (event) => {
        event.preventDefault()
        const form = this.formRef;
        const values = form.current.getFieldValue()

        reqRegister(values).then(response =>{
            const data = response.data
            if (data.code === '1'){
                message.success("register success");
            }else{
                message.error(data.msg)
            }
        })
    }
    render() {
        return (
            <div className="register">
                <header className="register-header">
                    <h1>Online Appointment Booking System</h1>
                </header>
                <section className="register-content">
                    <h2>register</h2>
                    <Form
                        ref={this.formRef}
                        onSubmitCapture={this.handleSubmit}
                        name="normal_login"
                        className="register-form"
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
                            <Form.Item
                                name="address"
                                rules={[{required: true, message: 'Please input your address!'}]}
                            >
                                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="address"/>
                            </Form.Item>


                            <Form.Item
                                name="email"
                                rules={[{required: true, message: 'Please input your email!'}]}
                            >
                                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="email"/>
                            </Form.Item>


                        <Form.Item>
                            <Button type="primary" htmlType="submit" className="register-form-button">
                                Register
                            </Button>
                            Or <a href="/login">to login now!</a>
                        </Form.Item>
                    </Form>
                </section>
            </div>
        );
    }
}
