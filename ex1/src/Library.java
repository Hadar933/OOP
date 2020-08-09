public class Library {
    final int maxBookCapacity;
    final int maxBorrowedBooks;
    final int maxPatronCapacity;
    Book[] bookArray;
    String[] registeredPatrons;
    private int currentNumberOfBooks = 0;

    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        this.bookArray = new Book[maxBookCapacity];
        this.registeredPatrons = new String[maxPatronCapacity];
    }



    /**
     * Adds the given book to this library, if there is place available,
     * and it isn't already in the library.
     *
     * @param book - some book to insert
     * @return - a non-negative id number for the book if there was a spot and the book was
     * successfully added, or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        if (currentNumberOfBooks < maxBorrowedBooks)
        {
            if()
        }
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number of
     * books allowed, and if the patron will enjoy this book.
     *
     * @param bookId-The id number of the book to borrow.
     * @param patronId - The id number of the patron that will borrow the book.
     * @return - true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        return true;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book - The book for which to find the id number.
     * @return - a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        return 0;
    }

    /**
     * Returns the non-negative id number of the given patron if he is
     * registered to this library, -1 otherwise.
     *
     * @param patron - The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this
     * library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        return 0;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId - The id number of the book to check.
     * @return - true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        return true;
    }

    /**
     *Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId - The id to check.
     * @return - true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {

        return false;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId - The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return true;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron - The patron to register to this library.
     * @return - a non-negative id number for the patron if there was a spot and the patron was
     * successfully registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        return 0;
    }

    /**
     * Return the given book.
     *
     * @param bookId - The id number of the book to return.
     */
    void returnBook(int bookId) {

    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available
     * books he will enjoy, if any such exist.
     *
     * @param patronId - The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most.
     * Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {

    }

}
