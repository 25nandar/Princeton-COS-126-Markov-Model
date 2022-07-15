/*

When given an input string, the program will take the first n characters
and then use them to predict the following characters, resulting in a
total of T characters.

 */


public class TextGenerator {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        String input = StdIn.readAll();
        String current = input.substring(0, k);

        MarkovModel generator = new MarkovModel(input, k);

        StdOut.print(current);


        for (int i = 0; i < T - k; i++) {

            current = current + generator.random(current);
            current = current.substring(1);
            StdOut.print(current.charAt(k - 1));

        }
    }
}
