package ru.sugandrey.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sugandrey.book.Book;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBookList() {
        return jdbcTemplate.query("SELECT * FROM books", new BookMapper());
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE book_id = ?", new Object[]{id}, new BookMapper())
                           .stream().findAny().orElse(null);
    }

    public void createNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO books (book_name, author, edition_year) VALUES (?, ?, ?)", book.getBookName(), book.getAuthor(), book.getEditionYear());
    }

    public void updateBook(Book updatedBook, int id) {
        jdbcTemplate.update("UPDATE books SET book_name = ?, author = ?, edition_year = ? WHERE book_id = ?", updatedBook.getBookName(),
                updatedBook.getAuthor(), updatedBook.getEditionYear(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE book_id = ?", id);
    }

    public List<Book> getBusyBooks(int personId) {
        return jdbcTemplate.query("SELECT * FROM books WHERE person_id = ?", new Object[]{personId}, new BookMapper());
    }

    public boolean checkBookDuplicates(Book book) {
        return getBookList().contains(book);
    }

    public void addReader(int bookId, int personId) {
        jdbcTemplate.update("UPDATE books SET person_id = ? WHERE book_id = ?", personId, bookId);
    }

    public void deleteReader(int bookId) {
        jdbcTemplate.update("UPDATE books SET person_id = null WHERE book_id = ?", bookId);
    }
}
