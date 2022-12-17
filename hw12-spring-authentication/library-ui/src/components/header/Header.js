import React from "react"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import '../../css/Header.css';
import Button from 'react-bootstrap/Button';
import {
    useNavigate
} from "react-router-dom";

function getToken() {
    const tokenString = sessionStorage.getItem('token');
    const userToken = JSON.parse(tokenString);
    return userToken;
}

function Header() {
    const token = getToken();
    const logToggle = token ? 'Выйти' : 'Войти';
    const navigate = useNavigate();

    const handleLogToggle = (event) => {
        sessionStorage.clear();
        navigate("/login");
    }
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
                    <Nav className="justify-content-end">
                        <Button variant="link" type="button" onClick={handleLogToggle}>{logToggle}</Button>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Header