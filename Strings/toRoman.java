
import java.util.LinkedHashMap;
import java.util.Map;

/**Convert a number to roman numerals. 
 * 
 * INTUITION:
 * 
 * Order matters.So create a map to map from the largest to the smallest.
 * And then iterate over the map and compare num >=value if it does, add the numeral and decrement the
 * value of num by value. 
*/
public class toRoman {

    public static String convert(int num){
        if(num<=0) {
            throw new IllegalArgumentException("Number is zero or negative.");
        }

        if(num >=4000){
            throw new IllegalArgumentException("Number is greater than 4000.");
        }

        //mapping
        Map<Integer,String> map = new LinkedHashMap<>();  //use a linked hashmap for to keeps order

        map.put(1000,"M");
        map.put(900,"CM");
        map.put(500,"D");
        map.put(400,"CD");
        map.put(100,"C");
        map.put(90,"XC");
        map.put(50,"L");
        map.put(40,"XL");
        map.put(10,"X");
        map.put(9,"IX");
        map.put(5,"V");
        map.put(4,"IV");
        map.put(1,"I");

        StringBuilder sb=new StringBuilder();

        for(Map.Entry<Integer,String> entry: map.entrySet()){
            //get a key from the map to find the starting for the numeral
            String numeral=entry.getValue();
            int value=entry.getKey();

            while(num>=value){
                sb.append(numeral);
                num-=value;
            }
        }
        
        return sb.toString();
    }

    public static void main(String[] args){
        int num=18;

        System.out.println(convert(num));
    }
    
}
