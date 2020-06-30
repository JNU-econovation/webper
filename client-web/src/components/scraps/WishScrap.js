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

    renderContents() {
        if (this.state.onEdit === true) {
            const editable_info_name = { name: "product", thumbnails: "thumbnails", price: "price", shoppingmall: "shoppingmall", delivery: "delivery", description: "description", redirectionLink: "url" }
            return (
                <React.Fragment>
                    <EditScrap
                        category='wishlist'
                        scrap_detail={this.props.wish}
                        initialValues={_.pick(this.props.wish, 'name', 'thumbnails', 'price', 'shoppingmall', 'delivery', 'description', 'redirectionLink')}
                        image={this.props.wish.thumbnails}
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
                <a href={this.props.wish.redirectionLink} target="_blank" rel="noopener noreferrer">
                    <img className="scrap-img" src={this.props.wish.thumbnails || window.location.origin + "/images/emptyImage.png"} alt={this.props.wish.name} />
                </a>
                <div className="detail-container">
                    <div>{this.props.wish.name}</div>
                    <div>{this.props.wish.price}</div>
                    <div>{this.props.wish.shoppingmall}</div>
                    <div>{this.props.wish.delivery}</div>
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