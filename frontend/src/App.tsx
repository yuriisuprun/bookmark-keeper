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
  const [searchQuery, setSearchQuery] = useState('')
  const [isSearching, setIsSearching] = useState(false)
  const [searchType, setSearchType] = useState<'all' | 'title' | 'author' | 'isbn' | 'year'>('all')

  useEffect(() => {
    fetchBooks()
  }, [])

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/books')
      setBooks(response.data)
      setIsSearching(false)
      setSearchQuery('')
    } catch (error) {
      console.error('Error fetching books:', error)
    }
  }

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault()

    if (!searchQuery.trim()) {
      fetchBooks()
      return
    }

    try {
      setIsSearching(true)
      let response

      switch (searchType) {
        case 'title':
          response = await axios.get('http://localhost:8080/api/books/search/title', {
            params: { title: searchQuery }
          })
          break
        case 'author':
          response = await axios.get('http://localhost:8080/api/books/search/author', {
            params: { author: searchQuery }
          })
          break
        case 'isbn':
          response = await axios.get('http://localhost:8080/api/books/search/isbn', {
            params: { isbn: searchQuery }
          })
          break
        case 'year':
          response = await axios.get('http://localhost:8080/api/books/search/year', {
            params: { year: parseInt(searchQuery) }
          })
          break
        default: // 'all'
          response = await axios.get('http://localhost:8080/api/books/search', {
            params: { query: searchQuery }
          })
      }

      setBooks(response.data)
    } catch (error) {
      console.error('Error searching books:', error)
      setBooks([])
    }
  }

  const clearSearch = () => {
    setSearchQuery('')
    setSearchType('all')
    fetchBooks()
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
      <header className="app-header">
        <div className="header-container">
          <div className="logo-section">
            <h1>Library</h1>
            <p className="tagline">Book collection manager</p>
          </div>
        </div>
      </header>

      <main className="app-main">
        {/* Search Section */}
        <section className="search-section">
          <div className="section-container">
            <h2>Search books</h2>
            <form onSubmit={handleSearch} className="search-form">
              <div className="search-controls">
                <input
                  type="text"
                  placeholder="Search by title, author, ISBN..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className="search-input"
                  aria-label="Search books"
                />

                <select
                  value={searchType}
                  onChange={(e) => setSearchType(e.target.value as any)}
                  className="search-type-select"
                  aria-label="Select search type"
                >
                  <option value="all">All Fields</option>
                  <option value="title">Title</option>
                  <option value="author">Author</option>
                  <option value="isbn">ISBN</option>
                  <option value="year">Year</option>
                </select>

                <button type="submit" className="search-btn">Search</button>
                {isSearching && <button type="button" onClick={clearSearch} className="clear-btn">Clear</button>}
              </div>
            </form>
          </div>
        </section>

        {/* Add Book Section */}
        <section className="add-book-section">
          <div className="section-container">
            <h2>Add new book</h2>
            <div className="form-group">
              <input
                type="text"
                placeholder="Book Title"
                value={newBook.title}
                onChange={(e) => setNewBook({ ...newBook, title: e.target.value })}
                aria-label="Book title"
              />
              <input
                type="text"
                placeholder="Author Name"
                value={newBook.author}
                onChange={(e) => setNewBook({ ...newBook, author: e.target.value })}
                aria-label="Author name"
              />
              <input
                type="text"
                placeholder="ISBN"
                value={newBook.isbn}
                onChange={(e) => setNewBook({ ...newBook, isbn: e.target.value })}
                aria-label="ISBN"
              />
              <input
                type="number"
                placeholder="Published Year"
                value={newBook.publishedYear}
                onChange={(e) => setNewBook({ ...newBook, publishedYear: parseInt(e.target.value) || 0 })}
                aria-label="Published year"
              />
              <button onClick={addBook} className="add-btn">Add Book</button>
            </div>
          </div>
        </section>

        {/* Books Display Section */}
        <section className="books-section">
          <div className="section-container">
            <div className="books-header">
              <h2>Your Books</h2>
              {isSearching && <span className="results-count">{books.length} result{books.length !== 1 ? 's' : ''}</span>}
            </div>
            {books.length === 0 ? (
              <p className="no-books">No books found. Start adding your first book or try a different search.</p>
            ) : (
              <div className="books-grid">
                {books.map((book) => (
                  <article key={book.id} className="book-card">
                    <div className="book-content">
                      <h3>{book.title}</h3>
                      <dl className="book-metadata">
                        <div className="metadata-item">
                          <dt>Author</dt>
                          <dd>{book.author}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>ISBN</dt>
                          <dd className="isbn-code">{book.isbn}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Published</dt>
                          <dd>{book.publishedYear}</dd>
                        </div>
                      </dl>
                    </div>
                    <button
                      onClick={() => book.id && deleteBook(book.id)}
                      className="delete-btn"
                      aria-label={`Delete ${book.title}`}
                    >
                      Remove
                    </button>
                  </article>
                ))}
              </div>
            )}
          </div>
        </section>
      </main>
    </div>
  )
}

export default App
