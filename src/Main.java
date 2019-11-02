import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Main {
    public static String[] IdentifierType = {"int", "String", "char", "byte", "short", "long", "float", "double", "boolean"};
    public static String[] Symbols = {"=", "{", "}", "[]", ";", ","};
    public static String[] Operator = {"+", "-", "*", "/"};
    public static String[] JavaKeywords = {"abstract", "assert", "break", "case", "catch", "class", "const", "continue", "default", "do", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "nstanceof", "interface", "native", "new", "package", "private", "protected", "public", "return", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"};
    public static String[] Digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

    public static void ekranaYaz(String bir, String iki) {
        System.out.println(bir + " ----------> " + iki);
    }

    public static boolean bul(String ilk, String array[]) {
        boolean result = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(ilk)) {
                return true;
            }
        }
        return result;
    }

    public static void AssigmentStatement(Stack stack) {
        int durum = 1;
        while (!stack.isEmpty()) {
            String lex = (String) stack.pop();
            if (durum == 1 && lex.equals(Symbols[3])) {

                durum = 3;
                ekranaYaz(lex, " Special Symbol");
                ekranaYaz((String) stack.pop(), " Identifier");
            } else if (durum == 3 && lex.equals(Symbols[4])) {
                durum = 4;
                ekranaYaz(lex, "Semi Colon");
            } else if (durum == 3 && lex.equals(Symbols[0])) {
                durum = 5;
                ekranaYaz(lex, "Assigment");
            } else if (durum == 5 && lex.equals(Symbols[1])) {
                ekranaYaz(lex, "Special Symbol");
                ekranaYaz((String) stack.pop(), "Constant");
                durum = 9;
            } else if (durum == 9 && lex.equals(Symbols[5])) {
                durum = 9;
                ekranaYaz((String) stack.pop(), "Constant");
            } else if (durum == 9 && lex.equals(Symbols[2])) {
                durum = 10;
                ekranaYaz(lex, "Special Symbol");
            } else if (durum == 10 && lex.equals(Symbols[4])) {
                durum = 4;
                ekranaYaz(lex, "Semi Colon");
            } else if (durum == 1 && !lex.equals(Symbols[3])) {
                durum = 7;
                ekranaYaz(lex, "Identifier");

            } else if (durum == 7 && lex.equals(Symbols[0])) {
                durum = 2;
                ekranaYaz(lex, "Assigment");
                ekranaYaz((String) stack.pop(), "Constant");
            } else if (durum == 2 && lex.equals(Symbols[4])) {
                durum = 4;
                ekranaYaz(lex, "Semi Colon");
            } else if (durum == 7 && lex.equals(Symbols[4])) {
                durum = 4;
                ekranaYaz(lex, "Semi Colon");
            } else if (durum == 7 && lex.equals(Symbols[3])) {
                durum = 13;
                ekranaYaz(lex, "Special Symbol");

            } else if (durum == 13 && lex.equals(Symbols[4])) {
                durum = 4;
                ekranaYaz(lex, "Semi Colon");
            } else if (durum == 13 && lex.equals(Symbols[0])) {
                durum = 14;
                ekranaYaz(lex, "Assigment");
            } else if (durum == 14 && lex.equals(Symbols[1])) {
                durum = 9;
                ekranaYaz(lex, "Special Symbol");
                ekranaYaz((String) stack.pop(), "Constant");

            } else if (durum == 2 && bul(lex, Operator)) {
                durum = 2;
                ekranaYaz(lex, "Operator");
                ekranaYaz((String) stack.pop(), "Constant");
            } else if (durum == 2 && lex.equals(Symbols[4])) {
                durum = 4;
                ekranaYaz(lex, "Semi Colon");

            } else if (durum == 4) {
                break;
            } else {

            }


        }

    }

    public static void Kontrol(Stack stack) {
        System.out.println("Lexeme ----------- Token");
        String data = (String) stack.pop();
        if (bul(data, IdentifierType)) {
            System.out.println(data + " ----------> Keyword");
            AssigmentStatement(stack);

        } else if (bul(data, Symbols)) {
            System.out.println(data + " ----------> Symbol");
        } else if (bul(data, Operator)) {
            System.out.println(data + " ----------> Operator");
        } else if (bul(data, JavaKeywords)) {
            System.out.println(data + " ----------> Java Keywords");
        } else if (bul(data, Digits)) {
            System.out.println(data + " ----------> Digit");
        } else {
            System.out.println(data + " ----------> Tanımsız");
        }


    }

    public static void main(String[] args) throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("code.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;

        BufferedReader br = new BufferedReader(fileReader);
        int satir = 1;
        while ((line = br.readLine()) != null) {
            System.out.println("***SATIR" + satir + "***");
            String array[] = line.split(" ");
            Stack stack = new Stack();
            for (int i = array.length - 1; i >= 0; i = i - 1) {
                stack.push(array[i]);
            }

            Kontrol(stack);
            satir++;
            System.out.println("#####################");

        }


        br.close();
    }
}
