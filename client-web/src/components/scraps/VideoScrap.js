import React from 'react';

const VideoScrap = ({ video }) => {
    return (
        <div className="scrap-item">
            <a href={video.redirection_link} target="_blank" rel="noopener noreferrer">
                <img alt={video.video_title} src={video.thumbnails} />
            </a>
            <div className="detail-container">
                <div>{video.video_title}</div>
            </div>
        </div>
    )
}

export default VideoScrap;