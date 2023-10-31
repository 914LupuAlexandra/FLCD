import java.util.ArrayList;

public class HashTable {
    private ArrayList<ArrayList<String>> hashTable;
    private Integer size;

    public HashTable(Integer size) {
        this.size = size;
        this.hashTable = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.hashTable.add(new ArrayList<>());
        }
    }

    public Integer getSize() {
        return size;
    }

    // hash function
    public Integer hash(String key) {
        int sum = 0;
        char[] characters = key.toCharArray();
        for (char c: characters) {
            sum += c;
        }
        return sum % size;
    }

    // returns a pair with the position of the element from the both arrays
    public Pair findElemPosition(String elem) {
        int pos = hash(elem);
        if (!hashTable.get(pos).isEmpty()) {
            ArrayList<String> elems = this.hashTable.get(pos);
            for(int i = 0; i < elems.size(); i++) {
                if (elems.get(i).equals(elem)) {
                    return new Pair(pos, i);
                }
            }
        }
        return null;
    }

    public Boolean contains(String elem) {
        return this.findElemPosition(elem) != null;
    }

    public Boolean add(String elem) {
        if(contains(elem)) {
            return false;
        }
        Integer pos = hash(elem);
        ArrayList<String> elems = this.hashTable.get(pos);
        elems.add(elem);
        return true;
    }

    public String findByPosition (Pair position) throws Exception{
        if (this.hashTable.size() <= position.getFirst() || this.hashTable.get(position.getFirst()).size() <= position.getSecond()) {
            throw new Exception("Invalid position");
        }
        return this.hashTable.get(position.getFirst()).get(position.getSecond());
    }

    public Boolean isEmpty() {
       for (ArrayList<String> bucket: hashTable) {
           if (!bucket.isEmpty()) { // at least one bucket is not empty
               return false;
           }
       }
       return true; // all buckets are empty
    }

    public int getNrElems() {
        int count = 0;
        for (ArrayList<String> bucket: hashTable) {
            count += bucket.size();
        }
        return count;
    }

    // clears every element from the arrays
    public void clear() {
        for (ArrayList<String> bucket: hashTable) {
            bucket.clear();
        }
    }

    public Boolean remove(String elem) {
        Pair position = findElemPosition(elem);
        if (position != null) {
            int bucketIndex = position.getFirst();
            int elemIndex = position.getSecond();
            hashTable.get(bucketIndex).remove(elemIndex);
            return true;
        }
        return false;
    }

}
