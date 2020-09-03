import {instance} from './base.js'


export const requestLogin = params => { return instance.post(`/login`, params) };

export const toRegister = params => { return instance.post(`/toRegister`, params) };

export const getUser = (params) => { return instance.post(`/user/list`, params) };

export const deleteUser = (params) => { return instance.post(`/user/delete/` + params) };

export const getEmployee = (params) => { return instance.get(`/employee`, params) };

export const deleteEmployee = (params) => { return instance.delete(`/employee/` + params) };

export const addOrUpdateEmployee = (params) => { return instance.post(`/employee`, params) };

export const getUserRole = (params) => {return instance.get(`/user/roleList/` + params)};

export const getRole = () => { return instance.post(`/role/list`) };

export const saveUserRoles = (params) => { return instance.post(`/user/saveRoles`, params)};

export const resetUserPassword = (params) => { return instance.post(`/user/restoreInitialPassword/` + params) };

export const addOrUpdateUser = (params) => { return instance.post(`/user/save`, params) };

export const getMenu = (page, rows) => { return instance.post(`/menu/list`, page, rows) };

export const addOrUpdateMenu = (params) => { return instance.post(`/menu/save`, params) };

export const deleteMenu = (params) => { return instance.post(`/menu/delete/` + params) };

export const roleList = ()=>{ return instance.post('/role/list')}

export const findMenuById = params =>{ return instance.get('/role/menuList/'+params)}

export const saveMenu = params => { return instance.post('/role/saveMenu',params) }

export const addRole = params => { return instance.post('/role/save',params) }

export const deleteRole = params =>{ return instance.post('role/delete/'+params) }
