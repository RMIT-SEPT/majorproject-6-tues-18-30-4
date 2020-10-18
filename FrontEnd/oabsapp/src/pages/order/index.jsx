import React from "react";
import './index.css'
import {DatePicker, Icon, Layout, message, Modal, Table} from "antd";
import LeftNav from "../../components/left-nav";
import {Form, Input, InputNumber, Button, Card} from 'antd';
import {reqOrders, reqSaveOrUpdateOrder ,reqDelete} from "../../api";
import moment from 'moment';
const {Footer, Sider, Content} = Layout;

export default class Order extends React.Component {

    state = {
        orders: [],
        showStatus: 0,
    }
    order={
        id:"",
        date:"",
        content:""
    }
    //get form value
    formRef = React.createRef();
    getOrders = async () => {
        const result = await reqOrders();
        if (result.status === 200) {
            const orders = result.data
            this.setState({
                orders
            })
        }
    }
    //close modal
    handleCancel = () => {
        this.setState({
                showStatus: 0
            }
        )
    }
    //do add method
    handleAdd = () => {
        const form = this.formRef.current.getFieldValue()
        const {date,order} = form
        const newOrder = {};
        newOrder.date = date
        newOrder.content = order.content
        reqSaveOrUpdateOrder(newOrder).then(response =>{
            const data = response.data
            if (data.code === '0'){
                message.success("save success");
                this.getOrders()
                this.handleCancel()
            }else{
                message.error(data.msg)
            }
        })
    }
    //do update method
    handleUpdate = () => {
        const form = this.formRef.current.getFieldValue()
        const {date,order} = form
        console.log(form)
        const newOrder = {};
        newOrder.date = date
        newOrder.id = this.order.id
        newOrder.content = order.content
        console.log(newOrder)
        reqSaveOrUpdateOrder(newOrder).then(response =>{
            const data = response.data
            if (data.code === '0'){
                message.success("save success");
                this.getOrders()
                this.handleCancel()
            }else{
                message.error(data.msg)
            }
        })
    }

    //show save modal
    showModal = () =>{
        this.setState({showStatus: 1})
    }

    //show delete modal
    showDeleteModal = (order) =>{
        this.order.date = order.date
        this.order.id = order.id
        this.order.content = order.content
        this.setState({showStatus: 3})
    }

    // show update modal
    showUpdateModal = (order) =>{
        console.log(order)
        this.order.date = order.date
        this.order.id = order.id
        this.order.content = order.content
        this.setState({
            showStatus: 2,
        })
    }

    // delete order
    handelDelete = () =>{
        reqDelete(this.order.id).then(response =>{
            const data = response.data
            if (data.code === '0'){
                message.success("delete success");
                this.getOrders()
                this.handleCancel()
            }else{
                message.error(data.msg)
            }
        })
    }

    //date on change
    onChange = (date, dateString)=> {
        console.log(date, dateString);
        console.log(this.formRef.current.setFieldsValue({date:dateString}))
    }

    //page loading implement
    componentDidMount() {
        this.getOrders()
    }

    render() {
        const {orders, showStatus} = this.state
        const order = this.order
        const dateFormat = 'YYYY/MM/DD';
        const extra = (
            <Button onClick={this.showModal}>
                Add
            </Button>
        )
        const layout = {
            labelCol: { span: 8 },
            wrapperCol: { span: 16 },
        };
        const onFinish = values => {
            console.log(values);
        };

        function disabledDate(current) {
            // Can not select days before today and today
            return current && current < moment().endOf('day');
        }
        const columns = [
            {
                title: 'order id',
                dataIndex: 'id',
                key: 'id',
            },
            {
                title: 'order person',
                dataIndex: 'person',
                key: 'person',
            },
            {
                title: 'order date',
                dataIndex: 'date',
                key: 'date',
            },
            {
                title: 'order content',
                dataIndex: 'content',
                key: 'content',
            },
            {
                title: 'operation',
                dataIndex: '',
                render: (order) => (
                    <span>
                         <a onClick={()=>this.showUpdateModal(order)} href="javascript:void(0)">Update</a> &nbsp;&nbsp;
                        <a onClick={()=>this.showDeleteModal(order)}>Delete</a>
                    </span>
                )
            },
        ];
        return (
            <Layout style={{height: '100%'}}>
                <Sider><LeftNav/>
                </Sider>
                <Layout>
                    <Content className="personinfo-content" style={{backgroundColor: '#fff'}}>
                        <Card title="ORDER" extra={extra}>
                            <Table bordered rowKey='_id' dataSource={orders} columns={columns}/>
                            <Modal
                                title="add"
                                visible={showStatus === 1}
                                onOk={this.handleAdd}
                                onCancel={this.handleCancel}
                            >
                                <Form ref={this.formRef} {...layout} name="nest-messages" onFinish={onFinish}>
                                    <Form.Item name={['order', 'date']} label="date" rules={[{ required: true }]}>
                                        <DatePicker onChange={this.onChange}  disabledDate={disabledDate} format={dateFormat} />
                                    </Form.Item>
                                    <Form.Item name={['order', 'content']} label="content" rules={[{ required:true }]}>
                                        <Input />
                                    </Form.Item>
                                </Form>
                            </Modal>
                            <Modal
                                title="update"
                                visible={showStatus === 2}
                                onOk={this.handleUpdate}
                                onCancel={this.handleCancel}
                            >
                                <Form ref={this.formRef} {...layout} name="nest-messages" onFinish={onFinish}>
                                    <input type="hidden" name={"id"} value={order.id}/>
                                    <Form.Item name={['order', 'date']} initialValue={moment(order.date,dateFormat)} label="date" rules={[{ required: true }]}>
                                        <DatePicker onChange={this.onChange}  disabledDate={disabledDate} format={dateFormat} />
                                    </Form.Item>
                                    <Form.Item name={['order', 'content']} initialValue={order.content} label="content" rules={[{ required:true }]}>
                                        <Input/>
                                    </Form.Item>
                                </Form>
                            </Modal>

                            <Modal
                                title="Delete"
                                visible={showStatus === 3}
                                onOk={this.handelDelete}
                                onCancel={this.handleCancel}
                                okText="ok"
                                cancelText="cancel"
                            >
                                <p>Are you sure you want to delete it？</p>
                            </Modal>
                        </Card>
                    </Content>
                    <Footer style={{textAlign: 'center'}}>Welcome to Online Appointment Booking System</Footer>
                </Layout>
            </Layout>
        );
    }
}
