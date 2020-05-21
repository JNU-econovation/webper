import React from 'react';

import { Field, reduxForm } from 'redux-form';

class UrlForm extends React.Component {

    renderURLInput = ({ input }) => {
        const className = "replace this!!";
        return (
            <div className={className}>
                <input {...input} type="url" autoComplete='off' placeholder="Enter a URL" />
            </div>
        )
    }

    onSubmit = (formValues) => {
        console.log("submit");
        this.props.onSubmit(formValues);
    }

    render() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                <Field name="inputURL" component={this.renderURLInput} />
                <button onSubmit={this.onSubmit}>Create</button>
            </form>
        )
    }
};

export default reduxForm({ form: 'urlForm' })(UrlForm);