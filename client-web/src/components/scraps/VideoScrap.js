import _ from 'lodash';
import React from 'react';
import EditScrap from './EditScrap';

class VideoScrap extends React.Component {
    state = { onEdit: false };

    editModeOn = () => {
        const id = this.props.video.id;
        this.setState({ onEdit: true })
        document.querySelector('body').addEventListener('click', this.dismiss);
        document.querySelector(`#scrap${id}`).addEventListener('click', this.stopPropagation)
    }

    editModeOff = () => {
        const id = this.props.video.id;
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
    	this.props.onDelete(this.props.video.id);
    }

    renderContents() {
        if (this.state.onEdit === true) {
            const editable_info_name = { title: "title", redirectionLink: "url" }
            return (
                <React.Fragment>
                    <EditScrap
                        category='VIDEO'
                        scrap_detail={this.props.video}
                        initialValues={_.pick(this.props.video, 'title', 'redirectionLink')}
                        image={this.props.video.thumbnailURL}
                        editable_info_name={editable_info_name}
                        saveCallback={() => { this.setState({ onEdit: false }); this.editModeOff(); }}
                    />
                </React.Fragment>
            )
        }

        return (
            <React.Fragment>
                <div className="scrap-button-container">
                    <img onClick={this.onDelete} className="scrap-button" src={window.location.origin + "/images/trash.png"} alt="scrap delete button" />
                    <img onClick={this.editModeOn} className="scrap-button" src={window.location.origin + "/images/more.png"} alt="scrap edit button" />
		</div>
                <a href={this.props.video.redirectionLink} target="_blank" rel="noopener noreferrer">
                    <img className="scrap-img" src={this.props.video.thumbnailURL || window.location.origin + "/images/emptyImage.png"} alt={this.props.video.title} />
                </a>
                <div className="detail-container">
                    <div>{this.props.video.title}</div>
                </div>
            </React.Fragment>
        )

    }

    render() {
        return (
            <div id={`scrap${this.props.video.id}`} className="scrap-item"  >
                {this.renderContents()}
            </div>
        )

    }
}

export default VideoScrap;
