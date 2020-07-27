import axios from 'axios';

export default axios.create({
    baseURL: process.env.REACT_APP_BACK_SERVER,
    headers: { 'Access-Control-Allow-Origin': 'http://www.webper.net:3000' }
})