import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'

interface Book {
  id?: number;
  title: string;
  author: string;
  isbn: string;
  publishedYear: number;
}

function App() {
  const [books, setBooks] = useState<Book[]>([])
  const [newBook, setNewBook] = useState<Book>({ title: '', author: '', isbn: '', publishedYear: 0 })

  useEffect(() => {
    fetchBooks()
  }, [])

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/books')
      setBooks(response.data)
    } catch (error) {
      console.error('Error fetching books:', error)
    }
  }

  const addBook = async () => {
    try {
      await axios.post('http://localhost:8080/api/books', newBook)
      fetchBooks()
      setNewBook({ title: '', author: '', isbn: '', publishedYear: 0 })
    } catch (error) {
      console.error('Error adding book:', error)
    }
  }

  const deleteBook = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8080/api/books/${id}`)
      fetchBooks()
    } catch (error) {
      console.error('Error deleting book:', error)
    }
  }

  return (
    <div className="App">
      <h1>Library App</h1>
      <div>
        <h2>Add New Book</h2>
        <input
          type="text"
          placeholder="Title"
          value={newBook.title}
          onChange={(e) => setNewBook({ ...newBook, title: e.target.value })}
        />
        <input
          type="text"
          placeholder="Author"
          value={newBook.author}
          onChange={(e) => setNewBook({ ...newBook, author: e.target.value })}
        />
        <input
          type="text"
          placeholder="ISBN"
          value={newBook.isbn}
          onChange={(e) => setNewBook({ ...newBook, isbn: e.target.value })}
        />
        <input
          type="number"
          placeholder="Published Year"
          value={newBook.publishedYear}
          onChange={(e) => setNewBook({ ...newBook, publishedYear: parseInt(e.target.value) || 0 })}
        />
        <button onClick={addBook}>Add Book</button>
      </div>
      <div>
        <h2>Books</h2>
        <ul>
          {books.map((book) => (
            <li key={book.id}>
              {book.title} by {book.author} ({book.publishedYear}) - ISBN: {book.isbn}
              <button onClick={() => book.id && deleteBook(book.id)}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default App
