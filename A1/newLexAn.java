import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
public class newLexAn{
    
    public static void main(String args[]){

    }
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

class Node{
    int id;
    
}