package com.currency.scraper.scraping;

import com.currency.scraper.entity.ExchangeRate;
import com.currency.scraper.vo.ExchangeLink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Parser {

    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    private static final URI LINK_TEMPLATE = URI.create("/rss/fxref");

    public List<ExchangeLink> parseExchangeLinks(String payload) {
        Document doc = Jsoup.parse(payload);
        Elements links = doc.select("a");
        return links.stream()
                .filter(link -> link.attr("abs:href").startsWith(LINK_TEMPLATE.toString()))
                .map(exchangeLink -> ExchangeLink.of(exchangeLink.attr("href")))
                .collect(Collectors.toList());
    }

    public List<ExchangeRate> parseExchangeRates(String payload) {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        InputSource source = new InputSource(new StringReader(payload));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        org.w3c.dom.Document doc;
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(source);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            log.debug("Unexpected exception occurred while parsing exchange rates xml");
            return Collections.emptyList();
        }

        doc.getElementsByTagName()
    }
}
