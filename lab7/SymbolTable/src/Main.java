import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static void printToFile(String filePath, Object object) {
        try (PrintStream printStream = new PrintStream(filePath)) {
            printStream.println(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void run(String filePath) {
        MyScanner scanner = new MyScanner(filePath);
        scanner.scan();
        printToFile(filePath.replace(".txt", "ST.txt"), scanner.getSt());
        printToFile(filePath.replace(".txt", "PIF.txt"), scanner.getPif());
    }

    public static void printFAMenu() {
        System.out.println("Menu: ");
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print transitions");
        System.out.println("4. Print initial state");
        System.out.println("5. Print final states");
        System.out.println("6. Check word");
        System.out.println("7. Check if it's deterministic");
        System.out.println("0. Exit");
    }

    public static void runGrammar() {
        Grammar grammar = new Grammar("in_out/g2.txt");
        System.out.println(grammar.getNonTerminals());
        System.out.println(grammar.getTerminals());
        System.out.println(grammar.getIsCFG());
        System.out.println(grammar.getProductions());
       // List<List<String>> prod = grammar.getProductions().values().stream().toList();
       // System.out.println(grammar.getProductions());
    }



    public static void runFA() {
        FA fa = new FA("in_out/FA.in");
        try {
            fa.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            printFAMenu();
            System.out.println("Choose: ");
            try {
                int option = new Scanner(System.in).nextInt();
                if (option == 0) {
                    break;
                }
                switch (option) {
                    case 1:
                        fa.printStates();
                        break;
                    case 2:
                        fa.printAlphabet();
                        break;
                    case 3:
                        fa.printTransitions();
                        break;
                    case 4:
                        fa.printInitialState();
                        break;
                    case 5:
                        fa.printFinalStates();
                        break;
                    case 6:
                        String word = new Scanner(System.in).nextLine();
                        System.out.println(fa.checkAccepted(word));
                        break;
                    case 7:
                        System.out.println(fa.isDeterministic());
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input a number");
            }
        }
    }

    public static void runParser() {
        Parser parser = new Parser();
        String str = "program";
        parser.checkSequence();
    }
    public static void main(String[] args) {
//        run("in_out/p1.txt");
//        run("in_out/p2.txt");
//        run("in_out/p3.txt");
//        run("in_out/p1err.txt");
//        runGrammar();
        runParser();

    }
}