/*
Quinten Bailey
Written 11/8/15
Spell checker using BST
*/
package program5;


import java.io.*;
import java.util.Scanner;

public class Program5 {

    double avgCompsFound;
    double avgCompsNotFound;
    int comparisons = 0;
    int wordsFound;
    int wordsNotFound;
    int comparisonsFound;
    int comparisonsNotFound;
    
     BinarySearchTree[] tree = new BinarySearchTree[26];

    /**pre: none
     * post: none
     * @param args
     */
    public static void main(String[] args) {
        Program5 Test = new Program5();

        Test.readDictionary();
        Test.readAnalyzeFile();
        Test.output();
    }

    /**pre: none
     * post: none
     * Reads dictionary file and adds each word to appropriate BST
     * based off the first letter.
     */
    public void readDictionary() {
        File d = new File("dictionary.txt");
        
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new BinarySearchTree <String>();
        } 
        
        try {
            
           Scanner input = new Scanner(d);
            while (input.hasNext()) {
                String s = input.next();
                s.trim();
                s = s.toLowerCase();
                tree[(int)s.charAt(0) - 97].insert(s);
            }
            input.close();
        } catch (IOException e) {
        }
    }

    /**pre: none
     * post: none
     *reads the target file and compares it against the words in the appropriate
     * BST. 
     */
    public void readAnalyzeFile() {

        try {
            boolean temp;
            
            FileInputStream inf = new FileInputStream(new File("oliver.txt"));
            char let;
            String str = "";
            int n = 0;
            while ((n = inf.read()) != -1) {
                let = (char) n;
                if (Character.isLetter(let)) {
                    str += Character.toLowerCase(let);
                }
                if ((Character.isWhitespace(let) || let == '-') && !str.isEmpty()) {
                    temp = tree[(int)str.charAt(0) - 97].search(str);
                    comparisons = tree[(int)str.charAt(0) - 97].comps(str);
                    if (temp == true) {
                        wordsFound++;
                        comparisonsFound += comparisons;
                    } else {
                        wordsNotFound++;
                        comparisonsNotFound += comparisons;
                    }                   
                    str = "";
                }
            }
            inf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**pre: none
     * post: none
     * outputs data
     */
    public void output() {
        System.out.println("Number of words found: " + wordsFound);
        System.out.println("Number of words not found: " + wordsNotFound);
        System.out.println("---------------------------------------");
        System.out.println("Number of comparisons for words found: " + comparisonsFound);
        System.out.println("Number of comparisons for words not found: " + comparisonsNotFound);
        System.out.println("---------------------------------------");
        System.out.println("Average number of comparisons for words that were found: " + (avgCompsFound = comparisonsFound / wordsFound));
        System.out.println("Average number of comparisons for words that were not found: " + (avgCompsNotFound = comparisonsNotFound / wordsNotFound));
    }
}
/*
Number of words found: 937492
Number of words not found: 54648
---------------------------------------
Number of comparisons for words found: 14388139
Number of comparisons for words not found: 568211
---------------------------------------
Average number of comparisons for words that were found: 15.0
Average number of comparisons for words that were not found: 10.0
*/
