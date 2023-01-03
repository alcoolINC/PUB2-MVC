import React from "react";
const NavbarItem = ({ path, label, attribute}) => {
  if(attribute){
  return (
    <li className="nav-item">
      <a className={"nav-link"+" " +attribute} href={path}>
        {label}
      </a>
    </li>
  );}
  else{
  return(
    <li className="nav-item">
      <a className="nav-link" href={path}>
        {label}
      </a>
    </li>
  )}
};

export default NavbarItem;

