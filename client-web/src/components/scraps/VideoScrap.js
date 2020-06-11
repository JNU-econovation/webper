import _ from 'lodash';
import React, { useState } from 'react';
import EditScrap from './EditScrap';

const VideoScrap = ({ video }) => {

    const [onEdit, setOnEdit] = useState(false);

    if (onEdit === false) {
        return (
            <div className="scrap-item">
                <div className="scrap-edit-button-container">
                    <img className="scrap-edit-button" src={window.location.origin + "/images/more.png"} alt="scrap edit button" onClick={() => setOnEdit(true)} />
                </div>
                <a href={video.redirectionLink} target="_blank" rel="noopener noreferrer">
                    <img className="scrap-img" src={video.thumbnails} alt={video.videoTitle} />
                </a>
                <div className="detail-container">
                    <div>{video.videoTitle}</div>
                </div>
            </div>
        )
    } else if (onEdit === true) {
        const editable_info_name = { videoTitle: "title", redirectionLink: "url" }
        return (
            <EditScrap
                video_detail={video}
                initialValues={_.pick(video, 'videoTitle', 'redirectionLink')}
                image={video.thumbnails}
                editable_info_name={editable_info_name}
                saveCallback={() => setOnEdit(false)}
            />
        )
    } else return <div>Error</div>
}

export default VideoScrap;