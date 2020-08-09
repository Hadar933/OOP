/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library
 * to be able to check out books, if a copy of the requested book is available.
 */
public class Library {
    final private int EMPTY = -1;
    /**
     * an index corresponds with the id of a book or a patron. when a new book/patron is added,these
     * will be incremented by one.
     */
    private int bookId = 0;
    private int patronId = 0;

    /**
     * consists of all the books that are in the library
     */
    Book[] allBooks;

    /**
     * consists of all the patrons that have rented a book
     */
    Patron[] allPatrons;

    /**
     * list of patrons id, in which the values represent how many books a patron borrowed
     */
    int[] borrowedBooksList;
    /**
     * The maximal number of books this library can hold.
     */
    final private int maxBookCapacity;

    /**
     * The maximal number of books this library allows a single patron to
     * borrow at the same time
     */
    final private int maxBorrowedBooks;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    final private int maxPatronCapacity;


    /**
     * Creates a new library with the given parameters.
     *
     * @param maxBookCapacity   - The maximal number of books this library can hold.
     * @param maxBorrowedBooks  - The maximal number of books this library allows a single patron to
     *                          borrow at the same time.
     * @param maxPatronCapacity - The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        this.allBooks = new Book[maxBookCapacity];
        this.allPatrons = new Patron[maxPatronCapacity];
        this.borrowedBooksList = new int[maxPatronCapacity];
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
        for (int i = 0; i < allBooks.length; i++) {
            if (allBooks[i] == book) { // book is already in the library
                return i;
            }
        }
        if (bookId == maxBookCapacity) { // library if full
            return EMPTY;
        }
        allBooks[bookId] = book;
        return bookId++;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number of
     * books allowed, and if the patron will enjoy this book.
     *
     * @param bookId-The id number of the book to borrow.
     * @param patronId   - The id number of the patron that will borrow the book.
     * @return - true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (isBookIdValid(bookId)
                && isPatronIdValid(patronId)
                && allBooks[bookId].currentBorrowerId == EMPTY
                && allPatrons[patronId].willEnjoyBook(allBooks[bookId])
                && borrowedBooksList[patronId] < maxBorrowedBooks) {
            allBooks[bookId].setBorrowerId(patronId);
            borrowedBooksList[patronId]++;
            return true;
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book - The book for which to find the id number.
     * @return - a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < allBooks.length; i++) {
            if (allBooks[i] == book) {
                return i;
            }
        }
        return EMPTY;
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
        for (int i = 0; i < allPatrons.length; i++) {
            if (allPatrons[i] == patron) {
                return i;
            }
        }
        return EMPTY;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId - The id number of the book to check.
     * @return - true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (allBooks[bookId] == null) {
            return false;
        }
        if (isBookIdValid(bookId)) {
            return allBooks[bookId].getCurrentBorrowerId() == EMPTY;
        }
        return false;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId - The id to check.
     * @return - true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        if (bookId >= maxBookCapacity || bookId < 0) {
            return false;
        }
        return allBooks[bookId] != null;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId - The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if (patronId >= maxPatronCapacity || patronId < 0) {
            return false;
        }
        return allPatrons[patronId] != null;

    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron - The patron to register to this library.
     * @return - a non-negative id number for the patron if there was a spot and the patron was
     * successfully registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i = 0; i < allPatrons.length; i++) {
            if (allPatrons[i] == patron) {
                return i;
            }
        }
        if (patronId == maxPatronCapacity) {
            return EMPTY;
        }
        allPatrons[patronId] = patron;
        return patronId++;

    }

    /**
     * Return the given book.
     *
     * @param bookId - The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId)) {
            borrowedBooksList[allBooks[bookId].currentBorrowerId]--;
            allBooks[bookId].currentBorrowerId = EMPTY;
        }
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
        if (isPatronIdValid(patronId)) {
            Patron patron = allPatrons[patronId];
            int bestID = -1;
            int bestEnjoyment = 0;
            for (int i = 0; i < maxBookCapacity; i++) {
                if (allBooks[i] != null) {
                    int currEnjoyment = patron.getBookScore(allBooks[i]);
                    if (currEnjoyment > bestEnjoyment + patron.patronEnjoymentThreshold
                            && allBooks[i].currentBorrowerId == EMPTY) {
                        bestEnjoyment = currEnjoyment;
                        bestID = i;
                    }
                }
            }
            if (isBookIdValid(bestID)) {
                return allBooks[bestID];
            }
        }
        return null;
    }

}
