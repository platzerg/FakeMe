package com.platzerworld.fakefb.utils.parser;

import java.net.*;

import org.xml.sax.InputSource;
import org.w3c.dom.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Created by platzerworld on 09.08.14.
 */public class GeocodeProcessor {

    private static final String GEOCODE_REQUEST = "https://maps.googleapis.com/maps/api/geocode/xml";
    private static final String TEXT_REQUEST = "https://maps.googleapis.com/maps/api/place/textsearch/xml";
    private static final String PLACES_REQUEST = "https://maps.googleapis.com/maps/api/place/search/xml";
    private static final String PLACES_DETAIL = "https://maps.googleapis.com/maps/api/place/details/xml";
    private static final String GOOGLE_KEY = "AIzaSyB5H8frwYEom9rgEzqjJ169nC5yZKVX1fQ";

    public GeocodeProcessor( int test, String testname, String sta, String county, String cit, String zip ) {

        System.out.println("Processing test " + test);

        /** String search to find latitude and longitude of nearest hotels to state+city+zip */
        String smartQuery = "hotel+near+" + cit + "+" + sta;
        smartQuery = smartQuery.replace( ' ', '+' );
        if ( zip.length() == 5 ) smartQuery = smartQuery + "+" + zip;

        Document geocodeResult = queryByText( GOOGLE_KEY, smartQuery );
        //printXML( geocodeResult );

        //archiveResult( test, testname, sta, county, cit, address, zip, lat, lng );

    }

    public void archiveResult( int test, String tname, String sta, String county, String cit, String add, String zip, String lat, String lng ) {
        System.out.println( "Result: " + test + "," + sta + "," + cit + "," + zip + "," + add + "," + lat + "," + lng + "," + tname );
        PrintWriter out = null;
        try {
            out = new PrintWriter( new FileWriter( "out.csv", true ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        out.println( test + "," + sta + "," + county + "," + cit + "," + add + "," + zip + "," + lat + "," + lng + "," + tname );
        out.close();
    }

    public static Document queryGeocodeByCity( String key, String sta, String cit, String zip ) {

        //if ( sta.equals("null") ) sta = "OR";
        //if ( cit.equals("null") ) cit = "Portland";
        if ( zip.equals("null") ) zip = "";

        String urlString = GEOCODE_REQUEST
                + "?address=" + cit + ",+" + sta + "+" + zip
                + "&sensor=false"
                //+ "&region=us"
                + "&key=" + key;
        System.out.println( urlString );
        Document result = sendQuery( urlString );
        return result;
    }

    public static Document queryByText( String key, String text ) {
        String urlString = TEXT_REQUEST
                + "?query=" + text
                + "&sensor=false"
                + "&key=" + key;
        System.out.println( urlString );
        Document result = sendQuery( urlString );
        return result;
    }

    public static Document queryPlaceByKeyword( String loc, String key, String keyword )
            throws IOException, SAXException, ParserConfigurationException {
        String urlString = PLACES_REQUEST
                + "?location=" + loc
                + "&sensor=false"
                + "&key=" + key
                + "&rankby=distance"
                + "&keyword=" + keyword;
        //System.out.println( urlString );
        Document result = sendQuery( urlString );
        return result;
    }

    public static Document queryPlaceByDetail( String key, String ref )
            throws IOException, SAXException, ParserConfigurationException {
        String urlString = PLACES_DETAIL
                + "?reference=" + ref
                + "&sensor=false"
                + "&key=" + key;
        //System.out.println( urlString );
        Document result = sendQuery( urlString );
        return result;
    }

    @SuppressWarnings("unused")
    private static Document queryPlaceByRadius( String loc, String key, String radius ) {
        String urlString = PLACES_REQUEST
                + "?location=" + loc
                + "&sensor=false"
                + "&key=" + key
                + "&radius=" + radius;
        //System.out.println( urlString );
        Document result = sendQuery( urlString );
        return result;
    }

    private static Document sendQuery( String urlstring ) {
        URL url = null;
        try {
            url = new URL( urlstring );
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document result = null;
        try {
            conn.connect();
            InputSource geocoderResultInputSource
                    = new InputSource( conn.getInputStream() );
            result = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse( geocoderResultInputSource );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return result;
    }

    public static void printXML( Document xmlDoc )  {
        System.out.print("\nGoogle Places Result\n------------------\n");
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult( new StringWriter() );
        DOMSource source = new DOMSource( xmlDoc );
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String xmlString = result.getWriter().toString();
        System.out.println(xmlString);
    }

}
