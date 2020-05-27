import React from "react";

const Navbar = () => {
  return (
    <div>
      <nav>
        <ul>
          <h2>Directory</h2>
          <div>
            <a href="#">Videos</a>
            <button>...</button>
            <button>+</button>
          </div>
          <ul>
            <li>
              <a href="#">test1</a>
              <button>...</button>
              <button>+</button>
            </li>
            <li>
              <a herf="#">test2</a>
              <button>...</button>
              <button>+</button>
            </li>
            <li>
              <a herf="#">test3</a>
              <button>...</button>
              <button>+</button>
            </li>
            <li>
              <a herf="#">test4</a>
              <button>...</button>
              <button>+</button>
            </li>
          </ul>

          <div>
            <a href="#" />
            Blogs<button>...</button>
            <button>+</button>
          </div>
          <div>
            <a href="#" />
            Wishlist<button>...</button>
            <button>+</button>
          </div>
          <div>
            <a href="#" />
            Portals<button>...</button>
            <button>+</button>
          </div>
          <div>
            <a href="#" />
            News<button>...</button>
            <button>+</button>
          </div>
          <div>
            <a href="#" />
            JobHuntingInfo<button>...</button>
            <button>+</button>
          </div>
        </ul>
      </nav>
    </div>
  );
};

export default Navbar;
