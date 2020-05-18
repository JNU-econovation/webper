import axios from 'axios';

const KEY = 'AIzaSyBnBzbs0ecDf83WunUFVSBXo0Z1XI68iS8';

export default axios.create({
    baseURL: 'https://www.googleapis.com/youtube/v3',
    params: {
        part: 'snippet',
        type: 'video',
        maxResults: 5,
        key: KEY
    }
})