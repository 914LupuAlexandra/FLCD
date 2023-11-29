import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class MyScanner {
    private String filePath;
    private List<String> tokenFile;

    private SymbolTable st;

    private ProgramInternalForm pif;

    private final ArrayList<String> operators = new ArrayList<>(
            List.of("<=", ">=", "==", "!=", "<", ">", "=", "+", "-", "*", "/", "%")
    );

    private final ArrayList<String> separators = new ArrayList<>(
            List.of("{", "}", "(", ")", "[", "]", ":", ";", " ", ",", "\n", "'", "\"")
    );

    private final ArrayList<String> reservedWords = new ArrayList<>(
            List.of("if", "else", "elif", "and", "for", "while", "int", "string", "char", "return", "start",
                    "array", "input", "print", "definition")
    );

    public MyScanner(String filePath) {
        this.filePath = filePath;
        this.st = new SymbolTable(100);
        this.pif = new ProgramInternalForm();
        this.tokenFile = readTokenFile("in_out/token.in");
    }

    public List<String> readTokenFile(String tokenFilePath) {
        List<String> tokens = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(tokenFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                tokens.add(line);
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the token.in file: " + e.getMessage());
        }
        return tokens;
    }

    // we'll read the file and replace the tabs in order to tokenize the content
    private String readFile() throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(this.filePath));
        while (scanner.hasNextLine()) {
            fileContent.append(scanner.nextLine()).append("\n");
        }
        return fileContent.toString().replace("\t", "");
    }

    private List<Pair<String, Pair<Integer, Integer>>> createListOfTokens() {
        try {
            String content = this.readFile();
            String separatorsString = this.separators.stream().reduce("", (a, b) -> (a + b));
            StringTokenizer tokenizer = new StringTokenizer(content, separatorsString, true); // break a string into tokens by separators
            List<String> tokens = new ArrayList<>(); // used to transform the StringTokenizer into a list of tokens

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                tokens.add(token);
            }

            return tokenAndPosition(tokens);
        } catch (FileNotFoundException e) {
            System.out.println("No such file found!");
            ;
        }

        return null;
    }


    // return a list with the token and the position in the source code
    private List<Pair<String, Pair<Integer, Integer>>> tokenAndPosition(List<String> tokensToBe) {

        List<Pair<String, Pair<Integer, Integer>>> resultedTokens = new ArrayList<>(); // pif
        int numberLine = 1; // used for error
        int numberColumn = 1; // used for error
        // System.out.println(tokensToBe);
        for (String t : tokensToBe) {
            if (t.equals("\n")) {
                numberLine++;
                numberColumn = 1;
            } else if (!t.equals(" ")) { // we don't add the whitespaces
                resultedTokens.add(new Pair<>(t, new Pair<>(numberLine, numberColumn)));
                numberColumn++;
            }
        }
//        System.out.println(resultedTokens);
        return resultedTokens;
    }

    private boolean isCharValid(char ch, List<String> tokenFile) {
        // Check if the character is valid based on the characters in tokenFile.
        String charStr = Character.toString(ch);
        return tokenFile.contains(charStr);
    }

    public void scan() {

        List<Pair<String, Pair<Integer, Integer>>> tokens = createListOfTokens();
        AtomicReference<Boolean> error = new AtomicReference<>(false);

        if (tokens == null) {
            return;
        }

        tokens.forEach(t -> {
            String token = t.getFirst();

            // check if the token contains invalid characters
            boolean containsInvalidCharacter = token.chars()
                    .mapToObj(ch -> (char) ch)
                    .anyMatch(ch -> !isCharValid(ch, tokenFile));

            if (containsInvalidCharacter) {
                Pair<Integer, Integer> pairLineColumn = t.getSecond();
                System.out.println(filePath + " - ERROR at line: " + pairLineColumn.getFirst() + " and column: " + pairLineColumn.getSecond() + ", invalid token: " + t.getFirst());
                error.set(true);
                return; // there's an invalid char
            }

            if (this.reservedWords.contains(token) || this.operators.contains(token) || this.separators.contains(token)) {
                this.pif.add(new Pair<>(token, new Pair<>(-1, -1)));
            } else if (Pattern.compile("^0|[-+]?[1-9][0-9]*|'[1-9]'*|\"[0-9]*[a-zA-Z]*\"$").matcher(token).matches()) { // constant
                System.out.println(token + " ----- ct");
                this.st.add(token);
                this.pif.add(new Pair<>(token, st.findElemPosition(token)));
            } else if (Pattern.compile("^([a-zA-Z])|[a-zA-Z_]*$").matcher(token).matches()) { // identifier
                System.out.println(token + " ----- id");
                this.st.add(token);
                this.pif.add(new Pair<>(token, st.findElemPosition(token)));
            } else {
                Pair<Integer, Integer> pairLineColumn = t.getSecond();
                System.out.println(filePath + " - ERROR at line: " + pairLineColumn.getFirst() + " and column: " + pairLineColumn.getSecond() + ", invalid token: " + t.getFirst());
                error.set(true);
            }
        });

        if (!error.get()) {
            System.out.println(filePath + " - lexically CORRECT");
        }

    }

    public ProgramInternalForm getPif() {
        return this.pif;
    }

    public SymbolTable getSt() {
        return this.st;
    }

}
