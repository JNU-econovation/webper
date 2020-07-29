import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import Modal from '../Modal';
import history from '../../history';
import { editDir } from '../../actions';

class EditDirectory extends React.Component {

    onSubmit = formValues => {
        this.props.editDir(this.props.match.params.id, formValues);
    }

    renderContent() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                <label>Directory Title</label>
                <Field name="directoryTitle" component="input" placeholder="Enter the name of directory" autoComplete="off" />
                <button type="submit">Rename</button>
                <button onClick={() => history.goBack()}>Cancel</button>
            </form>
        )
    }

    render() {
        return (
            <Modal
                title="Rename Directory Title"
                content={this.renderContent()}
                onDismiss={() => { history.goBack() }}
            />
        )
    }

}

const Wrapped = reduxForm({
    form: 'editDirForm'
})(EditDirectory);

export default connect(null, { editDir })(Wrapped);
