import React from 'react';
import { connect } from 'react-redux';
import history from '../../history';
import { fetchDir, fetchScraps } from '../../actions';
import renderScraps from './renderScraps';

class Main extends React.Component {

    componentDidMount() {
        const mainDirs = Object.values(this.props.setting.mainDirs);
        if (this.props.setting.mainDirs)
            mainDirs.forEach(dirId => {
                this.props.fetchDir(dirId);
            });

        if (this.props.dirs)
            mainDirs.forEach(dirId => {
                if (this.props.dirs[dirId])
                    this.props.fetchScraps(dirId, this.props.dirs[dirId].category);
            })
    }

    render4Scraps(directory) {
        if (!this.props.scraps) return null;

        const scraps = this.props.scraps.filter(scrap => scrap.directoryId == directory.id);
        const scrapComponents = renderScraps(directory, scraps);
        return scrapComponents;
    }

    renderDir() {
        const dir = Object.values(this.props.setting.mainDirs).map(dir => {
            const directory = this.props.dirs[dir];
            if (!directory) return;

            return (
                <div className="title">
                    {directory.title}
                    {this.render4Scraps(directory)}
                </div>
            )
        })

        return dir;
    }

    render() {
        if (!this.props.isSignedIn)
            history.push('/user_login')



        return (
            <div>
                <div className="container">
                    <div className="scrap-container">
                        <h1 className="title">
                            {this.props.setting.webperTitle || ""}
                        </h1>
                        {this.renderDir()}
                    </div>
                </div>
            </div>
        );
    };
};

const mapStateToProps = (state, ownProps) => {
    return {
        isSignedIn: state.auth.isSignedIn,
        setting: state.setting,
        dirs: state.dirs,
        scraps: Object.values(state.scraps).reverse()
    };
};

export default connect(mapStateToProps, { fetchDir, fetchScraps })(Main);
