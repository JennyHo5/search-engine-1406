import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FileInputAndOutputKit {
    public static HashSet<Page> readCrawledPages() {
        ObjectInputStream reader1;
        try {
            reader1 = new ObjectInputStream(new FileInputStream("crawled-pages.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashSet<Page> crawledPages = new HashSet<>();
        try {
            crawledPages = (HashSet<Page>)reader1.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return crawledPages;
    }

    public static ArrayList<String> readCrawledURLs(){
        ObjectInputStream reader1;
        try {
            reader1 = new ObjectInputStream(new FileInputStream("crawled-URLs.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> crawledURLs = new ArrayList<>();
        try {
            crawledURLs = (ArrayList<String>) reader1.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return crawledURLs;
    }

    public static HashMap<String, Integer> readAllWords(){
        ObjectInputStream reader1;
        try {
            reader1 = new ObjectInputStream(new FileInputStream("all-words.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Integer> allWords = new HashMap<>();
        try {
            allWords = (HashMap<String, Integer>) reader1.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allWords;
    }

    public static ArrayList<String> readCrawledWords(){
        ObjectInputStream reader1;
        try {
            reader1 = new ObjectInputStream(new FileInputStream("crawled-words.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> crawledWords = new ArrayList<>();
        try {
            crawledWords = (ArrayList<String>) reader1.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return crawledWords;
    }

    public static HashMap<String, Double> readPageranks() {
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("pageranks.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Double> pageranks = new HashMap<>();
        try {
            pageranks = (HashMap<String, Double>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return pageranks;
    }

    public static HashMap<String, Double> readWordIDF() {
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
        return wordIDF;
    }

    public static HashMap<String, HashMap<String, Double>> readUrlWordTF(){
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
        return urlWordTF;
    }

}
