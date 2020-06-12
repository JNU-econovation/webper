import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { editScrap } from '../../actions';

class EditScrap extends React.Component {

    onSubmit = formValues => {
        this.props.editScrap(this.props.video_detail.id, formValues, this.props.video_detail.directoryId);
        this.props.saveCallback();
    }

    renderForm() {
        const fields = Object.keys(this.props.editable_info_name).map(key =>
            <div>
                <label>{this.props.editable_info_name[key]}</label>
                <Field name={key} className="scrap-edit-input" component="input" autoComplete="off" />
            </div>
        )

        return fields
    }

    render() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                <div className="scrap-edit-button-container">
                    <button className="scrap-save-button" type="submit">save</button>
                </div>
                <div className="scrap-edit-container">
                    <img className="scrap-img" src={this.props.image} alt="thumbnail" />
                    {this.renderForm()}
                </div>
            </form>
        );
    }
}

const Wrapped = reduxForm({
    form: 'editForm'
})(EditScrap);

export default connect(null, { editScrap })(Wrapped);