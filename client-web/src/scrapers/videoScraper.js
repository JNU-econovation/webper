import youtube from '../apis/youtube';

const videoScrapers = async (url, callback) => {

    if (!isYoutube(url)) {
        alert("youtube 링크가 아닙니다");
        return callback(new Error('not supported url'), null);
    }

    const videoId = findVideoId(url);
    const video = await fetchVideo(videoId);
    const video_detail = {
       // videoId: videoId,
       title: video.title,
        thumbnailURL: video.thumbnails.medium.url,
        redirectionLink: `https://www.youtube.com/watch?v=${videoId}`
    }
    return callback(null, video_detail);
}

const findVideoId = url => {
    let videoId = url.split('v=')[1];
    if (!videoId)
        videoId = url.split('youtu.be/')[1];

    let ampersand_position = videoId.indexOf('&');
    if (ampersand_position !== -1) {
        videoId = videoId.substring(0, ampersand_position);
    }

    return videoId;
}

const fetchVideo = async videoId => {
    const response = await youtube.get('/videos', {
        params: {
            id: videoId
        }
    })

    return response.data.items[0].snippet;
}

const isYoutube = url => {
    let youtube_position1 = url.indexOf('https://www.youtube.com/watch?');
    let youtube_position2 = url.indexOf('https://m.youtube.com/watch?');
    let youtube_position3 = url.indexOf('https://youtu.be');
    return (youtube_position1 !== -1 || youtube_position2 !== -1 || youtube_position3 !== -1);
}

export default videoScrapers;
