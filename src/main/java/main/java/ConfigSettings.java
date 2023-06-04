package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class ConfigSettings {

    boolean enabledLoad;
    String loadFile;
    String loadFormat;

    boolean enabledSave;
    String saveFile;
    String saveFormat;

    boolean enabledLog;
    String logFile;

    public ConfigSettings(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        Element root = document.getDocumentElement();
        Element loadSettings = (Element) root.getElementsByTagName("load").item(0);
        Element saveSettings = (Element) root.getElementsByTagName("save").item(0);
        Element logSettings = (Element) root.getElementsByTagName("log").item(0);
        enabledLoad = Boolean.parseBoolean(loadSettings.getElementsByTagName("enabled").item(0).getTextContent());
        loadFile = loadSettings.getElementsByTagName("fileName").item(0).getTextContent();
        loadFormat = loadSettings.getElementsByTagName("format").item(0).getTextContent();

        enabledSave = Boolean.parseBoolean(saveSettings.getElementsByTagName("enabled").item(0).getTextContent());
        saveFile = saveSettings.getElementsByTagName("fileName").item(0).getTextContent();
        saveFormat = saveSettings.getElementsByTagName("format").item(0).getTextContent();

        enabledLog = Boolean.parseBoolean(logSettings.getElementsByTagName("enabled").item(0).getTextContent());
        logFile = logSettings.getElementsByTagName("fileName").item(0).getTextContent();
    }
}