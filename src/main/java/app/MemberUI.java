package app;

import domain.Book;
import domain.BookLoan;
import domain.Member;
import service.LibraryService;


import java.util.*;

public class MemberUI {

    public static void memberMenu(LibraryService libraryService, Scanner scanner) {

        Member member = logIn(libraryService,scanner);

        if(member == null){
            System.out.println("Invalid credentials.");
            return;
        }

        while (true) {
            System.out.println("\n──────── MEMBER MENU ─────────");
            System.out.println("[1] Search books.");
            System.out.println("[2] Loan a book.");
            System.out.println("[3] View my active loans.");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": searchBooks(libraryService,scanner);
                    break;
                case "2": loanBook(libraryService,scanner,member);
                    break;
                case "3": viewActiveLoans(libraryService,member);
                    break;
                case "b":
                    return;
            }
        }
    }

    public static Member logIn(LibraryService libraryService, Scanner scanner) {
        System.out.println("\n── LOGIN ───────────────");
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<Member> result = libraryService.getMember(memberId);

        if(result.isPresent()){
            if(result.get().getPassword().equals(password)){
                return result.get();
            }
        }
        return null;
    }

    public static void searchBooks(LibraryService libraryService,Scanner scan){

        System.out.println("\n── SEARCH BOOKS ───────────────");
        System.out.println("On what do you want to search? (Title, Genre, Author?)");
        String field = scan.nextLine();
        System.out.println("What is the query?");
        String query = scan.nextLine();

        try{
            Collection<Book> books = libraryService.searchBooks(query,field.toLowerCase());

            System.out.printf("  %-13s  %-30s  %-25s  %-18s %s%n",
                    "ISBN", "Title", "Author", "Genre", "Loan days");
            System.out.println("  " + "─".repeat(125));

            for(Book book : books){
                System.out.printf("  %-13s  %-30s  %-25s  %-18s %d%n",
                        book.getIsbn(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getLoanPeriodDays());
            }
        } catch (IllegalArgumentException e ){
            System.out.println(e.getMessage());
        }
    }

    public static void loanBook(LibraryService libraryService , Scanner scanner, Member member){
        System.out.println("\n── BORROW A BOOK ───────────────");
        System.out.println("Give an ISBN.");
        String isbn = scanner.nextLine();

        try {
            String memberId = member.getMemberId();

            libraryService.borrowBook(memberId,isbn);
            Book book = libraryService.getBook(isbn);

            System.out.println("You have successfully borrowed the following book: ");
            System.out.printf("  %-13s  %-30s  %-25s  %-18s%n",
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.getGenre());

        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    public static void viewActiveLoans(LibraryService libraryService, Member member){
        System.out.println("\n── ACTIVE LOANS ───────────────");

        Collection<BookLoan> loans = libraryService.getActiveLoansForMember(member.getMemberId());

        System.out.printf("  %-13s  %-30s  %-15s%n",
                "Loan ID", "Title", "Due date");
        System.out.println("  " + "─".repeat(75));

        for (BookLoan loan : loans){
            System.out.printf("  %-13s  %-30s  %-15s%n",
                    loan.getLoanId(), loan.getCopy().getBook().getTitle(), loan.getDueDate());
        }
    }


}