import _ from 'lodash';
import React from 'react';
import EditScrap from './EditScrap';

class BlogScrap extends React.Component {
    state = { onEdit: false };

    editModeOn = () => {
        const id = this.props.blog.id;
        this.setState({ onEdit: true })
        document.querySelector('body').addEventListener('click', this.dismiss);
        document.querySelector(`#scrap${id}`).addEventListener('click', this.stopPropagation)
    }

    editModeOff = () => {
        const id = this.props.blog.id;
        this.setState({ onEdit: false })
        document.querySelector('body').removeEventListener('click', this.dismiss);
        document.querySelector(`#scrap${id}`).removeEventListener('click', this.stopPropagation)
    }

    dismiss = () => {
        this.editModeOff();
    }

    stopPropagation = e => {
        e.stopPropagation();
    }

    renderContents() {
        if (this.state.onEdit === true) {
            const editable_info_name = { title: "title", thumbnails: "thumbnails", description: "description", redirectionLink: "url" }
            return (
                <React.Fragment>
                    <EditScrap
                        category='blog'
                        scrap_detail={this.props.blog}
                        initialValues={_.pick(this.props.blog, 'title', 'thumbnails', 'description', 'redirectionLink')}
                        image={this.props.blog.thumbnails}
                        editable_info_name={editable_info_name}
                        saveCallback={() => { this.setState({ onEdit: false }); this.editModeOff(); }}
                    />
                </React.Fragment>
            )
        }

        return (
            <React.Fragment>
                <div className="scrap-edit-button-container">
                    <img onClick={this.editModeOn} className="scrap-edit-button" src={window.location.origin + "/images/more.png"} alt="scrap edit button" />
                </div>
                <a href={this.props.blog.redirectionLink} target="_blank" rel="noopener noreferrer">
                    <img className="scrap-img" src={this.props.blog.thumbnails || window.location.origin + "/images/emptyImage.png"} alt={this.props.blog.title} />
                </a>
                <div className="detail-container">
                    <div>{this.props.blog.title}</div>
                    <div>{this.props.blog.description}</div>
                </div>
            </React.Fragment>
        )

    }

    render() {
        return (
            <div id={`scrap${this.props.blog.id}`} className="scrap-item"  >
                {this.renderContents()}
            </div>
        )

    }
}

export default BlogScrap;