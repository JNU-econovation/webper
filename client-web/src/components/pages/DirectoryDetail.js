import React from 'react';
import { connect } from 'react-redux';
import UrlForm from '../UrlForm';
import VideoScrap from '../scraps/VideoScrap';
import WishScrap from '../scraps/WishScrap';
import videoScraper from '../../scrapers/videoScraper';
import wishScraper from '../../scrapers/wishScraper';
import portalScraper from '../../scrapers/portalScraper';

import { createScrap, fetchScraps } from '../../actions';
import PortalScrap from '../scraps/PortalScrap';

class DirectoryDetail extends React.Component {
    componentDidMount() {
        this.props.fetchScraps(this.props.match.params.id, this.props.match.params.category);
    }

    UNSAFE_componentWillReceiveProps(nextProps) {
        if (this.props.match.params.id !== nextProps.match.params.id) {
            this.props.fetchScraps(nextProps.match.params.id, nextProps.match.params.category);
        }
    }

    onSubmit = ({ inputURL }) => {
        let Scraper;
        if (this.props.directory.category === "video") Scraper = videoScraper;
        if (this.props.directory.category === "wishlist") Scraper = wishScraper;
        if (this.props.directory.category === "portal") Scraper = portalScraper;

        Scraper(inputURL, (err, result) => {
            if (err) {
                console.log("Error: ", err.message);
            } if (result) {
                this.props.createScrap(result, this.props.match.params.id, this.props.directory.category);
            }
        })
    }

    renderScraps() {
        let scrap_component;
        if (this.props.directory)
            switch (this.props.directory.category) {
                case "video":
                    scrap_component = this.props.scraps.map(scrap => {
                        return <VideoScrap video={scrap} key={scrap.id} />
                    });
                    break;
                case "wishlist":
                    scrap_component = this.props.scraps.map(scrap => {
                        return <WishScrap wish={scrap} key={scrap.id} />
                    });
                    break;
                case "portal":
                    scrap_component = this.props.scraps.map(scrap => {
                        return <PortalScrap portal={scrap} key={scrap.id} />
                    });
                    break;
                default: return scrap_component;
            }
        return scrap_component;
    }

    renderTitle = () => {
        if (this.props.directory)
            return <h2 className="title">{this.props.directory.directoryTitle}</h2>
        else return null;
    }

    render() {
        return (
            < div >
                <div className="container">
                    <div className="scrap-container">
                        {this.renderTitle()}
                        <UrlForm onSubmit={this.onSubmit} />
                        {this.renderScraps()}
                    </div>
                </div>
            </div >
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        scraps: Object.values(state.scraps).reverse(),
        directory: state.dirs[ownProps.match.params.id]
    }
}

export default connect(mapStateToProps, { createScrap, fetchScraps })(DirectoryDetail);