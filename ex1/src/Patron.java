/**
 * This class represents a library patron that has a name and assigns values to different
 * literary aspects of books.
 */
public class Patron {

    final private String firstName;
    final private String lastName;
    /**
     * the weight the patron assigns to comic
     */
    final private int comicTendency;
    /**
     * the weight the patron assigns to drama
     */
    final private int dramaticTendency;
    /**
     * the weight the patron assigns to education
     */
    final private int educationalTendency;

    /**
     * a minimal score a book must be assigned by the patron in order for he to enjoy that book
     */
    final private int patronEnjoymentThreshold;


    /**
     * @param patronFirstName          - first name as string
     * @param patronLastName           - last name as a string
     * @param comicTendency            - the weight the patron assigns to comic
     * @param dramaticTendency         - the weight the patron assigns to education
     * @param educationalTendency      - the weight the patron assigns to comedy
     * @param patronEnjoymentThreshold - a minimal score a book must be assigned by the patron in order for
     *                                 he to enjoy that book
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {
        this.firstName = patronFirstName;
        this.lastName = patronLastName;
        this.comicTendency = comicTendency;
        this.dramaticTendency = dramaticTendency;
        this.educationalTendency = educationalTendency;
        this.patronEnjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     *
     * @param book - The book to asses.
     * @return - the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        return book.comicValue * comicTendency + book.dramaticValue * dramaticTendency
                + book.educationalValue * educationalTendency;
    }

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name,
     * separated by a single white space.
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     *
     * @param book - some book the patron might approve or disapprove
     * @return - true: book score bigger than thershold, false: otherwise
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) >= patronEnjoymentThreshold;
    }

}
