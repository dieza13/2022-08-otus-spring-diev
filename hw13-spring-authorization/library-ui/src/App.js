import './css/App.css';
import Main from './components/Main';
import BookList from './components/BookList';
import BookEdit from './components/BookEdit';
import AuthorList from './components/AuthorList';
import AuthorEdit from './components/AuthorEdit';
import GenreList from './components/GenreList';
import GenreEdit from './components/GenreEdit';
import Login from './components/Login';
import React, { Component, useState } from 'react';

import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import Header from './components/header/Header';

function setToken(userToken) {
  sessionStorage.setItem('token', JSON.stringify(userToken));
}

function getToken() {
  const tokenString = sessionStorage.getItem('token');
  const userToken = JSON.parse(tokenString);
  return userToken;
}

function App() {

  const token = getToken();

  if (!token) {
    return (
    <Router>
      <div>
        <Header />        
        <Login setToken={setToken} />
        {/* <Routes>
          <Route path="/" element={<Main />} ></Route>
        </Routes> */}
      </div>
    </Router>
    );
  }

  return (
    <Router>
      <div>
        <Header />

        <Routes>
          <Route path="/" element={<Main />} ></Route>
          <Route path="/book" element={<BookList />} />
          <Route path="/book/:id" element={<BookEdit />} />
          <Route path="/author" element={<AuthorList />} />
          <Route path="/author/:id" element={<AuthorEdit />} />
          <Route path="/genre" element={<GenreList />} />
          <Route path="/genre/:id" element={<GenreEdit />} />
          <Route path="/login" element={<Login setToken={setToken} />} />
        </Routes>

      </div>
    </Router>
  );
}

export default App;