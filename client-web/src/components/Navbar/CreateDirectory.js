import React from 'react';
import { connect } from 'react-redux';
import Modal from '../Modal';
import history from '../../history';
import { createDir } from '../../actions';
import DirForm from './DirForm';

class CreateDirectory extends React.Component {

    onSubmit = (formValues) => {
        this.props.createDir({ ...formValues, parentId: this.props.match.params.id });
    }

    renderContent() {
        return (
            <DirForm onSubmit={this.onSubmit} />
        )
    }

    render() {
        console.log(this.props);
        return (
            <Modal
                title="새로운 webper를 만듭니다"
                content={this.renderContent()}
                onDismiss={() => { history.goBack() }}
            />
        )
    }
}

export default connect(null, { createDir })(CreateDirectory);