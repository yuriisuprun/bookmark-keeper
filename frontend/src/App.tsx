import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'

type TargetAudience = 'CHILDREN' | 'YOUNG_ADULT' | 'ADULT' | 'ACADEMIC';

interface Book {
  id?: number;
  title: string;
  author: string;
  isbn?: string;
  publishedYear?: number;
  publisher?: string;
  genre?: string;
  targetAudience?: TargetAudience;
  country?: string;
  language?: string;
  pageCount?: number;
  description?: string;
  coverImageUrl?: string;
}

function App() {
  const [books, setBooks] = useState<Book[]>([])
  const [newBook, setNewBook] = useState<Book>({ title: '', author: '' })
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
      const payload: Book = {
        ...newBook,
        isbn: newBook.isbn?.trim() || undefined,
        publisher: newBook.publisher?.trim() || undefined,
        genre: newBook.genre?.trim() || undefined,
        country: newBook.country?.trim() || undefined,
        language: newBook.language?.trim() || undefined,
        description: newBook.description?.trim() || undefined,
        coverImageUrl: newBook.coverImageUrl?.trim() || undefined,
        pageCount: newBook.pageCount && newBook.pageCount > 0 ? newBook.pageCount : undefined,
        publishedYear: typeof newBook.publishedYear === 'number' ? newBook.publishedYear : undefined,
      }

      await axios.post('http://localhost:8080/api/books', payload)
      fetchBooks()
      setNewBook({ title: '', author: '' })
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
          <nav className="main-nav">
            <ul>
              <li><a href="#home">Home</a></li>
              <li><a href="#books">Browse Books</a></li>
              <li><a href="#add">Add Book</a></li>
            </ul>
          </nav>
        </div>
      </header>

      <main className="app-main">
        {/*<section id="home" className="hero-section">*/}
        {/*  <div className="section-container">*/}
        {/*    <h2>Welcome to Your Personal Library</h2>*/}
        {/*    <p>Manage your book collection with ease. Search, add, and organize your favorite reads.</p>*/}
        {/*  </div>*/}
        {/*</section>*/}

        {/* Search Section */}
        <section id="books" className="search-section">
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
        <section id="add" className="add-book-section">
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
                value={newBook.isbn ?? ''}
                onChange={(e) => setNewBook({ ...newBook, isbn: e.target.value })}
                aria-label="ISBN"
              />
              <input
                type="number"
                placeholder="Published Year"
                value={newBook.publishedYear ?? ''}
                onChange={(e) => {
                  const v = e.target.value.trim()
                  setNewBook({ ...newBook, publishedYear: v ? parseInt(v, 10) : undefined })
                }}
                aria-label="Published year"
              />
              <input
                type="text"
                placeholder="Publisher"
                value={newBook.publisher ?? ''}
                onChange={(e) => setNewBook({ ...newBook, publisher: e.target.value })}
                aria-label="Publisher"
              />
              <input
                type="text"
                placeholder="Genre"
                value={newBook.genre ?? ''}
                onChange={(e) => setNewBook({ ...newBook, genre: e.target.value })}
                aria-label="Genre"
              />
              <select
                value={newBook.targetAudience ?? ''}
                onChange={(e) => {
                  const v = e.target.value as TargetAudience | ''
                  setNewBook({ ...newBook, targetAudience: v || undefined })
                }}
                aria-label="Target audience"
              >
                <option value="">Target audience (optional)</option>
                <option value="CHILDREN">Children</option>
                <option value="YOUNG_ADULT">Young adult</option>
                <option value="ADULT">Adult</option>
                <option value="ACADEMIC">Academic</option>
              </select>
              <input
                type="text"
                placeholder="Country (e.g., US, IT)"
                value={newBook.country ?? ''}
                onChange={(e) => setNewBook({ ...newBook, country: e.target.value })}
                aria-label="Country"
              />
              <input
                type="text"
                placeholder="Language (e.g., en, it, en-US)"
                value={newBook.language ?? ''}
                onChange={(e) => setNewBook({ ...newBook, language: e.target.value })}
                aria-label="Language"
              />
              <input
                type="number"
                placeholder="Number of pages"
                value={newBook.pageCount ?? ''}
                onChange={(e) => {
                  const v = e.target.value.trim()
                  setNewBook({ ...newBook, pageCount: v ? parseInt(v, 10) : undefined })
                }}
                aria-label="Number of pages"
                min={1}
              />
              <input
                type="text"
                placeholder="Cover image URL"
                value={newBook.coverImageUrl ?? ''}
                onChange={(e) => setNewBook({ ...newBook, coverImageUrl: e.target.value })}
                aria-label="Cover image URL"
              />
              <input
                type="text"
                placeholder="Short description"
                value={newBook.description ?? ''}
                onChange={(e) => setNewBook({ ...newBook, description: e.target.value })}
                aria-label="Description"
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
                          <dd className="isbn-code">{book.isbn || '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Published</dt>
                          <dd>{typeof book.publishedYear === 'number' ? book.publishedYear : '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Publisher</dt>
                          <dd>{book.publisher || '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Genre</dt>
                          <dd>{book.genre || '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Audience</dt>
                          <dd>{book.targetAudience ? book.targetAudience.replace('_', ' ') : '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Country</dt>
                          <dd>{book.country || '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Language</dt>
                          <dd>{book.language || '—'}</dd>
                        </div>
                        <div className="metadata-item">
                          <dt>Pages</dt>
                          <dd>{typeof book.pageCount === 'number' ? book.pageCount : '—'}</dd>
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

      <footer className="app-footer">
        <div className="footer-container">
          <p>&copy; 2024 Library App. All rights reserved.</p>
          <nav className="footer-nav">
            <ul>
              <li><a href="#privacy">Privacy Policy</a></li>
              <li><a href="#terms">Terms of Service</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </nav>
        </div>
      </footer>
    </div>
  )
}

export default App
