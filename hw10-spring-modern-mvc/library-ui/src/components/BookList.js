import React, { Component } from 'react';
import Table from 'react-bootstrap/Table';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import BookService from '../services/BookService';



class BookList extends Component {

  constructor(props) {
    super(props);
    this.state = {books: []};
    this.remove = this.remove.bind(this);
  }

   componentDidMount() {
        BookService.getAll().then(data => this.setState({books: data}));
    }

  async remove(id) {
    await BookService.remove(id).then(() => {
    let updatedBooks = [...this.state.books].filter(i => i.id !== id);
    this.setState({ books: updatedBooks });
     });
  }

  authorFormat(author) {
    return author.name.at(0).concat(". ", author.lastName);
  }

  render() {
    const { books, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const bookList = books.map(book => {
      return <tr key={book.id}>
        <td >{book.id}</td>
        <td >{book.name}</td>
        <td >{this.authorFormat(book.author)}</td>
        <td >{book.genre.name}</td>
        <td>
          <Button variant="outline-success" href={"/book/" + book.id}>Изменить</Button> <a> </a>
          <Button variant="outline-danger" onClick={() => this.remove(book.id)}>Удалить</Button>

        </td>
      </tr>
    });

   

    return (
      <div className="container" id="main">
        <div className="table-container" id="main-book-table">
          <Card>

            <Card.Body className="card-body">
              <h3 text="#{form.book.list.list}">Список книг</h3>
              <div className="form-edit-group-row">
                  <Button variant="primary" href="/book/new">Создать</Button>
              </div>
            </Card.Body>
          </Card>
          <Table className="table" bordered striped hover>
            <thead className="table-light">
              <tr>
                <th >id</th>
                <th >Наименование</th>
                <th >Автор</th>
                <th >Жанр</th>
                <th >Действие</th>
              </tr>
            </thead>
            <tbody className="table-group-divider">
              {bookList}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}
export default BookList;