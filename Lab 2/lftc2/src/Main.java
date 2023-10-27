public class Main {
    public static void main(String[] args) {
        Hashtable<String, Integer> hashTable = new Hashtable<>(10);

        hashTable.add("10", 5);
        hashTable.add("20", 2);
        System.out.println(hashTable.get("10"));
        hashTable.remove("gg");

        SymbolTable SymbolTable = new SymbolTable(10);
        //SymbolTable constantsSymbolTable = new SymbolTable(10);

        SymbolTable.add("x", 1);





    }
}