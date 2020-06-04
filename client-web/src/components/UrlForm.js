import React from 'react';

import { Field, reduxForm } from 'redux-form';

class UrlForm extends React.Component {

    renderURLInput = ({ input }) => {
        return (
            <div>
                <input {...input} type="url" autoComplete='off' placeholder="Enter a URL" className="url-input" />
            </div>
        )
    }

    onSubmit = (formValues) => {
        this.props.onSubmit(formValues);
        this.props.initialize();
    }

    render() {
        return (
            <div className="scrap-item">
                <div className="urlform">
                    <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                        <Field name="inputURL" component={this.renderURLInput} />
                        <button onSubmit={this.onSubmit}>Create</button>
                    </form>
                </div>
            </div>
        )
    }
};

export default reduxForm({ form: 'urlForm' })(UrlForm);