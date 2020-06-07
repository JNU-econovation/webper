import React from 'react'
import CreateButton from './CreateButton';
import { Link } from 'react-router-dom'
import RenderNavList from './RenderNavList';

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
                    <Link to="#" className="directory-title">{this.props.directory_detail.directory_title}</Link>
                    <div className="button-container">
                        <CreateButton id={this.props.directory_detail.id} />
                        <img src={window.location.origin + "/images/more.png"} className="button" alt="more button" />
                    </div>
                </div>
                {this.renderChildren()}
            </div>
        )
    }
}

export default NavList;