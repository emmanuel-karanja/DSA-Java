public class BitCountExample {
    public static void main(String[] args) {
        int number = 6; 
        int count = Integer.bitCount(number);
        
        System.out.println("Number: " + number);
        System.out.println("Binary: " + Integer.toBinaryString(number));
        System.out.println("Number of set bits: " + count); 
        // Output: 4

        for(int mask=0;mask < (1<<4); mask++){
            System.out.println("mask:"+mask+", binary:"+Integer.toBinaryString(mask));
        }
    }
}