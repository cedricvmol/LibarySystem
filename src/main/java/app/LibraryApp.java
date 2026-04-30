package app;

import service.BookService;
import service.LibraryService;
import service.LoanService;
import service.MemberService;

import java.util.Scanner;

public class LibraryApp {


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

            String input = scanner.nextLine().trim();

            switch (input) {
                case "3":
                    addBook(libraryService, scanner);
                    break;
                case "4":
                    addCopy(libraryService, scanner);
                    break;
                case "B":
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
        System.out.printf("Amount of copies : ");
        int copies = scanner.nextInt();
        scanner.nextLine();

        try {
            libraryService.addBook(isbn, title, genre, author, language, publisher);
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


}
