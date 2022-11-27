const getAll = () => {
    return fetch('/api/genre').then(response => response.json());
}

const remove = id => {
    return fetch('/api/genre/'+id, {method: 'DELETE'});
}

const get = id => {
    return fetch('/api/genre/' + id).then(response => response.json());
}

const save = genre => {
    return fetch('/api/genre', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(genre),
      })
        .then(response => response.json());
}

const GenreService = {
    getAll,
    remove,
    get,
    save
    
}

export default GenreService;