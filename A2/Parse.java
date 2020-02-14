import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
public class Parse{ 
    // public static List<String> hold = new ArrayList<String>();
    // public static List<ccharac> stream = new ArrayList<ccharac>();
    public static List<Token> tokens = new ArrayList<Token>();
    public static List<String> idholder = new ArrayList<String>();
    public static String buffer ="";
    public static char ccharac;
    public static boolean flag = true;
    public static List<String> html = new ArrayList<String>();
    public static List<Character> codes = new ArrayList<Character>();
    public static void main(String args[]){
        startSetup();
        try{
            ccharac = (char)System.in.read();
            codes.add(ccharac);
            while(ccharac!=(char)-1){
                // System.out.println(a);
                getNextToken();
                getNextLexm();
            }
        }
        catch (IOException e){
            System.out.println("Error reading from user");
        }
        // //printing html
        // for(int i=0; i<html.size();i++){
        //     System.out.print(html.get(i));
        // }
        log("\n");
        log("\n");
        log("\n");
        
        
        //print tokens
        System.out.println("<!--");
        for(int i=0; i<tokens.size();i++){
            tokens.get(i).printToken();
            log(" ");
        }
        log("\n");
        System.out.println("-->");

        for(int i=0; i < codes.size(); i++){
            System.out.print(codes.get(i));
        }
    }
    
    public static void log(String s){
        System.out.print(s);
    }
    public static void addHTML(String name, String buffer){
        String thtml ="<span style=\"color:";
        switch(name){
            case"TERMINATE":
                thtml=thtml+"black\">" +"." + "</span>";
                break;
            case"INTEGER":
                thtml=thtml+"green\">"+buffer+" </span>";
                break;
            case"DOUBLE":
                //just word
                if(buffer.equals(""))
                    thtml=thtml+"greenyellow\">"+"double"+" </span>";
                //with number
                else
                    thtml=thtml+"purple\">"+buffer+" </span>";
                break;
            case"ERROR":
                thtml=thtml+"red\">"+buffer+" </span>";
                break;
            case"COMMA":
                thtml=thtml+"blue\">"+","+" </span>";
                break;
            case"SEMICOLON":
                thtml=thtml+"blue\">"+";"+" </span>";
                break;
            case"OPEN_BRACKET":
                thtml=thtml+"blue\">"+"("+" </span>";
                break;
            case"CLOSED_BRACKET":
                thtml=thtml+"blue\">"+")"+" </span>";
                break;
            case"OPERATOR":
            thtml=thtml+"orange\">"+buffer+" </span>";
                break;
            case"ID":
                thtml=thtml+"darkcyan\">"+buffer+" </span>";
                break;
            case"INT":
                thtml=thtml+"purple\">"+"int"+" </span>";
                break;
            case"IF": 
                thtml=thtml+"grey\">"+"if"+"</span>";
                break;
            case"FI":
                thtml=thtml+"grey\">"+"fi"+" </span>";
                break;
            case"THEN":
                thtml=thtml+"fuchsia\">"+"then"+" </span>";
                break;
            case"ELSE":
                thtml=thtml+"grey\">"+"else"+" </span>";
                break;
            case"WHILE":
                thtml=thtml+"brown\">"+"while"+"</span>";
                break;
            case"DO":
                thtml=thtml+"brown\">"+"do"+" </span>";
                break;
            case"OD":
                thtml=thtml+"brown\">"+"od"+" </span>";
                break;
            case"PRINT":
                thtml=thtml+"darkblue\">"+"print"+" </span>";
                break;
            case"RETURN":
                thtml=thtml+"darkblue\">"+"return"+" </span>";
                break;
            case"DEF":
                thtml=thtml+"aqua\">"+"def"+" </span>";
                break;
            case"FED":
                thtml=thtml+"aqua\">"+"fed"+" </span>";
                break;
            case"OR":
                thtml=thtml+"chocolate\">"+"or"+" </span>";
                break;
            case"AND":
                thtml=thtml+"chocolate\">"+"and"+"</span>";
                break;
            case"NOT":
                thtml=thtml+"chocolate\">"+"not"+" </span>";
                break;
        }       
        html.add(thtml);
        return;
    }

    public static void rmHTML(){
        html.remove(html.size()-1);
    }

    public static void getNextLexm(){
        try{
            ccharac = (char)System.in.read();
            codes.add(ccharac);
            // System.out.println("current val "+ccharac);
        }
        catch (IOException e){
            System.out.println("Error reading from user");
        }
        return;
    }
    public static void getNextToken(){

        // System.out.println("here we are");
        buffer="";
        if(ccharac==' '){
            html.add(" ");
            return;
        }
        else if(ccharac=='\t'){
            html.add("&nbsp;&nbsp;&nbsp;&nbsp;");
            return;
        }
        else if(ccharac=='\n'){
            html.add("\n");
            html.add("</br>");
            html.add("\n");
            return;
        }
        else if(ccharac == '.'){
            tokens.add(new Token("TERMINATE"));
            addHTML("TERMINATE",".");
        }
        else{
            determine();
            return;
        }
        // }
    }
    public static boolean checkAlpha(){
        if( (ccharac >= 'a' && ccharac <= 'z') || (ccharac >= 'A' && ccharac <= 'Z'))
            return true;
        else
            return false;
    }
    public static boolean checkDigit(){
        if( (ccharac >= '0' && ccharac <= '9'))
            return true;
        else
            return false;
    }
    public static boolean checkOper(){
        if(!checkAlpha() && !checkDigit())
            return true;
        else
            return false;
    }

    public static void determine(){
        if(checkAlpha()){
            while(checkAlpha()||checkDigit()){
                buffer+=ccharac;
                getNextLexm();
            }
            detectChars();
            getNextToken();
            return;
        }

        //TODO DOUBLE RECOGNITION


        else if(checkDigit()){
            while(checkDigit()){
                buffer+=ccharac;
                getNextLexm();
            }
            if(ccharac!='.'){
                tokens.add(new Token("INTEGER",buffer));
                addHTML("INTEGER", buffer);
                getNextToken();
                return;
            }
            else if(ccharac=='.'){
                char t = ccharac;
                getNextLexm();
                if(checkDigit()){
                    buffer+=t;
                    while(checkDigit()){
                        buffer+=ccharac;
                        getNextLexm();
                    }
                    //has E 
                    if(ccharac=='e' || ccharac =='E'){
                        buffer+=ccharac;
                        getNextLexm();
                        if(ccharac=='-'){
                            buffer+=ccharac;
                            getNextLexm();
                            if(checkDigit()){
                                while(checkDigit()){
                                    buffer+=ccharac;
                                    getNextLexm();
                                }
                                tokens.add(new Token("DOUBLE",buffer));
                                addHTML("DOUBLE",buffer);
                                getNextToken();
                                return;
                            }
                            else{
                                tokens.add(new Token("ERROR"));
                                addHTML("ERROR", buffer);}

                        }
                        else{
                            tokens.add(new Token("ERROR"));
                            addHTML("ERROR", buffer);
                        }

                    }
                    tokens.add(new Token("DOUBLE",buffer));
                    addHTML("DOUBLE",buffer);
                    getNextToken();
                    return;
                }
                else{
                    tokens.add(new Token("INTEGER",buffer));
                    addHTML("INTEGER", buffer);
                    tokens.add(new Token("TERMINAL"));
                    addHTML("TERMINAL",buffer);
                    getNextToken();
                    return;
                }
            }
        }
        else{
            // System.out.println("here we are");
            switch(ccharac){
                case ',':
                    tokens.add(new Token("COMMA"));
                    addHTML("COMMA", buffer);
                    return;
                case ';':
                    tokens.add(new Token("SEMICOLON"));
                    addHTML("SEMICOLON", buffer);
                    return;
                case '(':
                    tokens.add(new Token("OPEN_BRACKET","("));
                    addHTML("OPEN_BRACKET", buffer);
                    return;
                case ')':
                    tokens.add(new Token("CLOSED_BRACKET",")"));
                    addHTML("CLOSED_BRACKET",  buffer);
                    return;
                case '<':
                    tokens.add(new Token("OPERATOR", "<"));
                    addHTML("OPERATOR",  "<");
                    return;
                case '=':
                    if(tokens.size()>0){
                        String prevTok = tokens.get(tokens.size()-1).content;
                        if (prevTok.equals("<")){
                            tokens.remove(tokens.size()-1);
                            rmHTML();
                            tokens.add(new Token("OPERATOR","<="));
                            addHTML("OPERATOR",  "<=");
                            return;
                        }
                        else if (prevTok.equals("=")){
                            tokens.remove(tokens.size()-1);
                            rmHTML();
                            tokens.add(new Token("OPERATOR","=="));
                            addHTML("OPERATOR", "==");
                            return;
                        }
                        else if (prevTok.equals(">")){
                            tokens.remove(tokens.size()-1);
                            rmHTML();
                            tokens.add(new Token("OPERATOR",">="));
                            addHTML("OPERATOR", ">=");
                            return;
                        }
                    }
                    tokens.add(new Token("OPERATOR","="));
                    addHTML("OPERATOR", "=");

                    return;
                case '>':
                    if(tokens.size()>0){
                        String prevTok = tokens.get(tokens.size()-1).content;
                        if (prevTok.equals("<")){
                            tokens.remove(tokens.size()-1);
                            rmHTML();
                            tokens.add(new Token("OPERATOR","<>"));
                            addHTML("OPERATOR", "<>");
                            return;
                        }
                    }
                    tokens.add(new Token("OPERATOR", ">"));
                    addHTML("OPERATOR", ">");
                    return;
                case '+':
                    tokens.add(new Token("OPERATOR", "+"));
                    addHTML("OPERATOR", "+");
                    return;
                case '-':
                    tokens.add(new Token("OPERATOR", "-"));
                    addHTML("OPERATOR", "-");
                    return;
                case '*':
                    tokens.add(new Token("OPERATOR", "*"));
                    addHTML("OPERATOR", "*");
                    return;
                case '/':
                    tokens.add(new Token("OPERATOR", "/"));
                    addHTML("OPERATOR", "/");
                    return;
                case '%':
                    tokens.add(new Token("OPERATOR", "%"));
                    addHTML("OPERATOR", "%");
                    return; 
            }
        }
    }
    public static void detectChars(){
        int ideNum = idCheckExist(buffer);
        //if exist
        if(ideNum!=-1){
            if(ideNum<16){
                tokens.add(new Token(buffer.toUpperCase()));
                addHTML(buffer.toUpperCase(), buffer);
            }
            else{
                tokens.add(new Token("ID",Integer.toString(ideNum)));
                addHTML("ID", buffer);
            }
            buffer="";
            return;
        }
        //dne
        else{
            tokens.add(new Token("ID",Integer.toString(idholder.size())));
            addHTML("ID", buffer);
            idholder.add(buffer);
            buffer="";
            return;
        }
    }

    public static int idCheckExist(String s){
        if(idholder.contains(s)){
            return idholder.indexOf(s);
        }
        return -1;
    }
    public static void startSetup(){
        String[] startids = {"int", "double", "if", "fi", "then","else","while","do","od","print","return","def","fed","or","and","not"};
        for(int i=0; i<startids.length;i++){
            idholder.add(startids[i]);
        }
    }
}

class Token{
    String name;
    String content;
    public Token(String a){
        name = a;
        content = null;
        // System.out.println("token created");
        
    }
    public Token(String a, String b){
        name = a;
        content = b;
        // System.out.println("token created");
    }
    public void printToken(){
        if (content != null){
            System.out.print("<"+name+", '"+content+"'>");
        }else{
            System.out.print("<"+name+">");
        }
    }
}
