public class SymbolTable {
    private Hashtable<String, Integer> symbolTable;

    public SymbolTable(int capacity) {
        symbolTable = new Hashtable<>( capacity);
    }

    public Integer add(String key, int value) {
        return symbolTable.add(key, value);
    }

    public void remove(String key) {
        symbolTable.remove(key);
    }

    public int get(String key) {
        return symbolTable.get(key);
    }

    public boolean contains(String key) {
        return symbolTable.get(key) != null;
    }
}
