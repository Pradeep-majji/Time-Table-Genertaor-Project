import React from 'react'
import { Navbar, Nav } from 'react-bootstrap';

const guideNavbar = () => {
  return (
    <Navbar bg="info" expand="lg">
      <Navbar.Brand href="/adminhome">ADMIN DASHBOARD</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="ml-auto">
          <Nav.Link href="/adminhome">PROFILE</Nav.Link>
          <Nav.Link href="/adminaddteacher">ACCEPT TEACHERS</Nav.Link>
          <Nav.Link href="/adminaddsubject">ADD SUBJECTS</Nav.Link>
          <Nav.Link href="/adminaddclassroom">ADD CLASSROOMS</Nav.Link>
          <Nav.Link href="/adminallotment">ALLOTMENTS</Nav.Link>
          <Nav.Link href="/admingeneratett">GENERATE TIME TABLE</Nav.Link>
          <Nav.Link href="/logout">logout</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  )
}

export default guideNavbar;