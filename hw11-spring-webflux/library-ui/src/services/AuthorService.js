
const getAll = () => {
    return fetch('/api/author').then(response => response.json());
}

const remove = id => {
    return fetch('/api/author/'+id, {method: 'DELETE'});
}

const get = id => {
    return fetch('/api/author/' + id).then(response => response.json());
}

const save = author => {
    return fetch('/api/author', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(author),
      })
        .then(response => response.json());
}

const AuthorService = {
    getAll,
    remove,
    get,
    save
}

export default AuthorService;