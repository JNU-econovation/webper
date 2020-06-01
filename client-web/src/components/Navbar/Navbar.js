import React from "react";
import { connect } from 'react-redux';
import NavList from './NavList';
import history from '../../history';
import { fetchDirs } from '../../actions';

class Navbar extends React.Component {

  componentDidMount(directory_id = 0) {
    this.props.fetchDirs(directory_id);
  }

  createNewDir() {
    history.push('/new');
  }

  renderNavList() {
    if (this.props.dirs.length === 0)
      return null;

    return (
      <React.Fragment>
        {this.props.dirs.map(dir => {
          return <NavList directory_detail={dir} key={dir.id} />
        })}
      </React.Fragment>
    )
  }

  render() {
    return (
      <div>
        <nav>
          <ul>
            <div>
              <div className="container">
                <div className="top-hierarchy">Directory</div>
                <button onClick={this.createNewDir} className="top-hierarchy">+</button>
              </div>
            </div>
            {this.renderNavList()}
          </ul>
        </nav>
      </div>
    );
  }
};

const mapStateToProps = (state) => {
  return {
    dirs: Object.values(state.dirs)
  }
}

export default connect(mapStateToProps, { fetchDirs })(Navbar);
