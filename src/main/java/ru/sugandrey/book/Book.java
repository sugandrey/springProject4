package ru.sugandrey.book;

public class Book {

    private int bookId;
    private String bookName;
    private String author;
    private int editionYear;
    private Integer personId;

    public Book(final int bookId, final String bookName, final String author, final int editionYear, final Integer personId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.editionYear = editionYear;
        this.personId = personId;
    }

    public Book() {
    }

    public String getBookName() {
        return bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(final int bookId) {
        this.bookId = bookId;
    }

    public void setBookName(final String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public int getEditionYear() {
        return editionYear;
    }

    public void setEditionYear(final int editionYear) {
        this.editionYear = editionYear;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(final int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return this.getBookName().equals(book.getBookName());
    }
}
