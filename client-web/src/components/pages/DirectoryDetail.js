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

    onSubmit = ({ inputURL }) => {
        videoScrapper(inputURL, (err, result) => {
            if (err) {
                console.log("Error: ", err.message);
            } if (result) {
                this.props.createScrap(result);
            }
        })
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
                <div className="container">
                    <div className="scrap-container">
                        <h2 className="title">카테고리 상세 페이지</h2>
                        <UrlForm onSubmit={this.onSubmit} />
                        {this.renderScraps()}
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        scraps: Object.values(state.scraps).reverse()
    }
}

export default connect(mapStateToProps, { createScrap, fetchScraps })(DirectoryDetail);