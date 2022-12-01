public class Link {
    private String seed;
    private String link;

    //constructor
    public Link () {
        seed = "";
        link = "";
    }
    public Link (String iSeed, String iLink) {
        seed = iSeed;
        link = iLink;
    }

    //get methods
    public String getSeed() {
        return seed;
    }
    public String getLink() {
        return link;
    }

    //set methods
    public void setSeed(String seed) {
        this.seed = seed;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //return absolute link of the URL
    public String getFullURL () {
        if (link.startsWith("http://"))
            return link;
        int startRelIndex = seed.lastIndexOf("/");
        String root = seed.substring(0, startRelIndex);
        link = root + link.substring(1);
        return link;
    }
}
