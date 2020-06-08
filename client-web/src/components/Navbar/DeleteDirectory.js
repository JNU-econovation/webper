import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../Modal';
import history from '../../history';
import { fetchDir, deleteDir } from '../../actions';

class DeleteDirectory extends React.Component {
    componentDidMount() {
        this.props.fetchDir(this.props.match.params.id)
    }

    renderContent() {
        let message = <div>이 디렉토리와 담겨있는 스크랩들을 정말로 삭제하시겠습니까?</div>

        if (this.props.directory)
            message = <div>이 <span>{this.props.directory.directory_title}</span>디렉토리와 담겨있는 스크랩들을 정말로 삭제하시겠습니까?</div>

        return (
            <React.Fragment>
                {message}
                <div>
                    <button onClick={this.props.deleteDir(this.props.match.params.id)}>Delete</button>
                    <button>Cancel</button>
                </div>
            </React.Fragment>
        )
    }

    render() {
        return (
            <Modal
                title="Delete Directory"
                content={this.renderContent()}
                onDismiss={() => history.goBack()}
            />
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return { directory: state.dirs[ownProps.match.params.id] }
}

export default connect(mapStateToProps, { fetchDir, deleteDir })(DeleteDirectory);