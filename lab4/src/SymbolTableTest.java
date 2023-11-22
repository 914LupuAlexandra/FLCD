import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    @Test
    void getSize() {
        SymbolTable st = new SymbolTable(3);
        assertEquals(st.getSize(), 3);
    }

    @Test
    void findElemPosition() {
        SymbolTable st = new SymbolTable(3);
        st.add("a");
        st.add("b");
        st.add("d");
        assertAll(() -> assertEquals(st.findElemPosition("a").getFirst(), 1),
                () -> assertEquals(st.findElemPosition("a").getSecond(), 0),
                () -> assertEquals(st.findElemPosition("d").getFirst(), 1),
                () -> assertEquals(st.findElemPosition("d").getSecond(), 1),
                () -> assertEquals(st.findElemPosition("b").getFirst(), 2),
                () -> assertEquals(st.findElemPosition("b").getSecond(), 0));
    }

    @Test
    void contains() {
        SymbolTable st = new SymbolTable(3);
        st.add("a");
        assertAll(() -> assertEquals(st.contains("a"), true),
                () -> assertEquals(st.contains("b"), false));
    }

    @Test
    void findByPosition() {
        SymbolTable st = new SymbolTable(3);
        st.add("a");
        st.add("b");
        st.add("d");
        assertAll(() -> assertEquals(st.findByPosition(new Pair(1, 0)), "a"),
                () -> assertEquals(st.findByPosition(new Pair(1, 1)), "d"),
                () -> assertEquals(st.findByPosition(new Pair(2, 0)), "b"));
    }

    @Test
    void isEmpty() {
        SymbolTable st = new SymbolTable(3);
        assertAll(() -> assertEquals(st.isEmpty(), true),
                () -> {
                    st.add("re");
                    assertEquals(st.isEmpty(), false);
                });

    }

    @Test
    void getNrElems() {
        SymbolTable st = new SymbolTable(3);
        st.add("a");
        st.add("b");
        st.add("d");
        assertEquals(st.getNrElems(), 3);
    }

    @Test
    void clear() {
        SymbolTable st = new SymbolTable(3);
        st.add("a");
        assertAll(() -> assertEquals(st.getNrElems(), 1),
                () -> {
                    st.clear();
                    assertEquals(st.getNrElems(), 0);
                });
    }

    @Test
    void remove() {
        SymbolTable st = new SymbolTable(3);
        st.add("a");
        assertAll(() -> assertEquals(st.remove("a"), true),
                () -> assertEquals(st.remove("a"), false));
    }

    @Test
    void add() {
        SymbolTable st = new SymbolTable(3);
        assertAll(() -> assertEquals(st.add("a"), true),
                () -> assertEquals(st.add("a"), false),
                () -> assertEquals(st.add("d"), true),
                () -> assertEquals(st.add("b"), true));
    }
}