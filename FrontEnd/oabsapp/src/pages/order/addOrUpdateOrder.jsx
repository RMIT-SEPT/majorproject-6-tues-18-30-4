import React from "react";
import './index.css'
import {Layout, message} from "antd";
import LeftNav from "../../components/left-nav";
import { Form, Input, InputNumber, Button } from 'antd';
import {reqUpdateUserInfo} from "../../api";
const { Footer, Sider, Content} = Layout;

export default class AddOrUpdateOrder extends React.Component {

    render() {
        const user = JSON.parse(localStorage.getItem("user"))

        const layout = {
            labelCol: { span: 8 },
            wrapperCol: { span: 16 },
        };

        const validateMessages = {
            required: '${label} is required!',
            types: {
                email: '${label} is not validate email!',
                number: '${label} is not a validate number!',
            },
            number: {
                range: '${label} must be between ${min} and ${max}',
            },
        };
        const onFinish = values => {
            console.log(values);
            values.user.id = user.id
            console.log(values)
            reqUpdateUserInfo(values.user).then(response =>{
                const data = response.data
                if (data.code === '1'){
                    message.success("update success");
                    sessionStorage.setItem("user",JSON.stringify(values))
                }else{
                    message.error(data.msg)
                }
            })
        };
        return  (
            <Layout style={{height : '100%'}}>
                <Sider><LeftNav/>
                </Sider>
                <Layout>
                    <Content className="personinfo-content" style={{backgroundColor :'#fff'}}>
                        <Form {...layout} name="nest-messages" onFinish={onFinish} validateMessages={validateMessages}>
                            <input type="hidden" name={"id"} value={user.id}/>
                            <Form.Item name={['user', 'username']} label="username" rules={[{ required: true }]}>
                                <Input defaultValue={user.username}/>
                            </Form.Item>
                            <Form.Item name={['user', 'password']} label="password" rules={[{ required:true }]}>
                                <Input defaultValue={user.password} />
                            </Form.Item>
                            <Form.Item name={['user', 'address']} label="address" rules={[{ required:true,min: 0, max: 99 }]}>
                                <Input defaultValue={user.address}/>
                            </Form.Item>
                            <Form.Item name={['user', 'email']} label="email" rules={[{ required:true, type:"email"}]}>
                                <Input defaultValue={user.email}/>
                            </Form.Item>
                            <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
                                <Button type="primary" htmlType="submit">
                                    Submit
                                </Button>
                            </Form.Item>
                        </Form>
                    </Content>
                    <Footer style={{textAlign:'center'}}>Welcome to Online Appointment Booking System</Footer>
                </Layout>
            </Layout>
        );
    }
}
