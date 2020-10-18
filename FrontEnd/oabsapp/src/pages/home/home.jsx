import React from "react";
import './home.css'
import {Layout} from "antd";
import LeftNav from "../../components/left-nav";

const { Footer, Sider, Content} = Layout;
export default class Home extends React.Component {

    render() {
        // const user = JSON.parse(localStorage.getItem("user"))
        return  (
            <Layout style={{height : '100%'}}>
                <Sider><LeftNav/>
                </Sider>
                <Layout>
                    <Content style={{backgroundColor :'#fff'}}>Content</Content>
                    <Footer style={{textAlign:'center'}}>Welcome to Online Appointment Booking System</Footer>
                </Layout>
            </Layout>
        );
    }
}
