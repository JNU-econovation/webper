import React from 'react';
import { Field, reduxForm } from 'redux-form';

class DirForm extends React.Component {
    onSubmit = formValues => this.props.onSubmit(formValues);

    renderContent() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)} >
                <div>
                    <label>Directory Type </label>
                    <Field name="category" component="select">
                        <option></option>
                        <option value="WISHLIST">Wish List</option>
                        <option value="BLOG">Blog</option>
                        <option value="VIDEO">Youtube</option>
                        <option value="PORTAL">Portal</option>
                    </Field>
                </div>
                <div>
                    <label>Directory Title </label>
                    <Field name="title" component="input" placeholder="Enter the name of new directory" autoComplete="off" />
                </div>

                <button onSubmit={this.onSubmit}>Create</button>
            </form >
        )
    }

    // renderInput = ({ meta }) => {
    //     return (
    //         <div>
    //             <input type="text" placeholder="Enter the name of new directory" />
    //             {this.renderError(meta)}
    //         </div>
    //     )
    // }

    // renderError({ error, touched }) {
    //     if (error & touched) {
    //         return (
    //             <div className="error-message">{error}</div>
    //         )
    //     }
    // }

    render() {
        return (
            this.renderContent()
        )
    }
}

// const validate = formValues => {
//     let error = "";
//     if (!formValues.directoryTitle)
//         error = "You must enter a name of directory"

//     // console.log(error);

//     return error;
// }

export default reduxForm({
    form: 'dirForm'
    // validate
})(DirForm);
