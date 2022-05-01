
/**
 This class represents a library, which hold a collection of books. Patrons can register at the library to be
 able to check out books, if a copy of the requested book is available.
 */
public class Library {
    /**
     * The maximal number of books this library can hold.
     */
    final int libraryMaxBookCapacity;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time.
     */
    final int libraryMaxBorrowedBooks;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    final int libraryMaxPatronCapacity;

    Book[] bookArray;
    Patron[] patronArray;
    int [] maxPatronBorrowArray;



    /*----=  Constructors  =-----*/

    /**
     * Creates a new book with the given characteristic.
     *
     * @param maxBookCapacity             The maximal number of books this library can hold.
     * @param maxBorrowedBooks            The maximal number of books this library allows a single patron
     *                                    to borrow at the same time.
     * @param maxPatronCapacity           The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity){
        libraryMaxBookCapacity = maxBookCapacity;
        libraryMaxBorrowedBooks = maxBorrowedBooks;
        libraryMaxPatronCapacity = maxPatronCapacity;

        bookArray = new Book[libraryMaxBookCapacity];
        patronArray = new Patron[libraryMaxPatronCapacity];
        maxPatronBorrowArray = new int[libraryMaxPatronCapacity];

        for (int i=0; i<maxPatronBorrowArray.length; i++){
            maxPatronBorrowArray[i] = 0;
        }


    }


    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @param book  The book to add to this library.
     *
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book){
        int number = -1;
        if(!isBookExists(book)){ //(exists == false)
            for(int i = 0; i < bookArray.length; i++){
                if (bookArray[i] == null && book.currentBorrowerId == -1){
                    bookArray[i] = book;
                    book.returnBook();
                    number = i;
                    break;
                }
            }
        }
        else{
            number = getBookId(book);
        }
        return number;
    }


    /**
     * HELPER METHOD * //was not mentioned in the API
     *
     * Returns true if the book already exists(added), false otherwise.
     *
     * @param book   the book to check.
     *
     * @return true if the book exists, false otherwise.
     */
    boolean isBookExists (Book book){
        boolean exists = false;
        for(int i = 0; i < bookArray.length; i++) {
            if (bookArray[i] != null) {
                if (bookArray[i]==book) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }


    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
     *
     * @param bookId  The id number of the book to borrow.
     * @param patronId  The id number of the patron that will borrow the book.
     *
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId){
        boolean isBorrowed = false;
        if (isBookIdValid(bookId) && isPatronIdValid(patronId)){
            if (isBookAvailable(bookId) && maxPatronBorrowArray[patronId] < libraryMaxBorrowedBooks &&
                    patronArray[patronId].willEnjoyBook(bookArray[bookId])){
                bookArray[bookId].setBorrowerId(patronId);
                isBorrowed = true;
                maxPatronBorrowArray[patronId] += 1;
            }
        }
        return isBorrowed;
    }


    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book  The book for which to find the id number.
     *
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book){
        int numId = -1;
        if (isBookExists(book)) {
            for (int i = 0; i < bookArray.length; i++) {
                if (bookArray[i] == book) {
                    numId = i;
                    break;
                }
            }
        }
        return numId;
    }


    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     *
     * @param patron   The patron for which to find the id number.
     *
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron){
        int numId = -1;
        if (isPatronExists(patron)) {
            for (int i = 0; i < patronArray.length; i++) {
                if (patronArray[i] == patron) {
                    numId = i;
                    break;
                }
            }
        }
        return numId;
    }


    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId   The id number of the book to check.
     *
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId){
        boolean ans = false;
        if (isBookIdValid(bookId)){
            if (bookArray[bookId].currentBorrowerId == -1){
                ans = true;
            }
        }
        return ans;
    }


    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId   The id to check.
     *
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId){
        boolean ans = false;
        if (bookArray.length > bookId){
            if (bookArray[bookId] != null) {
                ans = true;
            }
        }
        return ans;
    }


    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId   The id to check.
     *
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId){
        boolean ans = false;
        if (patronArray.length > patronId){
            if (patronArray[patronId] != null) {
                ans = true;
            }
        }
        return ans;
    }


    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron   The patron to register to this library.
     *
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron){
        int number = -1;
        if(!isPatronExists(patron)){ //(exists == false)
            for(int i = 0; i < patronArray.length; i++){
                if (patronArray[i] == null){
                    patronArray[i] = patron;
                    number = i;
                    break;
                }
            }
        }
        return number;
    }


    /**
     * HELPER METHOD * //was not mentioned in the API
     *
     * Returns true if the patron already exists(already registered), false otherwise.
     *
     * @param patron   the patron to check.
     *
     * @return true if the patron exists, false otherwise.
     */
    boolean isPatronExists (Patron patron){
        boolean exists = false;
        for(int i = 0; i < patronArray.length; i++) {
            if (patronArray[i] == patron) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * Return the given book.
     *
     * @param bookId   The id number of the book to return.
     */
    void returnBook(int bookId){
        if (isBookIdValid(bookId)) {
            int borrowerId = bookArray[bookId].currentBorrowerId;
            maxPatronBorrowArray[borrowerId] -= 1;  //FIX "The number of books borrowed by a patron is not
                                                    // updated on book return"
            bookArray[bookId].returnBook();
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     *
     * @param patronId   The id number of the patron to suggest the book to.
     *
     * @return The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId){
        Book bookBest = null;
        if (isPatronIdValid(patronId)){
            int maxValue = 0;
            for (int i=0; i<bookArray.length; i++){
                if (patronArray[patronId].willEnjoyBook(bookArray[i])){
                    if (patronArray[patronId].getBookScore(bookArray[i])> maxValue){
                        bookBest = bookArray[i];
                        maxValue = patronArray[patronId].getBookScore(bookArray[i]);
                    }
                }
            }
        }
        return bookBest;
    }



}