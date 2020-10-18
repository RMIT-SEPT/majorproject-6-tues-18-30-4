import React from "react";
import { Menu, Button } from 'antd';
import {Link} from 'react-router-dom'
import './index.css'
import {
    PieChartOutlined,
} from '@ant-design/icons';

export default class LeftNav extends React.Component {
    render() {
        return (
            <div className="left-nav">
                <div className="header">
                    <h1>OABS</h1>
                </div>
                <div className="left-menu">
                    <Menu
                        defaultSelectedKeys={['1']}
                        defaultOpenKeys={['sub1']}
                        mode="inline"
                        theme="dark"
                    >

                            <Menu.Item key="/personinfo" icon={<PieChartOutlined />}>
                                <Link to={"/personinfo"}>
                                person info
                                </Link>
                            </Menu.Item>
                        <Menu.Item key="/order" icon={<PieChartOutlined />}>
                            <Link to={"/order"}>
                                order
                            </Link>
                        </Menu.Item>

                    </Menu>
                </div>
            </div>
        )
    }
}