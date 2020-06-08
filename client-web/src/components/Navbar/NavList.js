import React from 'react'
import { Link } from 'react-router-dom'
// import history from '../../history';
import RenderNavList from './RenderNavList';
import CreateButton from './CreateButton';
import MoreButton from './MoreButton';

class NavList extends React.Component {
    state = { dropdown: false }

    handleDrop = () => {
        if (this.state.dropdown === false)
            this.setState({ dropdown: true });
        else
            this.setState({ dropdown: false });

    }

    renderChildren() {
        if (this.state.dropdown === false)
            return null;
        else {
            return <RenderNavList parentId={this.props.directory_detail.id} />
        }
    }

    getClassName() {
        return (this.state.dropdown ? "down" : '');
    }

    render() {
        return (
            <div className="navlist">
                <div className="inner-container">
                    <img onClick={this.handleDrop} src={window.location.origin + "/images/dropdown.png"} alt="dropdownicon" className={`dropdown-icon ${this.getClassName()}`} />
                    <Link to={`/detail/${this.props.directory_detail.id}`} className="directory-title">{this.props.directory_detail.directory_title}</Link>
                    <div className="button-container">
                        <CreateButton id={this.props.directory_detail.id} />
                        <MoreButton id={this.props.directory_detail.id} />
                    </div>
                </div>
                {this.renderChildren()}
            </div>
        )
    }
}

export default NavList;