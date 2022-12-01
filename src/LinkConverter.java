//to convert a relative URL to a absolute URL
public class LinkConverter {
    public static String convert(String orgURL, String seed) {
        if (orgURL.startsWith("http://"))
            return orgURL;
        int startRelIndex = seed.lastIndexOf("/");
        String root = seed.substring(0, startRelIndex);
        String absURL = root + orgURL.substring(1);
        return absURL;
    }
}
