import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import history from '../../history';
import { fetchRandomDir } from '../../actions';
import RenderScraps from './RenderScraps';

class Main extends React.Component {

    componentDidMount() {
	this.props.fetchRandomDir();
    }

    renderEmpty() {
    	return (
		<h1>
			Create your first webper directory!
		</h1>
	)
    }
	
    renderTitle() {
	let title;
	title = "webper";
    	if (this.props.userName)
    		title = `${this.props.userName}의 webper`;
	return <h1>{title}</h1> 
    }

    renderRandomDir() {
	if (this.props.directory.length === 0)
	    return (
		<div className="container">
		    {this.renderEmpty()}
		</div>
	    )
	const directory = this.props.directory[0];
	return (
	    <div>
		{this.renderTitle()}
		<div className="container">
			<div className="scrap-container main">
				<h2 className="title">
					<Link to={`/detail/${directory.id}/${directory.category}`}>{directory.title}</Link>
				</h2>
				<RenderScraps
					directory={directory}
					scraps={Object.values(directory.components)}
					main="true"
				/>
			</div>
		</div>
	    </div>
	)
    }

    render() {
        if (!this.props.isSignedIn)
            history.push('/user_login')

        return (
            <div className="container">
                 {this.renderRandomDir()}
            </div>
        );
    };
};

const mapStateToProps = (state, ownProps) => {
    return {
        isSignedIn: state.auth.isSignedIn,
        userName: state.auth.userName,
	directory: Object.values(state.main),
        scraps: Object.values(state.scraps).reverse()
    };
};

export default connect(mapStateToProps, { fetchRandomDir })(Main);
