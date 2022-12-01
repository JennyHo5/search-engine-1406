import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Crawler {

    //find the title of the current page
    public String findTitle(String html) {
        int startTitleTagIndex = html.indexOf("<title");
        int endTitleTagIndex = html.indexOf("</title>");
        String titleTag = html.substring(startTitleTagIndex + 6, endTitleTagIndex);
        int startTitleIndex = titleTag.indexOf(">") + 1;
        String title = titleTag.substring(startTitleIndex);
        return title;
    }

    //find all words on the current page
    public HashMap<String, Integer> findWords(String html) {
        HashMap<String, Integer> allWords = new HashMap<>(); //key: specific word, value: appear times
        while (html.contains("<p")) {
            int startPIndex = html.indexOf("<p");
            int endPIndex = html.indexOf("</p>");
            String p = html.substring(startPIndex, endPIndex);
            int startWordsIndex = p.indexOf(">") + 1;
            String words = p.substring(startWordsIndex);
            words = words.trim(); //get rid of whitespaces (spaces, new lines etc.) from the beginning and end of the string
            String[] wordsList = words.split("[\\n\\s]"); //split a string by newline \n and space \s characters
            for (String word : wordsList) {
                if (!allWords.containsKey(word)) {
                    allWords.put(word, 0);
                }
                allWords.put(word, allWords.get(word) + 1); //update the value by +1
            }
            html = html.substring(0,startPIndex) + html.substring(endPIndex + 4);
        }
        return allWords;
    }

    //find all links on the current page
    public HashSet<String> findURLs(String html, String seed) {
        HashSet<String> allURLs = new HashSet<>();
        while (html.contains("<a href=\"")) {
            int startAIndex = html.indexOf("<a href=\"");
            int endAIndex = html.indexOf("</a>");
            String a = html.substring(startAIndex + 9, endAIndex);
            int endQuotationIndex = a.indexOf("\">");
            String URL = a.substring(0, endQuotationIndex);
            String absURL = LinkConverter.convert(URL, seed);
            //add the fullLink to the HashSet
            allURLs.add(absURL);
            html = html.substring(0, startAIndex) + html.substring(endAIndex + 5);
        }
        return allURLs;
    }


}
