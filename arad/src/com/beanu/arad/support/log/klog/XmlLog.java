//package com.beanu.arad.support.log.klog;
//
//import android.util.Log;
//
//
//import com.beanu.arad.support.log.KLog;
//import com.beanu.arad.support.log.Util;
//
//import org.w3c.dom.Document;
//
//import java.io.IOException;
//import java.io.StringReader;
//import java.io.StringWriter;
//
///**
// * Created by zhaokaiqiang on 15/11/18.
// */
//public class XmlLog{
//
//    public static void printXml(String tag, String xml,String headString) {
//
//        if (xml != null) {
//            xml = XmlLog.formatXML(xml);
//            xml = headString + "\n" + xml;
//        } else {
//            xml = headString + KLog.NULL_TIPS;
//        }
//
//        Util.printLine(tag, true);
//        String[] lines = xml.split(KLog.LINE_SEPARATOR);
//        for (String line : lines) {
//            if (!Util.isEmpty(line)) {
//                Log.d(tag, "║ " + line);
//            }
//        }
//        Util.printLine(tag, false);
//    }
//
//    public static String formatXML(String inputXML) {
//        XMLWriter writer = null;
//        String requestXML = null;
//        try {
//            SAXReader reader = new SAXReader();
//            Document document = reader.read(new StringReader(inputXML));
//            StringWriter stringWriter = new StringWriter();
//            OutputFormat format = new OutputFormat(" ", true);
//            writer = new XMLWriter(stringWriter, format);
//            writer.write(document);
//            writer.flush();
//            requestXML = stringWriter.getBuffer().toString();
//        } catch (IOException e) {
//            return inputXML;
//        } catch (DocumentException e) {
//            return inputXML;
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    return inputXML;
//                }
//            }
//        }
//
//        return requestXML;
//    }
//
//}
