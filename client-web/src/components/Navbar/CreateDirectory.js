import React from 'react';
import { connect } from 'react-redux';
import Modal from '../Modal';
import history from '../../history';
import { createDir } from '../../actions';
import DirForm from './DirForm';

class CreateDirectory extends React.Component {

    onSubmit = (formValues) => {
        console.log(formValues);
        this.props.createDir(formValues);
    }

    renderContent() {
        return (
            <DirForm onSubmit={this.onSubmit} />
        )
    }
    // renderActions() {
    //     return (
    //         <button type="submit">Create</button>
    //     )
    // }

    render() {
        return (
            <Modal
                title="새로운 webper를 만듭니다"
                content={this.renderContent()}
                // actions={this.renderActions()}
                onDismiss={() => history.goBack()}
            />
        )
    }
}

export default connect(null, { createDir })(CreateDirectory);