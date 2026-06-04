package app;

import domain.*;
import service.LibraryService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibrarianUI {


    public static void librarianMenu(LibraryService libraryService, Scanner scanner) {

        while (true) {
            System.out.println("\n──────── LIBRARIAN MENU ─────────");
            System.out.println("[1] Books");
            System.out.println("[2] Members");
            System.out.println("[3] Loans");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    booksMenu(libraryService, scanner);
                    break;
                case "2":
                    membersMenu(libraryService, scanner);
                    break;
                case "3":
                    loansMenu(libraryService,scanner);
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
            System.out.println("[1] Register Member");
            System.out.println("[2] View All Members");
            System.out.println("[3] Remove Member");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    registerMember(libraryService, scanner);
                    break;
                case "2":
                    viewAllMembers(libraryService);
                    break;
                case "3":
                    removeMember(libraryService,scanner);
                    break;
                case "b":
                    return;
            }
        }

    }

    public static void loansMenu(LibraryService libraryService , Scanner scanner) {


        while (true) {
            System.out.println("\n──LOANS MANAGEMENT ────────────────");
            System.out.println("[1] View all active loans.");
            System.out.println("[2] Process return.");
            System.out.println("[3] View all pending reservations.");
            System.out.println("[b] Back");


            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    viewAllActiveLoans(libraryService);
                    break;
                case "2":
                    returnBook(libraryService,scanner);
                    break;
                case "3":
                    viewAllReservations(libraryService);
                    break;
                case "b":
                    return;
            }
        }
    }

    public static void returnBook(LibraryService libraryService,Scanner scanner){
        System.out.println("\n── RETURN BOOK ───────────────");
        System.out.println("Give the loan ID for the returned book.");
        String loanId = scanner.nextLine();

        try {
            libraryService.returnBook(loanId);
            System.out.println("U have successfully returned the book.");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    public static void viewAllReservations(LibraryService libraryService){
        System.out.println("\n── ACTIVE RESERVATIONS ───────────────");

        List<Reservation> reservations = libraryService.getAllPendingReservations();

        System.out.printf("  %-16s  %-15s  %-30s  %-15s  %-15s%n",
                "Reservation ID","Member", "Title","Status","Reservation Date");
        System.out.println("  " + "─".repeat(100));


        for (Reservation reservation : reservations){
            System.out.printf("  %-16s  %-15s  %-30s  %-15s  %-15s %n",
                    reservation.getReservationId(),reservation.getMember().getMemberId(),reservation.getBook().getTitle(),reservation.getReservationStatus(),reservation.getReservationDate());
        }
    }

    public static void viewAllActiveLoans(LibraryService libraryService) {
        System.out.println("\n── ACTIVE LOANS ───────────────");

        Collection<BookLoan> loans = libraryService.getAllActiveLoans();

        System.out.printf("  %-13s  %-13s  %-30s  %-15s%n",
                "Loan ID","Member ID", "Title", "Due date");
        System.out.println("  " + "─".repeat(75));

        for (BookLoan loan : loans) {
            System.out.printf("  %-13s  %-13s  %-30s  %-15s%n",
                    loan.getLoanId(),loan.getMember().getMemberId() ,loan.getCopy().getBook().getTitle(), loan.getDueDate());
        }
    }

    public static void registerMember(LibraryService libraryService, Scanner scanner) {

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
            libraryService.registerMember(memberId, password, name, address, email, phone);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void viewAllMembers(LibraryService libraryService) {

        Collection<Member> members = libraryService.getAllMembers();

        System.out.println("\n── ALL MEMBERS ────────────────");
        System.out.printf("  %-13s  %-20s  %-40s  %-25s %s%n",
                "Member ID", "Name", "Address", "Email", "Phone number");
        System.out.println("  " + "─".repeat(125));

        for (Member member : members) {
            System.out.printf("  %-13s  %-20s  %-40s  %-25s %s%n",
                    member.getMemberId(), member.getName(), member.getAddress(), member.getEmail(), member.getPhone());
        }
    }

    public static void removeMember(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n──REMOVE MEMBER ────────────────");
        System.out.println("Give the Member ID of the member you want to remove:");
        String memberId = scanner.nextLine();

        Optional<Member> result = libraryService.getMember(memberId);

        if (result.isPresent()) {
            libraryService.removeMember(memberId);
            System.out.println("  Successfully removed the following member:");
            System.out.printf("  %-13s  %-30s  %n", "Member ID", "Name");
            System.out.println("  " + "─".repeat(45));
            System.out.printf("  %-13s  %-30s%n%n%n", result.get().getMemberId(), result.get().getName());
        } else {
            System.out.println("The member was not found.");
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


        List<Book> books = libraryService.getAllBooks().stream().sorted().toList();

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
