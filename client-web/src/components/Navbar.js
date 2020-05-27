import React from "react";

const Navbar = () => {
  return (
    <div>
      <nav>
        <ul>
          <h2>Directory</h2>
          <div>
            <a href="#">Videos</a>
            <button className="rightend">...</button>
            <button className="rightend">+</button>
          </div>
          <ul>
            <li>
              <a href="#">test1</a>
              <button className="rightend">...</button>
              <button className="rightend">+</button>
            </li>
            <li>
              <a herf="#">test2</a>
              <button className="rightend">...</button>
              <button className="rightend">+</button>
            </li>
          </ul>

          <div>
            <a href="#" />
            Blogs<button className="rightend">...</button>
            <button className="rightend">+</button>
          </div>
        </ul>
      </nav>
    </div>
  );
};

export default Navbar;
