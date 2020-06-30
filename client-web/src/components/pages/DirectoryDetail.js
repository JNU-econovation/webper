import React from 'react';
import { connect } from 'react-redux';
import UrlForm from '../UrlForm';
import videoScraper from '../../scrapers/videoScraper';
import wishScraper from '../../scrapers/wishScraper';
import blogScraper from '../../scrapers/blogScraper';
import portalScraper from '../../scrapers/portalScraper';

import { createScrap, fetchScraps } from '../../actions';
import renderScraps from './renderScraps';

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
        if (!inputURL) {
            alert("Url을 입력하세요");
            return;
        }

        let Scraper;
        if (this.props.directory.category === "video") Scraper = videoScraper;
        if (this.props.directory.category === "wishlist") Scraper = wishScraper;
        if (this.props.directory.category === "blog") Scraper = blogScraper;
        if (this.props.directory.category === "portal") Scraper = portalScraper;

        Scraper(inputURL, (err, result) => {
            if (err) {
                console.log("Error: ", err.message);
            } if (result) {
                console.log(result);
                this.props.createScrap(result, this.props.match.params.id, this.props.directory.category);
            }
        })
    }

    renderTitle = () => {
        if (this.props.directory)
            return <h2 className="title">{this.props.directory.directoryTitle}</h2>
        else return null;
    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="scrap-container">
                        {this.renderTitle()}
                        <UrlForm onSubmit={this.onSubmit} />
                        {renderScraps(this.props.directory, this.props.scraps)}
                    </div>
                </div>
            </div>
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