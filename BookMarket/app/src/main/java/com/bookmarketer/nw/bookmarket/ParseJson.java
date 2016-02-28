package com.bookmarketer.nw.bookmarket;

/**
 * Created by Rafay on 27/02/2016.
 */

import org.json.*;

public class ParseJson {

    public static BookInfo parse(String jsonStr){
        JSONObject obj;
        try {
            obj = new JSONObject(jsonStr);

            JSONArray arr = obj.getJSONArray("items");

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject json = arr.getJSONObject(i).getJSONObject("volumeInfo");

                String title = json.getString("title");

                JSONArray authorArray = json.getJSONArray("authors");
                String authors = "";
                for (int a = 0; a < authorArray.length(); a++) {
                    authors += authorArray.getString(a) + ",";
                }
                authors = authors.substring(0, authors.lastIndexOf(","));

                String publisher = json.getString("publisher");

                String publishDate = json.getString("publishedDate");

                String desc = json.getString("description");

                String sThumb = json.getJSONObject("imageLinks").getString("smallThumbnail");

                String thumb = json.getJSONObject("imageLinks").getString("thumbnail");

                BookInfo bookinfo = new BookInfo(title, authors, publisher, publishDate, desc, sThumb, thumb);

                return bookinfo;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}