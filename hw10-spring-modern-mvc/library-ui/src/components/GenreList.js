import React, { Component } from 'react';
import Table from 'react-bootstrap/Table';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import GenreService from '../services/GenreService';

class GenreList extends Component {

  constructor(props) {
    super(props);
    this.state = {genres: []};
    this.remove = this.remove.bind(this);
  }

   componentDidMount() {
        GenreService.getAll().then(data => this.setState({genres: data}));
    }

  async remove(id) {
     await GenreService.remove(id).then(() => {
    let updatedgenres = [...this.state.genres].filter(i => i.id !== id);
    this.setState({ genres: updatedgenres });
     });
  }


  render() {
    const { genres, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const genreList = genres.map(genre => {
      return <tr key={genre.id}>
        <td >{genre.id}</td>
        <td >{genre.name}</td>
        <td>
          <Button variant="outline-success" href={"/genre/" + genre.id}>Изменить</Button> <a> </a>
          <Button variant="outline-danger" onClick={() => this.remove(genre.id)}>Удалить</Button>

        </td>
      </tr>
    });

    return (
      <div className="container" id="main">
        <div className="table-container" id="main-genre-table">
          <Card>

            <Card.Body className="card-body">
              <h3 >Список жанров</h3>
              <div className="form-edit-group-row">
                  <Button variant="primary" href="/genre/new">Создать</Button>
              </div>
            </Card.Body>
          </Card>
          <Table className="table" bordered striped hover>
            <thead className="table-light">
              <tr>
                <th >id</th>
                <th >Наименование</th>                
                <th >Действие</th>
              </tr>
            </thead>
            <tbody className="table-group-divider">
              {genreList}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}
export default GenreList;