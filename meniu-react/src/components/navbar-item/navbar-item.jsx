import React from "react";

const NavbarItem = ({ path, label }) => {
  return (
    <li className="nav-item">
      <a className="nav-link" href={path}>
        {label}
      </a>
    </li>
  );
};

export default NavbarItem;
