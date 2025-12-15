package Strings;
public class CompressDecompressString {
    /**I'll assume we want to avoid repeating characters that are adjacent to each other. e.g.aaaabbb will become 4a3b */

    public static String compress(String s){
        if(s==null || s.length()==0) return "";

        if(s.length()==1) return s;

        int currentCount=1;
    
        StringBuilder sb=new StringBuilder();

        for(int i=1;i<s.length();i++){
            if(s.charAt(i)==s.charAt(i-1)){ //continue the current streak
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

    public static String decompress(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char c = s.charAt(i);

            // Case 1: digit → parse full number
            if (Character.isDigit(c)) {
                int count = 0;

                // build the full number (handles 12, 100, etc.), this is the key, taking account of  double
                //or even tripple digits. I am harping on this because micro code flares here are things you'll find
                //yourself using in other problems.
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    int currentNum=s.charAt(i)-'0'; //convert a char into an int

                    count = count * 10 + currentNum;
                    i++;
                }

                // now s.charAt(i) must be the character to repeat
                char ch = s.charAt(i);

                for (int j = 0; j < count; j++) {
                    sb.append(ch);
                }

                i++; // move past the character
            }
            // Case 2: non-digit → append directly
            else {
                sb.append(c);
                i++;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args){
        String s="aaaaaaaabbbbccaaaassss";
        System.out.println(s +" compressed is: "+compress(s));
        System.out.println("Compression ratio: "+s.length()/compress(s).length());

        String c=compress(s);
        String dc=decompress(c);

        System.out.println("Decompressed: "+dc);
        System.out.println("Correct?: "+s.equals(dc));

    }

}
