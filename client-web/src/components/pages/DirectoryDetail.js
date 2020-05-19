import React from 'react';
import { connect } from 'react-redux';
import UrlForm from '../UrlForm';
import VideoScrap from '../scraps/VideoScrap';
import videoScrapper from '../../scrappers/videoScrapper';
import { createScrap } from '../../actions';

class DirectoryDetail extends React.Component {
    onSubmit = async ({ inputURL }) => {
        const video_detail = await videoScrapper(inputURL);
        this.props.createScrap(video_detail);
    }

    render() {
        return (
            <div>
                <h2>카테고리 상세 페이지</h2>
                <UrlForm onSubmit={this.onSubmit} />
                <VideoScrap />
                <VideoScrap />
                <VideoScrap />
            </div>
        );
    }
}

export default connect(null, { createScrap })(DirectoryDetail);