import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Scanner {

    private ArrayList<String> reservedWords = new ArrayList<>(List.of("if", "read", "display", "else", "int", "char", "array", "shots", "cascade"));
    private ArrayList<String> operators = new ArrayList<>(List.of("+", "-", "*", "/", "%", "LT", "LE", "NE", "EQ", "GT", "GE", "=", "&&", "||"));
    private ArrayList<String> separators = new ArrayList<>(List.of("{", "}", "(", ")", "[", "]", "<", ">", ",", ";", ".", "#",
            ":", "\""));
    private SymbolTable identifiersSymbolTable;
    private SymbolTable constantsSymbolTable;
    private List<Pair<String,Integer>> pif;
    private String file;
    private int index;
    private int currentLine;
    private String line;


    public Scanner(String file) throws IOException {
        this.identifiersSymbolTable = new SymbolTable(30);
        this.constantsSymbolTable = new SymbolTable(30);
        this.pif = new ArrayList<>();
        this.file = file;
        this.index = 0;
        this.currentLine = 0;
    }

    private void skipWhiteSpaces() {
        while (index < line.length() && Character.isWhitespace(line.charAt(index))) {
            index++;
        }
    }
    public boolean stringConstantCase() {
        var stringConstantRegex = Pattern.compile("^\"[\\w ]*\"");
        var matcher = stringConstantRegex.matcher(line.substring(index));

        if (!matcher.find()) {
            return false;
        }
        var stringConstant = matcher.group(0);
        index += stringConstant.length();
        constantsSymbolTable.addSym(stringConstant);
        var position = constantsSymbolTable.getPos(stringConstant);
        pif.add(new Pair(stringConstant, position));
        return true;
    }
    public boolean intConstantCase() {
        var intConstantRegex = Pattern.compile("^([+-]?[1-9][0-9]*|0)");
        var matcher = intConstantRegex.matcher(line.substring(index));

        var invalidIntConstantRegex = Pattern.compile("([+-]?[1-9][0-9]*|0)[a-zA-Z]+");
        if (!matcher.find() || invalidIntConstantRegex.matcher(line.substring(index)).find()) {
            return false;
        }
        var intConstant = matcher.group(0);
        index += intConstant.length();
        constantsSymbolTable.addSym(intConstant);
        var position = constantsSymbolTable.getPos(intConstant);
        pif.add(new Pair(intConstant, position));
        return true;
    }

    public boolean identifierCase() {
        var identifierRegex = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
        var matcher = identifierRegex.matcher(line.substring(index));
        if (!matcher.find()) {
            return false;
        }
        var identifier = matcher.group(0);

        if (reservedWords.contains(identifier) || operators.contains(identifier) ) {
            return false;
        }
        // see if it is already in the symbol table
        var position = identifiersSymbolTable.getPos(identifier);
        if (identifiersSymbolTable.contains(identifier)) {
            index += identifier.length();
            pif.add(new Pair("id", position));
            return true;
        }

        index += identifier.length();
        identifiersSymbolTable.addSym(identifier);
        position = identifiersSymbolTable.getPos(identifier);
        pif.add(new Pair("id", position));
        return true;
    }
    public boolean tokenCase() {
        String possibleToken = line.substring(index).split(" ")[0];
        for (var op: operators) {
            if(Objects.equals(op, possibleToken)) {
                index += op.length();
                pif.add(new Pair(op, -1));
                return true;
            }
            else if (possibleToken.startsWith(op)) {
                //if(op.length() < op.length())
                index += op.length();
                pif.add(new Pair(op, -1));
                return true;
            }
        }

        for (var sep: separators) {
            if (possibleToken.startsWith(sep)) {
                index += sep.length();
                pif.add(new Pair(sep, -1));
                return true;
            }
        }
        for (var rw: reservedWords) {
            if (possibleToken.startsWith(rw)) {
                var invalidRegex = Pattern.compile(rw + "[^()]+");
                if (invalidRegex.matcher(possibleToken).find() ) {
                    return false;
                }
                index += rw.length();
                pif.add(new Pair(rw, -1));
                return true;
            }
        }
        return false;
    }
    public void next() throws Exception {
        skipWhiteSpaces();
        if (index == line.length())
            return;
        if (stringConstantCase())
            return;
        if (intConstantCase())
            return;
        if (identifierCase())
            return;
        if (tokenCase())
            return;
        FileWriter stWriter = new FileWriter("ST.out");
        stWriter.write("Identifiers:\n" + identifiersSymbolTable.toString() + "\nConstants:\n" + constantsSymbolTable.toString());

        stWriter.close();
        throw new Exception("Program has lexical error at line " + currentLine);
    }
    public void scan() {

        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/" +file));
            this.index = 0;
            this.currentLine = 0;
            while((line = reader.readLine()) != null){
                this.currentLine++;
                this.index = 0;
                line = line.strip();

                while(index<line.length()){
                    next();
                }
            }

            FileWriter pifWriter = new FileWriter("PIF.out");
            for (Pair<String, Integer> stringIntegerPair : pif) {
                pifWriter.write(stringIntegerPair.getFirst() + " => " + stringIntegerPair.getSecond() + "\n");
            }
            pifWriter.close();
            FileWriter stWriter = new FileWriter("ST.out");
            stWriter.write("Identifiers:\n" + identifiersSymbolTable.toString() + "\nConstants:\n" + constantsSymbolTable.toString());
            stWriter.close();
            System.out.println("No lexical errors");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
