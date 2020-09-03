import axios from 'axios';
import qs from 'qs'
import {baseHost} from '../utils/config.js'

const instance = axios.create({
  baseURL:baseHost,
  timeout:5000,
})
instance.defaults.withCredentials  = true

instance.interceptors.request.use((config) => {
  if (config.method === 'post') {
    config.data = qs.stringify(config.data)
  }
  return config
})


instance.interceptors.response.use(response => {
  console.log(response)
  return response
}, err => {
  app.$confirm('User expired, return to login', 'Tips', {
    confirmButtonText: 'ok',
    cancelButtonText: 'cancel',
    type: 'warning'
  }).then(() => {
    app.$router.push({name: 'login'})
  }).catch(()=>{
    app.$router.push({name: 'login'})
  })
  return Promise.reject(err)
})

export {instance}
