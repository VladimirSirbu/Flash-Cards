package com.flashcards;

import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);

        Cards cards = new Cards();

        while(true) {
            System.out.println("\nInput the action (add, remove, import, export, ask, hardest card, reset status, exit):");
            switch (in.nextLine()) {
                case "add" -> cards.addCard();
                case "remove" -> cards.removeCard();
                case "import" -> cards.importCard();
                case "export" -> cards.exportCard();
                case "ask" -> cards.ask();
                case "hardest card" -> cards.hardestCard();
                case "reset status" -> cards.resetStats();
                case "exit" -> {
                    System.out.println("Bye bye!");
                    System.exit(0);
                }
                default -> System.out.println("Enter please the correct form from MENU!");
            }
        }
    }
}
