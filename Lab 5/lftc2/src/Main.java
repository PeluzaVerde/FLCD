import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

//        SymbolTable st = new SymbolTable(30);
//        st.addSym("Result");
//        System.out.println(st.toString());
        //Scanner s = new Scanner("new 1.txt");
        //s.scan();

        FA fa;

         {
            try {
                fa = new FA("src/FA.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Scanner scan= new Scanner(System.in);

        label:
        while (true) {
            printFAMenu();

            String option = scan.nextLine();

            System.out.println("-------------------------------------------");

            switch (option) {
                case "0":
                    break label;
                case "1":
                    System.out.println(fa.statesToString());
                    break;
                //break;
                case "2":
                    System.out.println(fa.alphabetToString());
                    break;
                //break;
                case "3":
                    System.out.println(fa.transitionsToString());
                    break;
                //break;
                case "4":
                    System.out.println(fa.initialStateToString());
                    break;
                //break;
                case "5":
                    System.out.println(fa.finalStatesToString());
                    break;
                case"6":
                    String sequence = scan.nextLine();
                    System.out.println(fa.verifyTheSequence(sequence));
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            }
        System.out.println("-------------------------------------------");



        }


    public static void printFAMenu() {
        System.out.println("1.States");
        System.out.println("2.Alphabet");
        System.out.println("3.Transitions");
        System.out.println("4.Initial state");
        System.out.println("5.Final states");
        System.out.println("6.Verify the sequence");
        System.out.println("0.exit");
        System.out.print("Pick an option: ");
    }



}