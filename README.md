# library-system
Ex0 of the Java OOP course



## File description

- Book.java - contains the Book object implementation
- Patron.java - contains the Patron object implementation
- Library.java - contains the library system class implementation that uses Book and Patron classes
____________________


## Design

Each of the three objects Book, Patron and Library are implementated in different classes. The library
class uses the two classes of Book and Patron to add books, register patrons, borrow books to them and
suggests books they will enjoy most etc..
____________________



## Implementation details

I used arrays to store information about the books, patrons, max borrowing limits etc..

added two methods not mentioned in the API which are isPatronExists and isBookExists
in order to check if book or patron are already stored in the library system before adding them.
