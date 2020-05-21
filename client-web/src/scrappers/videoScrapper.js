import youtube from '../apis/youtube';

const videoScrappers = async (url, callback) => {

    if (!isYoutube(url)) {
        alert("youtube 링크가 아닙니다");
        return callback(new Error('not supported url'), null);
    }

    const video_id = findVideoId(url);
    const video = await fetchVideo(video_id);
    const video_detail = {
        video_id: video_id,
        video_title: video.title,
        thumbnails: video.thumbnails.medium.url,
        redirection_link: `https://www.youtube.com/watch?v=${video_id}`
    }
    return callback(null, video_detail);
}

const findVideoId = url => {
    let video_id = url.split('v=')[1];
    if (!video_id)
        video_id = url.split('youtu.be/')[1];

    let ampersand_position = video_id.indexOf('&');
    if (ampersand_position !== -1) {
        video_id = video_id.substring(0, ampersand_position);
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

const isYoutube = url => {
    let youtube_position1 = url.indexOf('https://www.youtube.com/watch?');
    let youtube_position2 = url.indexOf('https://youtu.be');
    return (youtube_position1 !== -1 || youtube_position2 !== -1);
}

export default videoScrappers;
