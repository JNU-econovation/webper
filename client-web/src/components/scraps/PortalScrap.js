import _ from "lodash";
import React from "react";
import EditScrap from "./EditScrap";

class PortalScrap extends React.Component {
  state = { onEdit: false };

  editModeOn = () => {
    const id = this.props.portal.id;
    this.setState({ onEdit: true });
    document.querySelector("body").addEventListener("click", this.dismiss);
    document
      .querySelector(`#scrap${id}`)
      .addEventListener("click", this.stopPropagation);
  };

  editModeOff = () => {
    const id = this.props.portal.id;
    this.setState({ onEdit: false });
    document.querySelector("body").removeEventListener("click", this.dismiss);
    document
      .querySelector(`#scrap${id}`)
      .removeEventListener("click", this.stopPropagation);
  };

  dismiss = () => {
    this.editModeOff();
  };

  stopPropagation = (e) => {
    e.stopPropagation();
  };

  renderContents() {
    if (this.state.onEdit === true) {
      const editable_info_name = {
        name: "name",
        favicon: "icon",
        redirectionLink: "url",
      };
      return (
        <React.Fragment>
          <EditScrap
            category="portal"
            scrap_detail={this.props.portal}
            initialValues={_.pick(
              this.props.portal,
              "name",
              "favicon",
              "redirectionLink"
            )}
            image={this.props.portal.favicon}
            editable_info_name={editable_info_name}
            saveCallback={() => {
              this.setState({ onEdit: false });
              this.editModeOff();
            }}
          />
        </React.Fragment>
      );
    }

    return (
      <React.Fragment>
        <div className="scrap-edit-button-container">
          <img
            onClick={this.editModeOn}
            className="scrap-edit-button"
            src={window.location.origin + "/images/more.png"}
            alt="scrap edit button"
          />
        </div>
        <a
          href={this.props.portal.redirectionLink}
          target="_blank"
          rel="noopener noreferrer"
        >
          <div className="portal-scrap-img-container">
            <img
              className="portal-scrap-img"
              src={
                this.props.portal.favicon ||
                window.location.origin + "/images/emptyImage.png"
              }
              alt={this.props.portal.name}
            />
          </div>
        </a>
        <div className="detail-container">
          <div>{this.props.portal.name}</div>
        </div>
      </React.Fragment>
    );
  }

  render() {
    return (
      <div id={`scrap${this.props.portal.id}`} className="scrap-item">
        {this.renderContents()}
      </div>
    );
  }
}

export default PortalScrap;
