import React from 'react';
import NavList from './NavList';
import { connect } from 'react-redux'
import { fetchDirs } from '../../actions';

class RenderNavList extends React.Component {

    componentDidMount() {
        this.props.fetchDirs(this.props.parentId);
    }

    render() {
        if (this.props.dirs.length === 0)
            return null;
        const dirs = this.props.dirs.filter(dir => dir.parentId == this.props.parentId);
        return (
            <React.Fragment>
                {dirs.map(dir => {
                    return <NavList directory_detail={dir} key={dir.id} />
                })}
            </React.Fragment>
        )
    }

}

const mapStateToProps = (state) => {
    return {
        dirs: Object.values(state.dirs)
    }
}

export default connect(mapStateToProps, { fetchDirs })(RenderNavList);