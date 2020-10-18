import ajax from "./ajax";

const BASE = ''

export const reqLogin = (username, password) => ajax(BASE + '/login',{username:username, password:password} , 'POST');

export const reqRegister = (user) => ajax(BASE + '/register', user, 'POST');

export const reqUpdateUserInfo = (user) => ajax(BASE + '/updateUserInfo', user, 'POST');

export const reqOrders = () => ajax(BASE + '/admin/order/list',{},"POST");

export const reqSaveOrUpdateOrder = (order) => ajax(BASE + '/admin/order/save',order,'POST');

export const reqDelete = (id) => ajax(BASE + '/admin/order/delete', {id:id},'POST');