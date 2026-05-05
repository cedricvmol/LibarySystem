package app;

import domain.Book;
import domain.BookCopy;
import service.LibraryService;

import java.util.Collection;
import java.util.Scanner;

public class LibrarianUI {


    public static void librarianMenu(LibraryService libraryService, Scanner scanner) {

        while (true) {
            System.out.println("\n──────── LIBRARIAN MENU ─────────");
            System.out.println("[1] Books");
            System.out.println("[2] Members");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    booksMenu(libraryService, scanner);
                    break;
                case "2":
                    membersMenu(libraryService,scanner);
                    break;
                case "b":
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
                    getBookByIsbn(libraryService, scanner);
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
                    removeBook(libraryService, scanner);
                    break;
                case "b":
                    return;
            }
        }
    }

    public static void membersMenu(LibraryService libraryService, Scanner scanner) {

        while (true) {
            System.out.println("\n──MEMBER MANAGEMENT ────────────────");
            System.out.println("[1] Register member");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    registerMember(libraryService, scanner);
                    break;
                case "b":
                    return;
            }
        }

    }

    public static void registerMember(LibraryService libraryService, Scanner scanner){

        System.out.println("\n── Register Member ─────────────────");
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        try {
            libraryService.registerMember(memberId,password,name,address,email,phone);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    public static void addBook(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n── Add New Book ─────────────────");
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
            if (loan_period.isEmpty()) {
                libraryService.addBook(isbn, title, genre, author, language, publisher, 14);
            } else {
                libraryService.addBook(isbn, title, genre, author, language, publisher, Integer.parseInt(loan_period));
            }
            libraryService.addCopies(isbn, copies);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addCopy(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n── Add New Copy ────────────────");
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

    public static void viewBooks(LibraryService libraryService) {
        Collection<Book> books = libraryService.getAllBooks();

        System.out.println("\n── ALL BOOKS ────────────────");
        System.out.printf("  %-13s  %-30s  %-25s  %-18s %s%n",
                "ISBN", "Title", "Author", "Genre", "Loan days");
        System.out.println("  " + "─".repeat(125));

        for (Book book : books) {
            System.out.printf("  %-13s  %-30s  %-25s  %-18s %d%n",
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getLoanPeriodDays());
        }
    }


    public static void getBookByIsbn(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n── SEARCH BOOK BY ISBN ────────────────");
        System.out.println("Search: ");
        String isbn = scanner.nextLine();

        try {
            Book searchedBook = libraryService.getBook(isbn);
            Collection<BookCopy> copies = libraryService.getAllCopies(isbn);

            System.out.printf("  %-13s  %-30s %-25s %s%n", "ISBN", "Title", "Author", "Books Available");
            System.out.println("  " + "─".repeat(90));

            System.out.printf("  %-13s  %-30s %-25s %d%n%n%n", searchedBook.getIsbn(), searchedBook.getTitle(), searchedBook.getAuthor(), copies.size());

            System.out.println("\n── ALL COPIES ──────────────────");
            System.out.printf("  %-13s  %-30s %-25s %s%n", "COPY-ID", "Title", "Author", "STATUS");
            System.out.println("  " + "─".repeat(90));

            for (BookCopy copy : copies) {
                System.out.printf("  %-13s  %-30s %-25s %s%n", copy.getCopyId(), copy.getBook().getTitle(), copy.getBook().getAuthor(), copy.getStatus());
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void removeBook(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n──REMOVE BOOK ────────────────");
        System.out.println("Give the ISBN of the book you want to remove:");
        String isbn = scanner.nextLine();


        try {
            Book removedBook = libraryService.getBook(isbn);
            libraryService.removeBook(isbn);
            System.out.println("  Successfully removed the following book:");
            System.out.printf("  %-13s  %-30s  %n", "ISBN", "Title");
            System.out.println("  " + "─".repeat(45));

            System.out.printf("  %-13s  %-30s%n%n%n", removedBook.getIsbn(), removedBook.getTitle());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
