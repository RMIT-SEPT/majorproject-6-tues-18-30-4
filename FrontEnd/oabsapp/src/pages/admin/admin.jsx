import React from "react";
import {Layout} from 'antd';
const { Footer, Sider, Content} = Layout;
import Header from "../../components/header/header";
import LeftNav from "../../components/left-nav";

export default class Admin extends React.Component {
    render() {
        return (
            <Layout style={{height : '100%'}}>
                <Sider>
                    <LeftNav/>
                </Sider>
                <Layout>
                    <Header>Header</Header>
                    <Content>Content</Content>
                    <Footer>Footer</Footer>
                </Layout>
            </Layout>
        )
    }
}