import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class LexAnPrev{ 
    public static List<String> hold = new ArrayList<String>();
    public static List<Character> stream = new ArrayList<Character>();
    public static List<Token> tokens = new ArrayList<Token>();
    public static List<String> idholder = new ArrayList<String>();
    public static int currentIndex = 0;
    public static String buffer ="";


    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            hold.add(sc.nextLine());
        }
        sc.close();
        for(int i=0; i< hold.size(); i++){
            toCharStream(i);
            for(int j = 0; j<stream.size(); j++){
                // System.out.println("currentIndex before peek "+currentIndex);
                getNextToken(stream.get(j));
                j = currentIndex;
                // System.out.println("currentIndex after peek "+currentIndex);
            }
            stream = new ArrayList<Character>();
            currentIndex = 0;
            // log("here1");
        }
        for(int i=0; i<tokens.size(); i++){
            tokens.get(i).printToken();
        }
    }

    public static void log(String s){
        System.out.println(s);
    }

    public static void toCharStream(int index){
        String temp = hold.get(index);
        for(int i=0; i<temp.length();i++){
            stream.add(temp.charAt(i));
        }
    }
    public static void getNextToken(char c ){
        
        determine(c);
    }

    public static boolean checkAlpha(char c){
        if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return true;
        else
            return false;
    }
    public static boolean checkDigit(char c){
        if( (c >= '0' && c <= '9'))
            return true;
        else
            return false;
    }

    public static boolean checkOper(char c){
        if(!checkAlpha(c) && !checkDigit(c))
            return true;
        else
            return false;
    }

    public static char peeking(){
        char peek;
        if (currentIndex+1 < stream.size()){
            peek = stream.get(currentIndex+1);
            currentIndex += 1;
        }
        else{
            peek = Character.MIN_VALUE;
        }
        return peek;
    }

    public static int idCheckExist(String s){
        for(int i=0; i<idholder.size();i++){
            if (idholder.get(i) == s){
                return i;
            }
        }
        return -1;
    }

    public static void detID(char c){
        Token tempToken;
        buffer = "";
        buffer+=c;
        log("here");
        char temp = peeking();
        while((checkAlpha(temp) || checkDigit(temp)) && (temp!=Character.MIN_VALUE)){
            buffer+=temp;
            temp = peeking();
        }
        int idNum = idCheckExist(buffer);

        //if existing id
        if (idNum != -1){
            tokens.add(tempToken = new Token("ID",Integer.toString(idNum)));
            buffer = "";
            return;
        }
        //id is new 
        else{
            idholder.add(buffer);
            tokens.add(tempToken = new Token("ID", Integer.toString(idholder.size()-1)));
            buffer = "";
            return;
        }

    }

    public static void determine(char c){
        //DONT FORGET TO CLEAN BUFFER
        int counter = 0;
        Token tempToken;
        char temp;
        if (checkAlpha(c)){
            switch(c){
            case 'i':
                // log("here inside checkalpha");
                if(peeking() == 'f'){
                    tokens.add(tempToken = new Token("IF"));
                    return;
                //if
                }
                else{
                    // log("here current");
                    currentIndex -=1;
                    // log("this caused error");
                    detID(c);
                    return;
                }
            case 'f':
                temp = peeking();
                counter+=1;
                if(temp == 'i'){
                    tokens.add(tempToken = new Token("FI"));
                    return;
                }
                else if (temp =='e'){
                    if(peeking() == 'd'){
                        counter +=1;
                        tokens.add(tempToken = new Token("FED"));
                        return;
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
                return;
            //check fi
            case 'd':
                temp = peeking();
                counter+=1;
                if(temp== 'o'){
                    tokens.add(tempToken = new Token("DO"));
                    return;
                }
                else if(temp == 'e'){
                    if(peeking()=='d'){
                        counter+=1;
                        tokens.add(tempToken = new Token("FED"));
                        return;
                    }
                }
                else{
                    currentIndex -=1;
                    detID(c);
                    return;
                }
            //check do
            case '0':
                temp = peeking();
                currentIndex+=1;
                if(temp == 'd'){
                    tokens.add(tempToken = new Token("WHILE"));
                    return;
                }
                else if(temp == 'r'){
                    tokens.add(tempToken = new Token("OR"));
                    return;
                }
                else{
                    currentIndex -=1;
                    detID(c);
                    return;
                }
            //check od || or
            case 'a':
                if(peeking() == 'n'){
                    counter +=1;
                    if(peeking() == 'd'){
                        counter +=1;
                        tokens.add(tempToken = new Token("AND"));
                        return;
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
            // check and 
            case 'n':
                if(peeking() == 'o'){
                    counter +=1;
                    if(peeking() == 't'){
                        counter +=1;
                        tokens.add(tempToken = new Token("NOT"));
                        return;
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
            //check not
            case 'e':
                if(peeking() == 'l'){
                    counter +=1;
                    if(peeking() == 's'){
                        counter +=1;
                        if(peeking() == 'e'){
                            counter +=1;
                            tokens.add(tempToken = new Token("ELSE"));
                            return;
                        }
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
            //check else
            case 't':
                if(peeking() == 'h'){
                    counter +=1;
                    if(peeking() == 'e'){
                        counter +=1;
                        if(peeking() == 'n'){
                            counter +=1;
                            tokens.add(tempToken = new Token("THEN"));
                            return;
                        }
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
            //check then 
            case 'p':
                if(peeking() == 'r'){
                    counter +=1;
                    if(peeking() == 'i'){
                        counter +=1;
                        if(peeking() == 'n'){
                            counter +=1;
                            if(peeking() == 't'){
                                counter +=1;
                                tokens.add(tempToken = new Token("PRINT"));
                                return;
                            }
                        }
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }

            // check print
            case 'r':
                if(peeking() == 'e'){
                    counter +=1;
                    if(peeking() == 't'){
                        counter +=1;
                        if(peeking() == 'u'){
                            counter +=1;
                            if(peeking() == 'r'){
                                counter +=1;
                                if(peeking() == 'n'){
                                    counter +=1;
                                    tokens.add(tempToken = new Token("RETURN"));
                                    return;
                                }
                            }
                        }
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
            // check return
            case 'w':
                if(peeking() == 'h'){
                    counter +=1;
                    if(peeking() == 'i'){
                        counter +=1;
                        if(peeking() == 'l'){
                            counter +=1;
                            if(peeking() == 'e'){
                                counter +=1;
                                tokens.add(tempToken = new Token("WHILE"));
                                return;
                            }
                        }
                    }
                }
                else{
                    currentIndex -=counter;
                    detID(c);
                    return;
                }
            //check while
            }
            
            
            
        }
        // if digit
        else if(checkDigit(c)){
            buffer+=c;
            temp = peeking();
            while(checkDigit(temp) && (temp!=Character.MIN_VALUE)){
                buffer+=temp;
                temp = peeking();
            }
            tokens.add(tempToken = new Token("INTEGER",buffer));
            return;
        }
        
        //operrand and others
        else{
            switch(c){
                case '<':
                    temp = peeking();
                    if(temp == '='){
                        tokens.add(tempToken = new Token("OPERATOR","<="));
                        return;
                    }
                    else if(temp == '>'){
                        tokens.add(tempToken = new Token("OPERATOR","<>"));
                        return;
                    }
                    else{
                        currentIndex -=1;
                        tokens.add(tempToken = new Token("OPERATOR","<"));
                        return;
                    }
                case '>':
                    if(peeking() == '='){
                        tokens.add(tempToken = new Token("OPERATOR",">="));
                        return;
                    }
                    else{
                        currentIndex -=1;
                        tokens.add(tempToken = new Token("OPERATOR",">"));
                        return;
                    }
                case '=':
                    if(peeking() == '='){
                        tokens.add(tempToken = new Token("OPERATOR","=="));
                        return;
                    }
                    else{
                        currentIndex -=1;
                        tokens.add(tempToken = new Token("OPERATOR","="));
                        return;
                    }
                case ',':
                    tokens.add(tempToken = new Token("COMMA"));
                    return;
                case ';':
                    tokens.add(tempToken = new Token("SEMICOLON"));
                    return;
                case '(':
                    tokens.add(tempToken = new Token("OPEN_BRACKET"));
                    return;
                case ')':
                    tokens.add(tempToken = new Token("CLOSED_BRACKET"));
                    return;
            }
        }
    }

    
    

    //world list
    // types = int, double 
    // statements = if, then, fi, else , while, do , od, print, return, def, fed
    // expr = +, -
    // term = *, /, %
    //bexpr = or, and , not
    // comp = <, > , ==, <>
}

class Token{
    String name;
    String content;
    public Token(String a){
        name = a;
        content = null;
    }
    public Token(String a, String b){
        name = a;
        content = b;
    }
    public void printToken(){
        if (content != null){
            System.out.print("<"+name+", '"+content+"'>");
        }else{
            System.out.print("<"+name+">");
        }
    }
}