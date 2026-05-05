package app;

import service.LibraryService;

import java.util.Scanner;

public class MemberUI {

    public static void memberMenu(LibraryService libraryService, Scanner scanner){
        while (true) {
            System.out.println("\n──────── MEMBER MENU ─────────");
            System.out.println("[1] Log in");
            System.out.println("[b] Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    logIn(libraryService, scanner);
                    break;
                case "b":
                    return;
            }
        }
    }


    public static void logIn(LibraryService libraryService,Scanner scanner){

    }
}
