import youtube from '../api/youtube';

const videoScrappers = async url => {
    const video_id = findVideoId(url);
    const video = await fetchVideo(video_id);
    const video_detail = {
        video_id: video_id,
        video_title: video.title,
        thumbnails: video.thumbnails.medium.url,
        redirection_link: `https://www.youtube.com/watch?v=${video_id}`
    }

    return video_detail
}

const findVideoId = url => {
    let video_id = url.split('v=')[1];
    let ampersandPosition = video_id.indexOf('&');
    if (ampersandPosition !== -1) {
        video_id = video_id.substring(0, ampersandPosition);
    }

    return video_id;
}

const fetchVideo = async video_id => {
    const response = await youtube.get('/videos', {
        params: {
            id: video_id
        }
    })

    return response.data.items[0].snippet;
}

export default videoScrappers;
