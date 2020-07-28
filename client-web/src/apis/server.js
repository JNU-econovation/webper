import axios from 'axios';

export default axios.create({
	baseURL: process.env.REACT_APP_BACK_SERVER,
	headers: { 'Authorization': localStorage.getItem('Authorization')}
})

