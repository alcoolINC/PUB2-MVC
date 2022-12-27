import React from "react";
import NavbarItem from "@/components/navbar-item";

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
      <div className="container-fluid">
        <a href="#" className="navbar-brand">
          MeniuRestaurant
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <NavbarItem path="/home" label="Home" />
            <NavbarItem path="/menu" label="Menu" />
            <NavbarItem path="/contact" label="Contact" />
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
