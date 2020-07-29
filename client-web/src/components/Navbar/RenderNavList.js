import React from 'react';
import NavList from './NavList';
import { connect } from 'react-redux'
import { fetchRootDirs, fetchDirs } from '../../actions';

class RenderNavList extends React.Component {

    componentDidMount() {
	if (this.props.parentId === 0)
		this.props.fetchRootDirs();
	else
	    this.props.fetchDirs(this.props.parentId);
    }

    render() {
        if (this.props.dirs.length === 0)
            return null;
	    let parentId = this.props.parentId;
	if (parentId === 0)
	    parentId = null;
        const dirs = this.props.dirs.filter(dir => (dir.parentDirectoryId == parentId));
        console.log("in renderNavlist, parentId", this.props.parentId, "dirs:", dirs);
	return (
            <React.Fragment>
                {dirs.map(dir => {
                    return <NavList directory_detail={dir} padding_left={this.props.padding_left} key={dir.id} />
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

export default connect(mapStateToProps, { fetchRootDirs, fetchDirs })(RenderNavList);
