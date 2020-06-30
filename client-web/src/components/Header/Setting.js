import React from 'react';
import { connect } from 'react-redux';
import Modal from '../Modal';
import history from '../../history';
import { fetchAllDirs, setting } from '../../actions';
import { Field, reduxForm } from 'redux-form';

class Setting extends React.Component {

    componentDidMount() {
        this.props.fetchAllDirs();
    }

    renderTitle() {
        return (
            <React.Fragment>
                <img src={window.location.origin + "/images/setting.png"} />
                Setting
            </React.Fragment>
        )
    }

    renderContent() {
        return (
            <form onSubmit={this.props.handleSubmit(this.props.setting)}>
                <div>
                    <label>Webper Name</label>
                    <Field name="webperTitle" component="input" initialValue="Seoyoung의 webper" />
                </div>
                <div>
                    <div>Main화면에 고정할 webper 디렉토리</div>
                    {this.renderLists()}
                </div>
                <button type="submit">Save</button>
            </form>
        )
    }

    renderLists() {
        const orderStr = ["first", "second", "third", "fourth", "fifth"];
        let index = 1;
        const orderList = orderStr.map(str => {
            return (
                <div>
                    <label>{index}</label>
                    <Field name={str} component="select" key={index++}>
                        <option></option>
                        {this.renderDirOptions()}
                    </Field>
                </div>
            )
        })

        return orderList;
    }

    renderDirOptions() {
        const dirOptions = this.props.dirs.map(dir => {
            return (<option value={dir.id}>{dir.directoryTitle}</option>)
        })
        return dirOptions;
    }

    render() {
        return (
            <Modal
                title={this.renderTitle()}
                content={this.renderContent()}
                onDismiss={() => { history.goBack() }}
            />
        )
    }
}

const mapStateToProps = (state) => {
    return {
        dirs: Object.values(state.dirs)
    }
}

const Wrapped = connect(mapStateToProps, { fetchAllDirs, setting })(Setting);

export default reduxForm({
    form: 'initializeFromState'
})(Wrapped);