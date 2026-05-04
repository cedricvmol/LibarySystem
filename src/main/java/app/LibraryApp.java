package app;

import domain.Book;
import service.BookService;
import service.LibraryService;
import service.LoanService;
import service.MemberService;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final int COL_ISBN   = 20;
    private static final int COL_TITLE  = 28;
    private static final int COL_AUTHOR = 20;
    private static final int COL_GENRE  = 14;
    private static final int COL_LANG   = 10;
    private static final int COL_COPIES = 12;

    public static void main(String[] args) {
        MemberService memberService = new MemberService();
        LoanService loanService = new LoanService();
        BookService bookService = new BookService();
        LibraryService libraryService = new LibraryService(memberService, bookService, loanService);

        mainMenu(libraryService);
    }

    public static void mainMenu(LibraryService libraryService) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
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
            System.out.println("\n--BOOK MANAGEMENT -----------------");
            System.out.println("[1] Search books");
            System.out.println("[2] View all books");
            System.out.println("[3] Add new book");
            System.out.println("[4] Add copies for a book");
            System.out.println("[5] Remove book");
            System.out.println("[B] Back");

            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "1": searchBooks(libraryService, scanner); break;
                case "2": viewAllBooks(libraryService);         break;
                case "3": addBook(libraryService, scanner);     break;
                case "4": addCopy(libraryService, scanner);     break;
                case "5": removeBook(libraryService, scanner);  break;
                case "B": return;
            }
        }
    }

    // ── Book table display ───────────────────────────────────────────────────

    public static void viewAllBooks(LibraryService libraryService) {
        Collection<Book> books = libraryService.getAllBooks();

        System.out.println();
        if (books.isEmpty()) {
            System.out.println("  No books registered in the library yet.");
            return;
        }

        printBookTable(books, libraryService);
        System.out.println("  " + books.size() + " book(s) in catalogue.");
    }

    public static void searchBooks(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n--Search Books -----------------");
        System.out.print("Query (title / author / ISBN): ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("No query entered.");
            return;
        }

        List<Book> results = libraryService.searchBooks(query);

        System.out.println();
        if (results.isEmpty()) {
            System.out.println("  No books found matching: \"" + query + "\"");
            return;
        }

        printBookTable(results, libraryService);
        System.out.println("  " + results.size() + " result(s) for \"" + query + "\".");
    }

    private static void printBookTable(Collection<Book> books, LibraryService libraryService) {
        String divider = "+"
                + "-".repeat(COL_ISBN   + 2) + "+"
                + "-".repeat(COL_TITLE  + 2) + "+"
                + "-".repeat(COL_AUTHOR + 2) + "+"
                + "-".repeat(COL_GENRE  + 2) + "+"
                + "-".repeat(COL_LANG   + 2) + "+"
                + "-".repeat(COL_COPIES + 2) + "+";

        System.out.println(divider);
        System.out.printf("| %-" + COL_ISBN   + "s "
                        + "| %-" + COL_TITLE  + "s "
                        + "| %-" + COL_AUTHOR + "s "
                        + "| %-" + COL_GENRE  + "s "
                        + "| %-" + COL_LANG   + "s "
                        + "| %-" + COL_COPIES + "s |%n",
                "ISBN", "Title", "Author", "Genre", "Language", "Copies");
        System.out.println(divider);

        for (Book book : books) {
            int total     = libraryService.getTotalCopiesCount(book.getIsbn());
            int available = libraryService.getAvailableCopiesCount(book.getIsbn());
            String copies = available + "/" + total + " avail.";

            System.out.printf("| %-" + COL_ISBN   + "s "
                            + "| %-" + COL_TITLE  + "s "
                            + "| %-" + COL_AUTHOR + "s "
                            + "| %-" + COL_GENRE  + "s "
                            + "| %-" + COL_LANG   + "s "
                            + "| %-" + COL_COPIES + "s |%n",
                    trunc(book.getIsbn(),    COL_ISBN),
                    trunc(book.getTitle(),   COL_TITLE),
                    trunc(book.getAuthor(),  COL_AUTHOR),
                    trunc(book.getGenre(),   COL_GENRE),
                    trunc(book.getLanguage(), COL_LANG),
                    copies);
        }

        System.out.println(divider);
    }

    private static String trunc(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }

    // ── CRUD helpers ─────────────────────────────────────────────────────────

    public static void addBook(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n--Add New Book -----------------");
        System.out.print("ISBN      : "); String isbn      = scanner.nextLine();
        System.out.print("Title     : "); String title     = scanner.nextLine();
        System.out.print("Genre     : "); String genre     = scanner.nextLine();
        System.out.print("Author    : "); String author    = scanner.nextLine();
        System.out.print("Language  : "); String language  = scanner.nextLine();
        System.out.print("Publisher : "); String publisher = scanner.nextLine();
        System.out.print("Copies    : ");
        int copies = Integer.parseInt(scanner.nextLine().trim());

        try {
            libraryService.addBook(isbn, title, genre, author, language, publisher);
            libraryService.addCopies(isbn, copies);
            System.out.println("Book added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addCopy(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n--Add Copies -----------------");
        System.out.print("ISBN   : "); String isbn = scanner.nextLine();
        System.out.print("Amount : ");
        int amount = Integer.parseInt(scanner.nextLine().trim());

        try {
            libraryService.addCopies(isbn, amount);
            System.out.println("Copies added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void removeBook(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n--Remove Book -----------------");
        System.out.print("ISBN: "); String isbn = scanner.nextLine();

        try {
            libraryService.removeBook(isbn);
            System.out.println("Book removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
