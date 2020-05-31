import React from "react";
import NavList from './NavList';
import history from '../../history';
const Navbar = () => {

  const createNewDir = () => {
    history.push('/new');
  }

  return (
    <div>
      <nav>
        <ul>
          <div>
            <div className="container">
              <div className="top-hierarchy">Directory</div>
              <button onClick={createNewDir} className="top-hierarchy">+</button>
            </div>
          </div>

          <NavList title="Videos" />
          <NavList title="Blogs" />
          <NavList title="test1" />
          <NavList title="test2" />

        </ul>
      </nav>
    </div>
  );
};

export default Navbar;
