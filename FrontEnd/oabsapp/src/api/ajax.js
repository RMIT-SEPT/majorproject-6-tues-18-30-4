import axios from 'axios'
import Qs from 'qs'

export default function ajax(url, data = {}, method = 'GET') {
    if (method === 'GET') {
        return axios.get(url, {params: data})
    } else {
        return axios.post(url, Qs.stringify(data))
    }
}