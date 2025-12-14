package Strings;
public class CompressString {
    /**I'll assume we want to avoid repeating characters that are adjacent to each other. e.g.aaaabbb will become 4a3b */

    public static String compress(String s){
        if(s==null || s.length()==0) return "";

        if(s.length()==1) return s;

        int currentCount=1;
    
        StringBuilder sb=new StringBuilder();

        for(int i=1;i<s.length();i++){
            if(s.charAt(i)==s.charAt(i-1)){
                 currentCount++;
            }else{
               //we start a new streak
                sb.append(currentCount).append(s.charAt(i-1));
                //reset count for next
                currentCount=1;
            }
        }

        //append the last character
        sb.append(currentCount).append(s.charAt(s.length() - 1));

        return sb.toString();
    }

    public static void main(String[] args){
        String s="aaaaaaaabbbbccaaaassss";
        System.out.println(s +" compressed is: "+compress(s));

    }

}
