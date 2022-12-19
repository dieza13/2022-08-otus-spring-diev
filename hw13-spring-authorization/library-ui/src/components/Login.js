import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import React, { useState } from "react";
import PropTypes from 'prop-types';
import {
  useNavigate
} from "react-router-dom";


async function loginUser(credentials) {
  return fetch('/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(credentials)
  })
    .then(data => data.json())
}



function Login({ setToken }) {

  const emptyUser = {
    username: '',
    password: ''
  };
  const navigate = useNavigate();


  const [values, setValues] = useState({
    username: "",
    password: "",
  });


  const handleChange = (event) => {

    setValues((values) => ({
      ...values,
      [event.target.name]: event.target.value,
    }));
  };

  const handleSubmit = async (event) => {

    const token = await loginUser(
      values
    );
    setToken(token);
    navigate("/")

  }

  return (
    <div className="container" id="main">

      {/* <form id="main-form" className="card mb-auto " method='POST'  onSubmit={handleSubmit}> */}
      <Card >
        <h3 className="title fw-bold text-success shadow-none text-center" >ДОБРО ПОЖАЛОВАТЬ В НАШУ БИБЛИОТЕКУ</h3>
        <Card.Body className="card card-body p-3 bg-light">
          <Form className="card mb-auto " method='POST'>
            <Form.Group className="mb-3" controlId="formBasicUsername">
              <Form.Label>Логин</Form.Label>
              <Form.Control type="username" name="username" placeholder="Введите логин" onChange={handleChange} />
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>Пароль</Form.Label>
              <Form.Control type="password" name="password" placeholder="Введите пароль" onChange={handleChange} />
            </Form.Group>
            <Button variant="primary" type="button" onClick={handleSubmit}>
              Войти
            </Button>
          </Form>
        </Card.Body>
      </Card>
      {/* </form> */}
    </div>
  );
}

Login.propTypes = {
  setToken: PropTypes.func.isRequired
}

export default Login;