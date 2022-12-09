import java.util.*;

public class Search {
    private static HashMap<String, Double> wordIDF =  FileInputAndOutputKit.readWordIDF();
    private static HashMap<String, HashMap<String, Double>> urlWordTF = FileInputAndOutputKit.readUrlWordTF();

    //turn the phrase (string) into a query
    //number of unique words -> number of entries (tfidf) to the words
    //only consider terms that exist in query vector and have idf > 0

    public static ArrayList<String> getSearchQuery(String query) {
        ArrayList<String> searchQuery = new ArrayList<>();
        String[] queryList = query.split(" ");
        HashSet<String> querySet = new HashSet<>(List.of(queryList)); //a set without duplication words
        for (String word : querySet) { //O(n), n = # words in query
            if (wordIDF.containsKey(word)) //O(1)
                searchQuery.add(word);
        }
        return  searchQuery;
    }

    public static ArrayList<Double> getDocVector(String url, ArrayList<String> query) {
        ArrayList<Double> docVector = new ArrayList<>();
        for (String word : query) { //O(n), n = # words in query
            double tf;
            if (!urlWordTF.containsKey(url)) //O(1)
                tf = 0;
            else if (!urlWordTF.get(url).containsKey(word)) {
                tf = 0;
            }
            else
                tf = urlWordTF.get(url).get(word);

            double idf;
            if (!wordIDF.containsKey(word))
                idf = 0;
            else
                idf = wordIDF.get(word);

            double tfidf = Math.log(1 + tf) / Math.log(2) * idf;
            docVector.add(tfidf);
        }
        return  docVector;
    }


    // user's query is also represented as a document (a much shorter document)
    // calculate tf-idf for the doc with each word, add url:tf-idf to the vector
    public static ArrayList<Double> getQueVector(String phrase, ArrayList<String> query) {
        ArrayList<Double> queVector = new ArrayList<>();
        String[] phraseList = phrase.split(" ");

        for (String word : query) {
            double count = 0;
            for (String w : phraseList) {
                if (Objects.equals(w, word))
                    count ++;
            }
            double tf = count/phraseList.length;
            double idf = wordIDF.get(word);
            double tfidf = Math.log(1+tf)/Math.log(2) * idf;
            queVector.add(tfidf);
        }
        return  queVector;
    }
}
