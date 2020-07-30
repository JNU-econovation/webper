import _ from 'lodash';
import React from 'react';
import EditScrap from './EditScrap';

class WishScrap extends React.Component {
    state = { onEdit: false };

    editModeOn = () => {
        const id = this.props.wish.id;
        this.setState({ onEdit: true })
        document.querySelector('body').addEventListener('click', this.dismiss);
        document.querySelector(`#scrap${id}`).addEventListener('click', this.stopPropagation)
    }

    editModeOff = () => {
        const id = this.props.wish.id;
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
    	this.props.onDelete(this.props.wish.id);
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
            const editable_info_name = { title: "product", thumbnailURL: "thumbnailURL", price: "price", shoppingMall: "shoppingMall", deliveryInfo: "deliveryInfo", description: "description", redirectionLink: "url" }
            return (
                <React.Fragment>
                    <EditScrap
                        category='wishlist'
                        scrap_detail={this.props.wish}
                        initialValues={_.pick(this.props.wish, 'title', 'thumbnailURL', 'price', 'shoppingMall', 'deliveryInfo', 'description', 'redirectionLink')}
                        image={this.props.wish.thumbnailURL}
                        editable_info_name={editable_info_name}
                        saveCallback={() => { this.setState({ onEdit: false }); this.editModeOff(); }}
                    />
                </React.Fragment>
            )
        }

        return (
            <React.Fragment>
		{this.renderButtons()}
                <a href={this.props.wish.redirectionLink} target="_blank" rel="noopener noreferrer">
                    <img className="scrap-img" src={this.props.wish.thumbnailURL || window.location.origin + "/images/emptyImage.png"} alt={this.props.wish.title} />
                </a>
                <div className="detail-container">
                    <div>{this.props.wish.title}</div>
                    <div>{this.props.wish.price}</div>
                    <div>{this.props.wish.shoppingMall}</div>
                    <div>{this.props.wish.deliveryInfo}</div>
                    <div>{this.props.wish.description}</div>
                </div>
            </React.Fragment>
        )

    }

    render() {
        return (
            <div id={`scrap${this.props.wish.id}`} className="scrap-item"  >
                {this.renderContents()}
            </div>
        )

    }
}

export default WishScrap;
