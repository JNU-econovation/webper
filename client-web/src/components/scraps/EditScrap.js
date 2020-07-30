import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import { editScrap } from '../../actions';

class EditScrap extends React.Component {

    onSubmit = formValues => {
        this.props.editScrap(this.props.scrap_detail.id, formValues, this.props.scrap_detail.directoryId, this.props.category);
        this.props.saveCallback();
    }

    renderForm() {
        const fields = Object.keys(this.props.editable_info_name).map(key => {
            let inputType = 'input';
            if (key === 'description') inputType = 'textarea';
            return (
                <div>
                    <label>{this.props.editable_info_name[key]}</label>
                    <Field name={key} className="scrap-edit-input" component={inputType} autoComplete="off" />
                </div>
            )
        }
        )

        return fields
    }

    render() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                <div className="scrap-button-container">
                    <button className="scrap-save-button" type="submit">save</button>
                </div>
                <div className="scrap-edit-container">
                    <img className="scrap-img" src={this.props.image || window.location.origin + "/images/emptyImage.png"} alt="thumbnail" />
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
