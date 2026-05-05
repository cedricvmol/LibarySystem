package app;

import domain.Member;
import service.LibraryService;

import java.util.Optional;
import java.util.Scanner;

public class MemberUI {

    public static void memberMenu(LibraryService libraryService, Scanner scanner) {

        Member member = logIn(libraryService,scanner);

        if(member == null){
            System.out.println("Invalid credentials.");
            return;
        }

        while (true) {
            System.out.println("\n──────── MEMBER MENU ─────────");
            System.out.println("[1] (PLACEHOLDER)");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": //Placeholder
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
}