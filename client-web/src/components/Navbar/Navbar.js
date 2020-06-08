import React from "react";
import CreateButton from './CreateButton';

import RenderNavList from './RenderNavList';

class Navbar extends React.Component {
  closeNav = () => {
    const nav = document.querySelector("nav");
    const main_body = document.querySelector(".main-body");
    nav.style.width = "0";
    main_body.style.marginLeft = "0";
  }

  render() {
    return (
      <div>
        <nav>
          <div>
            <img src={window.location.origin + "/images/back.png"} className="back-icon button" alt="back button" onClick={this.closeNav} />
            <div className="nav-top-container">
              <div className="top-hierarchy">Directory</div>
              <CreateButton className="top-hierarchy" />
            </div>
          </div>
          <RenderNavList parentId={0} />
        </nav>
      </div>
    );
  }
};

export default Navbar;
