public class Library {
    final int maxBookCapacity;
    final int maxBorrowedBooks;
    final int maxPatronCapacity;
    String[] books;
    String[] registeredPatrons;

    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        this.books = new String[maxBookCapacity];
        this.registeredPatrons = new String[maxPatronCapacity];
    }

    /**
     * Adds the given book to this library, if there is place available,
     * and it isn't already in the library.
     *
     * @param book - some book to insert
     * @return
     */
    int addBookToLibrary(Book book) {
        return 1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number of
     * books allowed, and if the patron will enjoy this book.
     *
     * @param bookId
     * @param patronId
     * @return
     */
    boolean borrowBook(int bookId, int patronId) {

    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book
     * @return
     */
    int getBookId(Book book) {

    }

    /**
     * Returns the non-negative id number of the given patron if he is
     * registered to this library, -1 otherwise.
     *
     * @param patron
     * @return
     */
    int getPatronId(Patron patron) {

    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId
     */
    boolean isBookAvailable(int bookId) {

    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId
     */
    boolean isBookIdValid(int bookId) {

    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId
     */
    boolean isPatronIdValid(int patronId) {

    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron
     * @return
     */
    int registerPatronToLibrary(Patron patron) {

    }

    /**
     * Return the given book.
     *
     * @param bookId
     */
    void returnBook(int bookId) {

    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available
     * books he will enjoy, if any such exist.
     * @param patronId
     * @return
     */
    Book suggestBookToPatron(int patronId) {

    }

}
