import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Card from 'react-bootstrap/Card';
import { useParams } from "react-router-dom";

function withParams(Component) {
  return props => <Component {...props} params={useParams()} />;
}



class GenreEdit extends Component {

  emptyGenre = {
    id: '',
    name: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      genre: this.emptyGenre,
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {

    let { id } = this.props.params;
    if (id !== 'new') {
      fetch('/api/genre/' + id)
        .then(response => response.json())
        .then(data => this.setState({ genre: data }));
    } else {
      this.setState({ genre: this.emptyGenre });
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let genre = { ...this.state.genre };
    genre[name] = value;
    this.setState({ genre });
  }

  handleSubmit(event) {
    const genre = this.state.genre;

    fetch('/api/genre', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(genre),
    })
      .then(response => response.json())
      .then(data => this.setState({ genre: data }));
    this.props.history.push('/genre');
  }

  render() {

    const { genre } = this.state;
    const title = genre.id ? 'Изменить жанр' : 'Новый жанр';
    return (
      <div className="container" id="main">

        <form id="genre-edit-form">
          <Card className="card mb-auto">
            <Card.Body className="card-body">
              <h3 >{title}</h3>
              <Form.Group className="form-edit-group-row mb-3"  >
                <Form.Label>id</Form.Label>
                <Form.Control type="text" name="id" id="id" value={genre.id} readOnly />
              </Form.Group>

              <Form.Group className="form-edit-group-row mb-3" >
                <Form.Label>Наименование</Form.Label>
                <Form.Control type="text" name="name" value={genre.name} onChange={this.handleChange} />
              </Form.Group>

              <div className="form-edit-group-row">
                <Button variant="primary" type="button" onClick={this.handleSubmit}>Сохранить</Button>
                <a href="/genre">
                  <Button variant="secondary" type="button">Выход</Button>
                </a>
              </div>

            </Card.Body>
          </Card>
        </form>
      </div>
    );
  }
}
export default withParams(GenreEdit);