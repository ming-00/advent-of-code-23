import java.util.*;

public class Trebuchet {
    public static Map<String, Integer> numberWordToIntegerMap = new HashMap<>() {
        {
            put("one", 1);
            put("two", 2);
            put("three", 3);
            put("four", 4);
            put("five", 5);
            put("six", 6);
            put("seven", 7);
            put("eight", 8);
            put("nine", 9);
        }
    };

    static class TrieNode {
        char c;
        TrieNode[] nextChar = new TrieNode[26];
        boolean isEnd;

        public TrieNode() {
        }
    }

    public static void addWordToTrie(TrieNode root, String str) {
        TrieNode curr = root;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (curr.nextChar[c - 'a'] == null) {
                curr.nextChar[c - 'a'] = new TrieNode();
            }
            curr = curr.nextChar[c - 'a'];
        }
        curr.isEnd = true;
    }

    public static int getDigitFromStringIndex(TrieNode root, String str, int index) {
        if (Character.isDigit(str.charAt(index))) {
            return Character.getNumericValue(str.charAt(index));
        }
        TrieNode curr = root;
        int currIndex = index;
        while (curr != null) {
            if (curr.isEnd) {
                return numberWordToIntegerMap.get(str.substring(index, currIndex));
            }
            if (currIndex == str.length()) {
                return -1;
            }
            char c = str.charAt(currIndex++);
            if (Character.isDigit(c)) {
                return -1;
            }
            curr = curr.nextChar[c - 'a'];
        }

        return -1;
    }

    public static int getNumberFromStringPartOne(String str) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
                break;
            }
        }

        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
                break;
            }
        }

        return Integer.valueOf(sb.toString());
    }

    public static int getNumberFromStringPartTwo(String str) {
        TrieNode root = new TrieNode();

        for (String numberWord : numberWordToIntegerMap.keySet()) {
            addWordToTrie(root, numberWord);
        }

        String res = "";

        for (int i = 0; i < str.length(); i++) {
            int numberAtIndex = getDigitFromStringIndex(root, str, i);
            if (numberAtIndex != -1) {
                res += numberAtIndex;
            }
        }
        if (res.length() > 2) {
            res = res.charAt(0) + "" + res.charAt(res.length() - 1);
        } else if (res.length() == 1) {
            res += res;
        }

        return Integer.valueOf(res);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int partOneSum = 0;
        int partTwoSum = 0;
        while (sc.hasNextLine()) {
            String curr = sc.nextLine();
            partOneSum += getNumberFromStringPartOne(curr);
            partTwoSum += getNumberFromStringPartTwo(curr);
        }
        System.out.println("Answer for part 1 is: " + partOneSum);
        System.out.println("Answer for part 2 is: " + partTwoSum);

        sc.close();
        return;
    }
}
