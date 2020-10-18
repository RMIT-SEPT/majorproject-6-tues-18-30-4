import React from "react";
import {Form, Input, Button, Radio} from 'antd';
import {message} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';

import './register.css';
import {reqRegister} from '../../api/index'

export default class Register extends React.Component {
    state = {
        value3: 'Admin',
    };
    formRef = React.createRef();
    onChange3 = e => {
        this.setState({
            value3: e.target.value,
        });
    };
    handleSubmit = (event) => {
        const form = this.formRef;
        const values = form.current.getFieldValue()
        const error = form.current.getFieldsError()
        reqRegister(values).then(response => {
            const data = response.data
            if (data.code === '1') {
                message.success("register success");
            } else {
                message.error(data.msg)
            }
        })

    }

    render() {
        const options = [
            {label: 'Admin', value: 'Admin'},
            {label: 'User', value: 'User'},
            {label: 'Employee', value: 'Employee'},
        ];


        const {value3} = this.state

        return (
            <div className="register">
                <header className="register-header">
                    <h1>Online Appointment Booking System</h1>
                </header>
                <section className="register-content">
                    <h2>register</h2>
                    <Form
                        ref={this.formRef}
                        onFinish={this.handleSubmit}
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
                            rules={[{required: true,type: 'email' , message: 'Please input your email!'}]}
                        >
                            <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="email"/>
                        </Form.Item>

                        <Radio.Group
                            options={options}
                            onChange={this.onChange3}
                            value={value3}
                            optionType="button"
                        />
                        <br/>
                        <br/>
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
