import React from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';
import Modal from '../Modal';
import history from '../../history';
import { fetchDir, editDir } from '../../actions';

class EditDirectory extends React.Component {

    componentDidMount() {
        this.props.fetchDir(this.props.match.params.id);
    }

    onSubmit = formValues => {
        console.log("in Edit", this.props);
        this.props.editDir(this.props.match.params.id, this.props.directory.category, this.props.directory.parentDirectoryId, formValues);
    }

    renderContent() {
        return (
            <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                <label>Directory Title</label>
                <Field name="title" component="input" placeholder="Enter the new name of directory" autoComplete="off" />
                <div>
                    <button type="submit">Rename</button>
                    <button onClick={() => history.goBack()}>Cancel</button>
                </div>
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

const mapStateToProps = (state, ownProps) => {
    return { directory: state.dirs[ownProps.match.params.id] }
}

export default connect(mapStateToProps, { fetchDir, editDir })(Wrapped);
