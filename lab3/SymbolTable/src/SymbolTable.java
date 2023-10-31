public class SymbolTable {
    private final HashTable hashTable;
    private Integer size;

    public SymbolTable(Integer size) {
        this.hashTable = new HashTable(size);
    }

    public Integer getSize() {
        return hashTable.getSize();
    }

    public Pair findElemPosition(String elem) {
        return hashTable.findElemPosition(elem);
    }

    public Boolean contains(String elem) {
        return hashTable.contains(elem);
    }

    public String findByPosition (Pair position) throws Exception {
        return hashTable.findByPosition(position);
    }

    public Boolean isEmpty() {
        return hashTable.isEmpty();
    }

    public int getNrElems() {
        return hashTable.getNrElems();
    }

    public void clear() {
        hashTable.clear();
    }

    public Boolean remove(String elem) {
        return this.hashTable.remove(elem);
    }

    public Boolean add(String elem) {
        return this.hashTable.add(elem);
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "hashTable=" + hashTable +
                ", size=" + size +
                '}';
    }
}
