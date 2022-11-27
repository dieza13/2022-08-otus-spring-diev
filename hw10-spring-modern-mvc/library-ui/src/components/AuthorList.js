import React, { Component } from 'react';
import Table from 'react-bootstrap/Table';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import AuthorService from '../services/AuthorService';

class AuthorList extends Component {

  constructor(props) {
    super(props);
    this.state = {authors: []};
    this.remove = this.remove.bind(this);
  }

   componentDidMount() {
        AuthorService.getAll().then(data => this.setState({authors: data}));
    }

  async remove(id) {
    await AuthorService.remove(id).then(() => {
    let updatedAuthors = [...this.state.authors].filter(i => i.id !== id);
    this.setState({ authors: updatedAuthors });
     });
  }


  render() {
    const { authors, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const authorList = authors.map(author => {
      return <tr key={author.id}>
        <td >{author.id}</td>
        <td >{author.name}</td>
        <td >{author.lastName}</td>
        <td>
          <Button variant="outline-success" href={"/author/" + author.id}>Изменить</Button> <a> </a>
          <Button variant="outline-danger" onClick={() => this.remove(author.id)}>Удалить</Button>

        </td>
      </tr>
    });

    return (
      <div className="container" id="main">
        <div className="table-container" id="main-author-table">
          <Card>

            <Card.Body className="card-body">
              <h3 >Список авторов</h3>
              <div className="form-edit-group-row">
                  <Button variant="primary" href="/author/new">Создать</Button>
              </div>
            </Card.Body>
          </Card>
          <Table className="table" bordered striped hover>
            <thead className="table-light">
              <tr>
                <th >id</th>
                <th >Имя</th>
                <th >Фамилия</th>                
                <th >Действие</th>
              </tr>
            </thead>
            <tbody className="table-group-divider">
              {authorList}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}
export default AuthorList;