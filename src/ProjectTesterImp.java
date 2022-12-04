import java.io.*;
import java.util.*;

public class ProjectTesterImp implements ProjectTester{
    /*
    This method must delete any existing data that has been stored from any previous crawl.
    This method should also perform any other initialization needed by your system.
    This method will be always called before executing the crawl for a new dataset
     */
    @Override
    public void initialize() {

    }

    //perform a web crawl starting at seed URL
    @Override
    public void crawl(String seedURL){
        Crawler c = new Crawler(seedURL);
        c.doTheCrawl();
        try {
            c.saveCrawledPages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveCrawledURLs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveAllWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveCrawledWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.savePageranks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveIDFs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveTFs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        return FindElementsKit.getOutgoingLinks(url);
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        return FindElementsKit.getIncomingLinks(url);
    }

    @Override
    public double getPageRank(String url){
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("pageranks.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Double> pageranks = null;
        try {
            pageranks = (HashMap<String, Double>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!pageranks.containsKey(url))
            return -1.0;
        return pageranks.get(url);
    }

    @Override
    public double getIDF(String word) {
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("word-idf.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Double> wordIDF = null;
        try {
            wordIDF = (HashMap<String, Double>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!wordIDF.containsKey(word))
            return 0;
        return wordIDF.get(word);
    }

    @Override
    public double getTF(String url, String word) {
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("url-word-tf.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, HashMap<String, Double>> urlWordTF = new HashMap<>();
        try {
            urlWordTF = (HashMap<String, HashMap<String, Double>>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!urlWordTF.containsKey(url))
            return 0;
        if (!urlWordTF.get(url).containsKey(word))
            return 0;
        return urlWordTF.get(url).get(word);
    }

    @Override
    public double getTFIDF(String url, String word) {
        double tf = getTF(url, word);
        double idf = getIDF(word);
        double tfidf = Math.log(1 + tf) / Math.log(2) * idf;
        return tfidf;
    }

    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {
        return null;
    }
}
