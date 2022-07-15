/*

This program uses a symbol table to build a statistical model of a
given input text, storing information about the sequences of characters that
appear in the input text, as well as information about the characters that
follow each of these sequences of characters.

 */

public class MarkovModel {

    // Instance varaibles
    private int order;
    ST<String, Integer> table1;
    ST<String, int[]> table2;

    // creates a Markov model of order k based on the specified text
    public MarkovModel(String text, int k) {
        order = k;

        table1 = new ST<String, Integer>();
        table2 = new ST<String, int[]>();

        String circular = text + text.substring(0, order);

        for (int i = 0; i < text.length(); i++) {
            String part = circular.substring(i, i + order);

            int[] values = new int[128];

            if (table1.contains(part)) {
                table1.put(part, (table1.get(part)) + 1);
            }

            if (!table1.contains(part)) {
                table1.put(part, 1);
            }


            if (table2.contains(part)) {
                values = table2.get(part);
                table2.put(part, values);
            }

            values[circular.charAt(i + order)] = values[circular.charAt(i + order)] + 1;
            table2.put(part, values);

        }
    }

    // returns the order of the model (also known as k)
    public int order() {
        return order;
    }

    // returns a String representation of the model (more info below)
    public String toString() {
        String result = "";
        for (String key : table2.keys()) {
            result += key + ": ";

            // access the letter frequencies by calling st.get(key)
            for (int i = 0; i < 128; i++) {
                if (table2.get(key)[i] > 0) {
                    result += Character.toString((char) i) + " " + table2.get(key)[i] + " ";
                }

            }

            result += "\n";
        }
        return result;
    }

    // returns the # of times 'kgram' appeared in the input text
    public int freq(String kgram) {

        if (kgram.length() != order) {
            throw new IllegalArgumentException("kgram is not of length " + order);
        }

        if (!table1.contains(kgram)) {
            return 0;
        }

        return table1.get(kgram);
    }

    // returns the # of times 'c' followed 'kgram' in the input text
    public int freq(String kgram, char c) {

        if (kgram.length() != order) {
            throw new IllegalArgumentException("kgram is not of length " + order);
        }

        int answer;
        answer = table2.get(kgram)[c];
        return answer;
    }

    // returns a random character, chosen with weight proportional to the
    // number of times each character followed 'kgram' in the input text
    public char random(String kgram) {

        if (kgram.length() != order) {
            throw new IllegalArgumentException("kgram is not of length " + order);
        }

        if (!table1.contains(kgram)) {
            throw new IllegalArgumentException("kgram does not appear in the text");
        }

        int[] data = new int[128];

        for (int i = 0; i < 128; i++) {
            int temp = table2.get(kgram)[i];
            data[i] = temp;
        }

        char index = (char) StdRandom.discrete(data);

        return index;
    }

    // tests all instance methods to make sure they're working as expected
    public static void main(String[] args) {
        String text1 = "banana";
        MarkovModel model1 = new MarkovModel(text1, 2);

        String text2 = "gagggagaggcgagaaa";
        MarkovModel model2 = new MarkovModel(text2, 2);

    }
}
