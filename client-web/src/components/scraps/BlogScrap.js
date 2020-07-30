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

    onDelete = () => {
        this.props.onDelete(this.props.blog.id);
    }

    renderButtons = () => {
    	if (this.props.main !== "true")
	    return (
                <div className="scrap-button-container">
		    <img onClick={this.onDelete} className="scrap-button" src={window.location.origin + "/images/trash.png"} alt="scrap delete button" />
                    <img onClick={this.editModeOn} className="scrap-button" src={window.location.origin + "/images/more.png"} alt="scrap edit button" />
                </div>
	    )
	 return null;
    }

    renderContents() {
        if (this.state.onEdit === true) {
            const editable_info_name = { title: "title", thumbnailURL: "thumbnailURL", description: "description", redirectionLink: "url" }
            return (
                <React.Fragment>
                    <EditScrap
                        category='BLOG'
                        scrap_detail={this.props.blog}
                        initialValues={_.pick(this.props.blog, 'title', 'thumbnailURL', 'description', 'redirectionLink')}
                        image={this.props.blog.thumbnailURL}
                        editable_info_name={editable_info_name}
                        saveCallback={() => { this.setState({ onEdit: false }); this.editModeOff(); }}
                    />
                </React.Fragment>
            )
        }

        return (
            <React.Fragment>
		{this.renderButtons()}
                <a href={this.props.blog.redirectionLink} target="_blank" rel="noopener noreferrer">
                    <div className="crop blog">
                        <img className="scrap-img blog" src={this.props.blog.thumbnailURL || window.location.origin + "/images/emptyImage.png"} alt={this.props.blog.title} />
                    </div>
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
