import React from 'react';
import { connect } from 'react-redux';
import UrlForm from '../UrlForm';
import VideoScrap from '../scraps/VideoScrap';
import videoScrapper from '../../scrappers/videoScrapper';
import { createScrap, fetchScraps } from '../../actions';

class DirectoryDetail extends React.Component {
    componentDidMount() {
        this.props.fetchScraps(this.props.match.params.id);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.match.params.id !== nextProps.match.params.id) {
            this.props.fetchScraps(nextProps.match.params.id);
        }
    }

    onSubmit = ({ inputURL }) => {
        let Scrapper;
        if (this.props.directory.category === "video") Scrapper = videoScrapper;

        Scrapper(inputURL, (err, result) => {
            if (err) {
                console.log("Error: ", err.message);
            } if (result) {
                this.props.createScrap(result, this.props.match.params.id);
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
                default: return scrap_component;
            }
        // const scrap_component = this.props.scraps.map(scrap => {
        //     return <VideoScrap video={scrap} key={scrap.id} />
        // })
        // return scrap_component;
    }

    renderTitle = () => {
        if (this.props.directory)
            return <h2 className="title">{this.props.directory.directory_title}</h2>
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