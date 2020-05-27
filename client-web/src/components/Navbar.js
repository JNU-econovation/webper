import React from "react";

const Navbar = () => {
  return (
    <div>
      <nav>
        <ul>
          <h2>Directory</h2>
          <div>
            <a href="#">Videos</a>
            <button class="rightend">...</button>
            <button class="rightend">+</button>
          </div>
          <ul>
            <li>
              <a href="#">test1</a>
              <button class="rightend">...</button>
              <button class="rightend">+</button>
            </li>
            <li>
              <a herf="#">test2</a>
              <button class="rightend">...</button>
              <button class="rightend">+</button>
            </li>
          </ul>

          <div>
            <a href="#" />
            Blogs<button class="rightend">...</button>
            <button class="rightend">+</button>
          </div>
        </ul>
      </nav>
    </div>
  );
};

export default Navbar;
