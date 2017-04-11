package com.kayigwe.letters;

/**
 * Created by kayigwe on 11/14/15.
 */


import com.kayigwe.letters.RssItem;
import java.util.List;
import javax.xml.parsers.SAXParser;

import javax.xml.parsers.SAXParserFactory;


public class RssReader {
    private String rssUrl;

    public RssReader(String rssUrl){
        this.rssUrl = rssUrl;
    }

    public List<RssItem> getItems() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        RssParseHandler handler = new RssParseHandler();

        saxParser.parse(rssUrl, handler);

        return handler.getItem();
    }



}
