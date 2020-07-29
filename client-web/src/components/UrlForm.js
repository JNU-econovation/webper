import React from "react";

import { Field, reduxForm } from "redux-form";
import UrlDnd from "./UrlDnd";

class UrlForm extends React.Component {

  state = { onDnd: false };

  setDndMod = () => {
    return (this.state.onDnd ? this.setState({ onDnd: false }) : this.setState({ onDnd: true }));
  }

  renderURLInput = ({ input }) => {
    return (
      <div>
        <input
          {...input}
          type="url"
          autoComplete="off"
          placeholder="Enter a URL"
          className="url-input"
        />
      </div>
    );
  };

  onSubmit = (formValues) => {
    this.props.onSubmit(formValues);
    this.props.initialize();
  };


  renderContents = () => {
    if (this.state.onDnd === false) {
      return (
        <UrlDnd onSubmit={this.onSubmit} />
      )
    }
    return (
      <div className="urlform">
        <form onSubmit={this.props.handleSubmit(this.onSubmit)} onClick={(e) => e.stopPropagation()}>
          <Field name="inputURL" component={this.renderURLInput} />
          <button onSubmit={this.onSubmit}>Create</button>
        </form>
      </div>
    )
  }

  render() {
    return (
      <div className="scrap-url" onClick={this.setDndMod}>
        {this.renderContents()}
      </div>
    );
  }
}

export default reduxForm({ form: "urlForm" })(UrlForm);
