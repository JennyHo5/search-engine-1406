import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FileInputAndOutput {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(crawledURLs);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(crawledWords);
        return crawledWords;
    }

}
