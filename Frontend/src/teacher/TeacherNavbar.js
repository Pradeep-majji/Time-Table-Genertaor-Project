import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';

const TeacherNavbar = () => {
  return (
  <Navbar bg="info" expand="lg">
      <Navbar.Brand href="/teacherhome"></Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          <Nav.Link href="/teacherhome">TeacherHome</Nav.Link>
          <Nav.Link href="/teacherallotment">Teacher Allotment</Nav.Link>
          <Nav.Link href="/teachertimetable">View Time Table</Nav.Link>
          <Nav.Link href="/logout">Logout</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default TeacherNavbar;
