import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';

const IndexNavbar = () => {
  return (
  <Navbar bg="info" expand="lg">
      <Navbar.Brand href="/">Time Table Generator</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          <Nav.Link href="/">Home</Nav.Link>
          <Nav.Link href="/aboutpage">About</Nav.Link>
          <Nav.Link href="/contact">Contact</Nav.Link>
          <Nav.Link href="/login">Login</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default IndexNavbar;
