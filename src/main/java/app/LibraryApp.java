package app;

import domain.BookCopy;
import service.BookService;
import service.LibraryService;
import service.LoanService;
import service.MemberService;
import domain.Book;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {


    public static void main(String[] args) {

        MemberService memberService = new MemberService();
        LoanService loanService = new LoanService();
        BookService bookService = new BookService();

        LibraryService libraryService = new LibraryService(memberService, bookService, loanService);
        addTestData(libraryService);
        mainMenu(libraryService);
    }

    public static void mainMenu(LibraryService libraryService) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n──────── MAIN MENU ─────────");
            System.out.println("[1] Books");
            System.out.println("[0] Exit");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    booksMenu(libraryService, scanner);
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
            }
        }
    }

    public static void booksMenu(LibraryService libraryService, Scanner scanner) {

        while (true) {
            System.out.println("\n──BOOK MANAGEMENT ────────────────");
            System.out.println("[1] Search book by ISBN");
            System.out.println("[2] View all books");
            System.out.println("[3] Add new book");
            System.out.println("[4] Add copies for a book");
            System.out.println("[5] Remove book");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    getBookByIsbn(libraryService,scanner);
                    break;
                case "2":
                    viewBooks(libraryService);
                    break;
                case "3":
                    addBook(libraryService, scanner);
                    break;
                case "4":
                    addCopy(libraryService, scanner);
                    break;
                case "5":
                    removeBook(libraryService,scanner);
                    break;
                case "b":
                    return;
            }
        }
    }


    public static void addBook(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n--Add New Book -----------------");
        System.out.print("ISBN : ");
        String isbn = scanner.nextLine();
        System.out.print("Title : ");
        String title = scanner.nextLine();
        System.out.print("Genre : ");
        String genre = scanner.nextLine();
        System.out.print("Author : ");
        String author = scanner.nextLine();
        System.out.print("Language : ");
        String language = scanner.nextLine();
        System.out.print("Publisher : ");
        String publisher = scanner.nextLine();
        System.out.print("Loan period (leave blank for default value of 14 days): ");
        String loan_period = scanner.nextLine();
        System.out.print("Amount of copies : ");
        int copies = scanner.nextInt();
        scanner.nextLine();

        try {
            if(loan_period.isEmpty()){
                libraryService.addBook(isbn, title, genre, author, language, publisher,14);
            }else {
                libraryService.addBook(isbn, title, genre, author, language, publisher,Integer.parseInt(loan_period));
            }
            libraryService.addCopies(isbn, copies);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCopy(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n--Add New Copy -----------------");
        System.out.println("Give the ISBN to add a new copy");
        String isbn = scanner.nextLine();
        System.out.println("Give the amount of copies you want to add");
        int amount = scanner.nextInt();
        scanner.nextLine();
        try {

            libraryService.addCopies(isbn, amount);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void viewBooks(LibraryService libraryService){
        Collection<Book> books = libraryService.getAllBooks();

        System.out.println("\n──ALL BOOKS ────────────────");
        System.out.printf("  %-13s  %-30s  %-25s  %-18s %s%n",
                "ISBN","Title","Author","Genre","Available");
        System.out.println("  " + "─".repeat(125));

        for(Book book : books){
            System.out.printf("  %-13s  %-30s  %-25s  %-18s %d%n",
                    book.getIsbn(),book.getTitle(),book.getAuthor(),book.getGenre(),book.getLoanPeriodDays());
        }
    }


    public static void getBookByIsbn(LibraryService libraryService,Scanner scanner){
        System.out.println("\n──SEARCH BOOK BY ISBN ────────────────");
        System.out.println("Search: ");
        String isbn = scanner.nextLine();

        try{
            Book searchedBook = libraryService.getBook(isbn);
            Collection<BookCopy> copies = libraryService.getAllCopies(isbn);

            System.out.printf("  %-13s  %-30s %-25s %s%n","ISBN","Title","Author","Books Available");
            System.out.println("  " + "─".repeat(90));

            System.out.printf("  %-13s  %-30s %-25s %d%n%n%n",searchedBook.getIsbn(),searchedBook.getTitle(),searchedBook.getAuthor(),copies.size());

            System.out.println("\n──ALL COPIES ──────────────────");
            System.out.printf("  %-13s  %-30s %-25s %s%n","COPY-ID","Title","Author","STATUS");
            System.out.println("  " + "─".repeat(90));

            for (BookCopy copy : copies){
                System.out.printf("  %-13s  %-30s %-25s %s%n",copy.getCopyId(),copy.getBook().getTitle(),copy.getBook().getAuthor(),copy.getStatus());
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void removeBook(LibraryService libraryService , Scanner scanner){
        System.out.println("\n──REMOVE BOOK ────────────────");
        System.out.println("Give the ISBN of the book you want to remove:");
        String isbn = scanner.nextLine();


        try {
            Book removedBook = libraryService.getBook(isbn);
            libraryService.removeBook(isbn);
            System.out.println("  Successfully removed the following book:");
            System.out.printf("  %-13s  %-30s  %n","ISBN","Title");
            System.out.println("  " + "─".repeat(45));

            System.out.printf("  %-13s  %-30s%n%n%n",removedBook.getIsbn(),removedBook.getTitle());

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
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
        libraryService.addCopies("9781501156700",6);
        libraryService.addCopies("9780525559474",7);
        libraryService.addCopies("9780316769174",6);



    }


}
