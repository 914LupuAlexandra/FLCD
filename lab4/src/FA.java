import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FA {
    private final String filename;
    private List<String> states;
    private List<String> alphabet;
    private List<List<String>> transitions;
    private String initialState;
    private List<String> finalStates;

    public FA(String filename) {
        this.filename = filename;
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.initialState = "";
        this.finalStates = new ArrayList<>();
    }

    public void readFile() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                String keyword = parts[0].trim();
                String value = parts[1].trim();

                switch (keyword) {
                    case "states" -> this.states = parseList(value);
                    case "alphabet" -> this.alphabet = parseList(value);
                    case "final_states" -> this.finalStates = parseList(value);
                    case "initial_state" -> this.initialState = value;
                    case "transitions" -> this.transitions = parseTransitions(value);
                    default -> throw new Exception("Error in the FA.in file. Unknown keyword!");
                }

            }

        } catch (IOException e) {
            System.err.println("Error reading the file!");
        }
    }

    private List<String> parseList(String input) {
        String[] items = input.substring(1, input.length() - 1).split(","); // we keep the elements in a list of strings
        List<String> result = new ArrayList<>();
        for (String item : items) {
            result.add(item.trim());
        }
        return result;
    }

    private List<List<String>> parseTransitions(String input) {
        String[] items = input.substring(1, input.length() - 1).split(";");
        List<List<String>> result = new ArrayList<>();
        for (String item : items) {
            String[] values = item.substring(1, item.length() - 1).split(",");
            List<String> transition = new ArrayList<>();
            transition.add(values[0]);
            transition.add(values[1]);
            transition.add(values[2]);
            result.add(transition);
        }
        return result;
    }

    public boolean isDeterministic() {
        Set<Pair<String, String>> seenTransitions = new HashSet<>();
        for (List<String> transition :transitions) {
            Pair <String, String> transitionPair = new Pair<>(transition.get(0), transition.get(2));
            if (!seenTransitions.add(transitionPair)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAccepted(String word) {
        char[] wordArray = word.toCharArray();
        String currentState = initialState;

        for (char c : wordArray) {
            boolean found = false;
            for (List<String> transition : transitions) {
                if (transition.get(0).equals(currentState) && transition.get(2).equals(String.valueOf(c))) {
                    currentState = transition.get(1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return finalStates.contains(currentState);
    }


    private void printList(String listName, List<String> list) {
        System.out.println(listName + ": {" + String.join(", ", list) + "}");
    }

    public void printStates() {
        printList("states", states);
    }

    public void printAlphabet() {
        printList("alphabet", alphabet);
    }

    public void printTransitions() {
        System.out.print("transitions: {");
        int i = 0;
        for (List<String> transition : transitions) {
            if (i == transitions.size() - 1) {
                System.out.print("(" + String.join(", ", transition) + ")");
            } else {
                System.out.print("(" + String.join(", ", transition) + "), ");
            }
            i++;
        }
        System.out.println("}");
    }

    public void printInitialState() {
        System.out.println("initial_state = " + initialState);
    }

    public void printFinalStates() {
        printList("final_states", finalStates);
    }
    void printFA() {
        printStates();
        printAlphabet();
        printTransitions();
        printInitialState();
        printFinalStates();
    }
}

