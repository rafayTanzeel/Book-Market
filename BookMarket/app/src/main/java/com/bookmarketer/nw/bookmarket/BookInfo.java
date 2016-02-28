package com.bookmarketer.nw.bookmarket;

/**
 * Created by Rafay on 27/02/2016.
 */
public class BookInfo {

    private String title;
    private String authors;
    private String publisher;
    private String pubDate;
    private int price;
    private String mseller;
    public BookInfo(String ti, String au, String pub, String se, int pr){
        title = ti;
        authors = au;
        publisher = pub;
        mseller = se;
        price = pr;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDesc() {
        return desc;
    }

    public String getsThumb() {
        return sThumb;
    }

    public String getThumb() {
        return thumb;
    }

    private String desc;
    private String sThumb;
    private String thumb;

    public BookInfo(String title,
                    String authors,
                    String publisher,
                    String pubDate,
                    String desc,
                    String sThumb,
                    String thumb) {

        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.desc = desc;
        this.sThumb = sThumb;
        this.thumb = thumb;
    }
}
