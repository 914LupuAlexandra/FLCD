import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<String, Pair<Integer, Integer>>> tokenPosition;

    public ProgramInternalForm() {
        this.tokenPosition = new ArrayList<>();
    }

    public void add(Pair<String, Pair<Integer, Integer>> pair) {
        this.tokenPosition.add(pair);
    }

    @Override
    public String toString() {
        StringBuilder computedString = new StringBuilder();
        for(int i = 0; i < this.tokenPosition.size(); i++) {
            computedString.append(this.tokenPosition.get(i).getFirst())
                    .append(" - > [")
                    .append(this.tokenPosition.get(i).getSecond().getFirst())
                    .append(", ")
                    .append(this.tokenPosition.get(i).getSecond().getSecond())
                    .append("]\n");
        }

        return computedString.toString();
    }
}
