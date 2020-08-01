package com.flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Cards cards = new Cards();

        while(true) {
            System.out.println("\nInput the action (add, remove, import, export, ask, hardest card, reset stats, exit):");
            switch (in.nextLine()){
                case "add":
                    cards.addCard();
                    break;
                case "remove":
                    cards.removeCard();
                    break;
                case "import":
                    cards.importCard();
                    break;
                case "export":
                    cards.exportCard();
                    break;
                case "ask":
                    cards.ask();
                    break;
                case "hardest card":
                    cards.hardestCard();
                    break;
                case "reset stats":
                    cards.resetStats();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    System.exit(0);
                default:
                    System.out.println("Enter please the correct form from MENU!");
            }
        }
    }
}
