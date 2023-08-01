package org.example;

import lombok.SneakyThrows;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;

public class XMLReaderApplication {

    private static final String FILE_PATH = "AS_ADDR_OBJ.XML";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @SneakyThrows
    public static void main(String[] args) {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(FILE_PATH));

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your date: ");
            String dateString = scanner.nextLine();
            Date date = parseDate(dateString);

            System.out.print("Enter OBJECTIDs: ");
            String objectIdsinput = scanner.nextLine().replaceAll("\\s+", "");
            ;
            String[] objectIds = objectIdsinput.split(",");


            NodeList nodeList = document.getElementsByTagName("OBJECT");

            for (String objectId : objectIds) {

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element item = (Element) nodeList.item(i);

                    String objectIdAttr = item.getAttribute("OBJECTID");
                    String startDateAttr = item.getAttribute("STARTDATE");
                    String endDateAttr = item.getAttribute("ENDDATE");

                    Date itemStartDate = parseDate(startDateAttr);
                    Date itemEndDate = parseDate(endDateAttr);

                    if (objectId.equals(objectIdAttr) &&
                            date.before(itemEndDate) &&
                            date.after(itemStartDate)) {

                        String typeName = item.getAttribute("TYPENAME");
                        String name = item.getAttribute("NAME");

                        System.out.println(objectId + ": " + typeName + " " + name);

                    }
                }

            }

    }
    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateString);
    }
}
