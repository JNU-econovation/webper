import React from 'react';
import { connect } from 'react-redux';
import UrlForm from '../UrlForm';
import VideoScrap from '../scraps/VideoScrap';
import videoScrapper from '../../scrappers/videoScrapper';
import { createScrap, fetchScraps } from '../../actions';

class DirectoryDetail extends React.Component {
    componentDidMount() {
        this.props.fetchScraps();
    }

    onSubmit = async ({ inputURL }) => {
        const video_detail = await videoScrapper(inputURL);
        this.props.createScrap(video_detail);
    }

    renderScraps() {
        const scrap_component = this.props.scraps.map(scrap => {
            return <VideoScrap video={scrap} key={scrap.id} />
        })
        return scrap_component;
    }

    render() {
        return (
            <div>
                <h2>카테고리 상세 페이지</h2>
                <UrlForm onSubmit={this.onSubmit} />
                {this.renderScraps()}
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        scraps: Object.values(state.scraps)
    }
}

export default connect(mapStateToProps, { createScrap, fetchScraps })(DirectoryDetail);