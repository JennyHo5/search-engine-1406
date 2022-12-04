import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TfIdfCalculation {
    static HashSet<Page> crawledPages =  FileInputAndOutputKit.readCrawledPages();
    static ArrayList<String> crawledWords = FileInputAndOutputKit.readCrawledWords();

    //calculate idfs for all words
    //accepts a single string argument representing a word and returns the inverse document frequency of that word
    // within the crawled pages

    public static HashMap<String, Double> calculateIDFs () {
        HashMap<String, Double> wordIDF = new HashMap<>();
        //number of documents w appears in
        for (String w : crawledWords) {
            int appearNum = 0;
            for (Page p : crawledPages) {
                if (p.getAllWords().containsKey(w))
                    appearNum ++;
            }
            double numerator = crawledPages.size();
            double denominator = 1 + appearNum;
            double fraction = numerator / denominator;
            double idf = Math.log(fraction) / Math.log(2);
            wordIDF.put(w, idf);
        }
        return wordIDF;
    }

    //calculate tfs for all words and documents
    // Accepts two string arguments: a URL and a word. This function must return the term frequency of that word within the page with the given URL

    public static HashMap<String, HashMap<String, Double>> calculateTFs() {
        HashMap<String, HashMap<String, Double>> urlWordTF = new HashMap<>();
        for (Page p : crawledPages) {
            HashMap<String, Double> wordTF = new HashMap<>();
            String url = p.getURL();
            HashMap<String, Integer> currAllWords = p.getAllWords();
            for (String w : crawledWords) {
                double tf;
                if (!currAllWords.containsKey(w))
                    tf = 0;
                else {
                    double numerator = currAllWords.get(w);
                    double denominator = 0;
                    for (Map.Entry<String, Integer> entry : currAllWords.entrySet()) {
                        denominator += entry.getValue(); //the number of words no current page
                    }
                    tf = numerator / denominator;
                }
                wordTF.put(w, tf);
            }
            urlWordTF.put(url, wordTF);
        }
        return urlWordTF;
    }
}
