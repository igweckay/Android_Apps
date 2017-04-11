package com.kayigwe.letters;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kayigwe on 11/14/15.
 */
public class RssParseHandler extends DefaultHandler{

    private List<RssItem> rssItem;

    //used to reference item while parsikng
    private RssItem currentItem;

    //parsing title indicator
    private boolean parsingTitle;
    //parsing link indicator
    private boolean parsingLink;

    public RssParseHandler(){
        rssItem = new ArrayList<RssItem>();
    }

    //access methos
    public List<RssItem> getItem(){
        return rssItem;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       // super.startElement(uri, localName, qName, attributes);

        if ("item".equals(qName)){
            currentItem = new RssItem();
        } else if ("title".equals(qName)){
            parsingTitle = true;
        } else if ("link".equals(qName)){
            parsingLink = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //super.endElement(uri, localName, qName);

        if ("item".equals(qName)){
            rssItem.add(currentItem);
            currentItem = new RssItem();
        } else if ("title".equals(qName)){
            parsingTitle = true;
        } else if ("link".equals(qName)){
            parsingLink = true;
        }

    }

    //content is being processed
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
       // super.characters(ch, start, length);
        if (parsingTitle){
            if (currentItem != null)
                currentItem.setTitle(new String(ch, start, length));
        } else if (parsingLink){
            if (currentItem != null){
                currentItem.setLink(new String(ch, start, length));
                parsingLink = false;
            }
        }
    }
}
