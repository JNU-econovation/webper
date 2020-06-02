import React from 'react'
import CreateButton from './CreateButton';
import Link from 'react-router-dom'
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
            return null
        else
            return <RenderNavList parentId={this.props.directory_detail.id} />
    }

    getClassName() {
        return (this.state.dropdown ? "down" : '');
    }

    render() {
        return (
            <div>
                <img onClick={this.handleDrop} src="images/dropdown.png" alt="dropdownicon" className={`dropdown-icon ${this.getClassName()}`} />
                <a herf="#">{this.props.directory_detail.directory_title}</a>
                <button className="rightend">...</button>
                <CreateButton id={this.props.directory_detail.id} />
                {this.renderChildren()}
            </div>
        )
    }
}

export default NavList;