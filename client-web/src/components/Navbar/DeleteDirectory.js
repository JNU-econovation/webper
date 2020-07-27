import React from 'react';
import { connect } from 'react-redux';
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
            message = <div><span>{this.props.directory.directoryTitle}</span> 디렉토리와 담겨있는 스크랩들을 정말로 삭제하시겠습니까?</div>

        return (
            <React.Fragment>
                {message}
                <form>
                    <div>
                        <button onClick={() => this.props.deleteDir(this.props.match.params.id)}>Delete</button>
                        <button onClick={() => history.goBack()}>Cancel</button>
                    </div>
                </form>
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