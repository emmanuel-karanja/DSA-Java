package Strings;

public class CompressDecompressString {

    /** 
     * Compress a string into the format: char followed by count of consecutive repeats
     * e.g., "aaaabbbcc" -> "a4b3c2"
     */
    public static String compress(String s) {
        if (s == null || s.length() == 0) return "";
        if (s.length() == 1) return s + "1"; // single char → count 1

        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++; // continue streak
            } else {
                // append char first, then count
                sb.append(s.charAt(i - 1)).append(count);
                count = 1; // reset count for new char
            }
        }

        // append last character + count
        sb.append(s.charAt(s.length() - 1)).append(count);

        return sb.toString();
    }

    /**
     * Decompress a string in the format: char followed by count
     * e.g., "a4b3c2" -> "aaaabbbcc"
     */
    public static String decompress(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            // Get char first
            char ch = s.charAt(i);
            i++;

            // parse the number after the char
            int count = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                count = count * 10 + (s.charAt(i) - '0');
                i++;
            }

            // append char count times
            for (int j = 0; j < count; j++) {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "aaaaaaaabbbbccaaaassss";

        String compressed = compress(s);
        System.out.println("Original:   " + s);
        System.out.println("Compressed: " + compressed);
        System.out.println("Compression ratio: " + ((double)s.length() / compressed.length()));

        String decompressed = decompress(compressed);
        System.out.println("Decompressed: " + decompressed);
        System.out.println("Correct? " + s.equals(decompressed));
    }
}
