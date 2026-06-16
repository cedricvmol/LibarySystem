package app;


import service.*;
import storage.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LibraryApp {


    public static void main(String[] args) throws SQLException {

        DatabaseManager databaseManager = new DatabaseManager();
        Connection connection = databaseManager.getConnection();

        BookDao bookDao = new BookDao(connection);
        BookStorage bookStorage = new BookStorage(bookDao);

        MemberDao memberDao = new MemberDao(connection);
        MemberStorage memberStorage = new MemberStorage(memberDao);

        MemberService memberService = new MemberService(memberStorage);
        BookService bookService = new BookService(bookStorage);

        LoanService loanService = new LoanService(bookService);
        ReservationService reservationService = new ReservationService(bookService);

        LibraryService libraryService = new LibraryService(memberService, bookService, loanService,reservationService);

        databaseManager.initTables();
        addTestData(libraryService);
        mainMenu(libraryService);

    }

    public static void mainMenu(LibraryService libraryService){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n──────── MAIN MENU ─────────");
            System.out.println("[1] Librarian Admin Menu");
            System.out.println("[2] Member Login");
            System.out.println("[0] Exit");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    LibrarianUI.librarianMenu(libraryService,scanner);
                    break;
                case "2":
                    MemberUI.memberMenu(libraryService,scanner);
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
            }
        }
    }

    public static void addTestData(LibraryService libraryService){

        libraryService.addBook("9780261103573", "The Fellowship of the Ring", "Fantasy", "J.R.R. Tolkien", "English", "HarperCollins");
        libraryService.addBook("9780743273565", "The Great Gatsby", "Classic", "F. Scott Fitzgerald", "English", "Scribner");
        libraryService.addBook("9780385333481", "The Handmaid's Tale", "Dystopian", "Margaret Atwood", "English", "Anchor Books");
        libraryService.addBook("9780679720201", "Crime and Punishment", "Classic", "Fyodor Dostoevsky", "English", "Vintage Books",15);
        libraryService.addBook("9780062315007", "Sapiens", "Non-Fiction", "Yuval Noah Harari", "English", "Harper Perennial");
        libraryService.addBook("9781501156700", "It", "Horror", "Stephen King", "English", "Scribner");
        libraryService.addBook("9780525559474", "The Midnight Library", "Fiction", "Matt Haig", "English", "Viking");
        libraryService.addBook("9780316769174", "The Catcher in the Rye", "Classic", "J.D. Salinger", "English", "Little, Brown",21);
        libraryService.addBook("9782070360024", "Le Petit Prince", "Fable", "Antoine de Saint-Exupéry", "French", "Gallimard");
        libraryService.addBook("9788423335305", "Cien años de soledad", "Magical Realism", "Gabriel García Márquez", "Spanish", "Editorial Sudamericana", 21);


        libraryService.addCopies("9780261103573",2);
        libraryService.addCopies("9780743273565",5);
        libraryService.addCopies("9780385333481",4);
        libraryService.addCopies("9780679720201",3);
        libraryService.addCopies("9780062315007",5);
        libraryService.addCopies("9780525559474",7);
        libraryService.addCopies("9781501156700",1);
        libraryService.addCopies("9780316769174",1);


        libraryService.registerMember("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        libraryService.registerMember("US02", "admin", "Bob Smith", "456 Oak Avenue, Shelbyville", "bob.smith@email.com", "+1-555-876-5432");
        libraryService.registerMember("US03", "admin", "Carlos Rivera", "789 Pine Road, Capital City", "carlos.rivera@email.com", "+34-612-345-678");
        libraryService.registerMember("US04", "admin", "Diana Lee", "10 Elm Court, Apt 3B, Metropolis", "diana.lee@email.com", "+44-7911-123456");
        libraryService.registerMember("US05", "admin", "Emma Patel", "55 Birch Lane, Gotham", "emma.patel@email.com", "+91-98765-43210");


        libraryService.borrowBook("US02","9781501156700");
        libraryService.borrowBook("US03","9780316769174");

        libraryService.createReservation("US01","9780316769174");
        libraryService.createReservation("US01","9781501156700");
    }


}
