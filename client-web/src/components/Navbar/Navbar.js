import React from "react";
import CreateButton from './CreateButton';

import RenderNavList from './RenderNavList';

class Navbar extends React.Component {

  render() {
    return (
      <div>
        <nav>
          <ul>
            <div>
              <div className="container">
                <div className="top-hierarchy">Directory</div>
                <CreateButton className="top-hierarchy" />
              </div>
            </div>
            <RenderNavList parentId={0} />
          </ul>
        </nav>
      </div>
    );
  }
};

export default Navbar;
