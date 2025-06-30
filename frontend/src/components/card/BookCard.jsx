import './BookCard.css';

export default function BookCard({ book, showSubscribe = true }) {
  return (
    <div className="book-card">
      <div className="book-thumbnail">{book.title}</div>
      <div className="book-actions">
        {showSubscribe && <button>구독</button>}
        <button>열람</button>
      </div>
      <div className="book-meta">❤️ {book.likes} ☆ {book.subscribes}</div>
    </div>
  );
}