import React from 'react';

const VideoScrap = ({ video }) => {
    return (
        <div>
            <a href={video.redirection_link} target="_blank" rel="noopener noreferrer">
                <img alt={video.video_title} src={video.thumbnails} style={{ width: "300px" }} />
            </a>
            <div>
                <div>{video.video_title}</div>
            </div>
        </div>
    )
}

export default VideoScrap;