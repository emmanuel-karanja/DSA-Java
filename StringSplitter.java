import java.util.regex.Pattern;
/**split() = modern, regex-powered array-based splitting.
StringTokenizer = old-school, lightweight, iterative splitting without regex. */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringSplitter {

    /**
     * Splits a string by multiple delimiters and whitespace.
     *
     * @param input       The string to split
     * @param delimiters  String containing all delimiter characters (e.g., ",;|")
     * @param ignoreEmpty If true, empty tokens are removed
     * @return Array of cleaned tokens
     */
    public static String[] splitString(String input, String delimiters, boolean ignoreEmpty) {
        if (input == null || input.isEmpty()) {
            return new String[0];
        }

        // Build regex: escape delimiters, include whitespace, + to collapse consecutive delimiters
        String regex = "[" + Pattern.quote(delimiters) + "\\s]+";

        // Split
        String[] rawParts = input.split(regex);

        if (!ignoreEmpty) {
            // Trim and return as-is
            for (int i = 0; i < rawParts.length; i++) {
                rawParts[i] = rawParts[i].trim();
            }
            return rawParts;
        }

        // If ignoring empty tokens, collect into a list
        List<String> tokens = new ArrayList<>();
        for (String part : rawParts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                tokens.add(trimmed);
            }
        }

        return tokens.toArray(new String[0]);
    }

    // Demo
    public static void main(String[] args) {
        String input = "apple,  banana ;; cherry |   date  orange,,";
        String delimiters = ",;|";

        String[] tokens = splitString(input, delimiters, true);

        for (String token : tokens) {
            System.out.println(token);
        }
    }
}

