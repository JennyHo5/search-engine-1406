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

    public static HashSet<String> readCrawledURLs(){
        ObjectInputStream reader1;
        try {
            reader1 = new ObjectInputStream(new FileInputStream("crawled-URLs.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashSet<String> crawledURLs = new HashSet<>();
        try {
            crawledURLs = (HashSet<String>) reader1.readObject();
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
        System.out.println(allWords);
        return allWords;
    }

    public static ArrayList<String> readCrawledWorlds(){
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
        System.out.println(crawledWords);
        return crawledWords;
    }

}
