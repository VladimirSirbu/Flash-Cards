package com.flashcards;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;

class Cards{
    private final Map<String, String> flashCards;
    private final Map<String, Integer> hardestCard;
    private final Scanner in;
    private String term;
    private String definition;
    private int mistakes;

    public Cards() {
        this.flashCards = new LinkedHashMap<>();
        this.hardestCard = new LinkedHashMap<>();
        this.in = new Scanner(System.in);
        this.term = null;
        this.definition = null;
        this.mistakes = 0;
    }

    protected void addCard(){
        System.out.println("The card:");
        term = in.nextLine();
        if(flashCards.containsKey(term)){
            System.out.println("The card \"" + term + "\" already exists.");
            term = null;
            return;
        }

        System.out.println("The definition of the card:");
        definition = in.nextLine();
        if(flashCards.containsValue(definition)){
            System.out.println("The definition \"" + definition + "\" already exists.");
            definition = null;
            return;
        }

        flashCards.put(term,definition);
        hardestCard.put(term,0);
        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    protected void removeCard(){
        System.out.println("The card:");
        term = in.nextLine();
        if(flashCards.containsKey(term)) {
            flashCards.remove(term);
            hardestCard.remove(term);
            System.out.println("The card has been removed.");
        } else
            System.out.println("Can't remove \"" + term + "\", there is no such card.");
    }

    protected void importCard(){
        int count = 0;
        System.out.println("File name:");
        String pathToFile = in.nextLine();
        try (Scanner scanner = new Scanner(new File(pathToFile))) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                term = line.split(":")[0];
                definition = line.split(":")[1];
                mistakes = Integer.parseInt(line.split(":")[2]);
                flashCards.put(term,definition);
                hardestCard.put(term,mistakes);
                count++;
            }
            System.out.println(count + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    protected void exportCard(){
        System.out.println("File name:");
        String pathToFile = in.nextLine();
        try (FileWriter fileWriter = new FileWriter(new File(pathToFile))) {
            for (var cards: flashCards.entrySet()) {
                fileWriter.write(cards.getKey() + ":" + cards.getValue() + ":" + hardestCard.get(cards.getKey()) + "\n");
            }
            System.out.println(flashCards.size() + " cards have been saved.");
        } catch (IOException e) {
            System.out.println("An exception occurs %s" + e.getMessage());
        }
    }

    protected void ask(){
        if(flashCards.size()!=0) {
            System.out.println("How many times to ask?");
            String numOfQuestion = in.nextLine();
            String answer;
            int i = 0;
            while (i < Integer.parseInt(numOfQuestion)) {
                for (var entry : flashCards.entrySet()) {
                    if (i++ < Integer.parseInt(numOfQuestion)) {
                        System.out.println("Print the definition of \"" + entry.getKey() + "\":");
                        answer = in.nextLine();
                        if (answer.equals(entry.getValue()))
                            System.out.println("Correct answer.");
                        else if (flashCards.containsValue(answer)) {
                            System.out.println("Wrong answer. (The correct one is \"" + entry.getValue() + "\", " +
                                    " you've just written the definition of \"" + getKeyByValue(flashCards, answer) + "\" card.)");
                            //Count errors for this card
                            countCardErrors(hardestCard, entry.getKey());
                        } else {
                            System.out.println("Wrong answer. The correct one is \"" + entry.getValue() + "\".");
                            //Count errors for this card
                            countCardErrors(hardestCard, entry.getKey());
                        }
                    } else
                        break;
                }
            }
        }else
            System.out.println("There are no cards.");
    }

    protected void hardestCard(){
        if(hardestCard.size()!=0) {
            //sort hardestCard map by value in desc order
            Map<String, Integer> sortedByValueDesc = hardestCard.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            int maxValue = sortedByValueDesc.values().stream().findFirst().orElseThrow();
            if (maxValue == 0) {
                System.out.println("There are no cards with errors.");
            } else {
                // add to a list all cards with maximum mistakes
                List<String> mistakeCards = new LinkedList<>();
                for (var entry : sortedByValueDesc.entrySet())
                    if (entry.getValue().equals(maxValue))
                        mistakeCards.add(entry.getKey());
                    else
                        break;
                // output hardest card from list
                if (mistakeCards.size() == 1)
                    System.out.print("The hardest card is \"" + mistakeCards.get(0) + "\". ");
                else if (mistakeCards.size() > 1) {
                    System.out.print("The hardest card is ");
                    for (String country : mistakeCards)
                        if (country.equals(mistakeCards.get(mistakeCards.size() - 1)))
                            System.out.print("\"" + country + "\". ");
                        else
                            System.out.print("\"" + country + "\", ");
                }
                System.out.println(" You have " + maxValue + " errors answering them.");
            }
        } else
            System.out.println("There are no cards with errors.");
    }

    protected void resetStats(){
        hardestCard.replaceAll((key, value) -> 0);
        System.out.println("Card statistics has been reset.");
    }

    private <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (var entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void countCardErrors(Map<String, Integer> hardestCard, String key){
        if (hardestCard.containsKey(key))
            hardestCard.put(key, hardestCard.get(key)+1);
        else
            hardestCard.put(key, 1);
    }

}