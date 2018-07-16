package com.currency.scraper.scraping;

import com.currency.scraper.exception.ScraperException;
import com.currency.scraper.vo.ExchangeLink;
import com.currency.scraper.vo.ParsedExchangeRate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
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

    private NodeList parseItems(String payload) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        InputSource source = new InputSource(new StringReader(payload));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document doc = db.parse(source);
        XPathExpression expr = xPath.compile("//item");
        return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    }

    private List<ParsedExchangeRate> mapItemToExchangeRate(NodeList nodes) {
        List<ParsedExchangeRate> result = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            NamedNodeMap attr = nodes.item(i).getAttributes();
            result.add(
                    ParsedExchangeRate.of(
                            attr.getNamedItem("cb:value").getNodeValue(),
                            attr.getNamedItem("cb:baseCurrency").getNodeValue(),
                            attr.getNamedItem("cb:targetCurrency").getNodeValue()
                    )
            );
        }

        return result;
    }

    public List<ParsedExchangeRate> parseExchangeRates(String payload) {
        try {
            NodeList items = parseItems(payload);
            return mapItemToExchangeRate(items);
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            throw new ScraperException(e.getMessage());
        }
    }
}
