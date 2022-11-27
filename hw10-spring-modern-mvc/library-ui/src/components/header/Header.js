import React from "react"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import '../../css/Header.css';

function Header() {
    return (
        <Navbar className="LibNavbar" collapseOnSelect expand="lg" >
                <Container fluid>
                    <Navbar.Brand className="navbar-brand " href="/">Книги для всех</Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="/book">Книги</Nav.Link>
                            <Nav.Link href="/author">Авторы</Nav.Link>
                            <Nav.Link href="/genre">Жанры</Nav.Link>
                        </Nav>
                        {/* <Nav className="justify-content-end">
                            <NavDropdown title="lang" id="collasible-nav-dropdown">
                                <NavDropdown.Item href="#lang=en">EN</NavDropdown.Item>
                                <NavDropdown.Item href="#lang=ru">RU</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>                         */}
                    </Navbar.Collapse>
                </Container>
        </Navbar>
    )
}

export default Header