import java.util.ArrayList;
import java.util.List;
public class test{
    // public static List<Token> tokens = new ArrayList<Token>();
    public static void main(String[] args){
        int j = 12;
        String js = Integer.toString(j);
    }
    public static boolean check(char c){
        if( (c >= '0' && c <= '9'))
            return true;
        else
            return false;
    }


//         Token a = new Token("first");
//         Token b = new Token("this","works");
//         tokens.add(a);
//         tokens.add(b);
//         for(int i=0;i<tokens.size();i++){
//             tokens.get(i).printToken();
//             System.out.println();
//         }
//     }
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