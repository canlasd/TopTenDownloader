package canlasd.example.com.top10downloader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by canlasd on 2016-05-20.
 */
public class ParseApplication {
    private String xmlData;
    private ArrayList<Application> applications;

    public ParseApplication(String xmlData) {
        this.xmlData = xmlData;
        // initialize application variable in the constructor
        applications = new ArrayList<Application>();
    }

    public ArrayList<Application> getApplication() {
        return applications;
    }

    public boolean Process() {
        boolean status = true;
        boolean inEntry = false;
        Application currentRecord = null;
        String textValue = "";

        try {

            XmlPullParserFactory parseFactory = XmlPullParserFactory.newInstance();
            parseFactory.setNamespaceAware(true);
            XmlPullParser xpp = parseFactory.newPullParser();
            // format downloaded data for parsing
            xpp.setInput(new StringReader(this.xmlData));

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (eventType) {


                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("entry")) {
                            //Log.d("Parse Application: ", "Starting Tag: " + tagName);
                            inEntry = true;
                            currentRecord = new Application();


                        }

                        break;




                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;


                    case XmlPullParser.END_TAG:
                        //Log.d("Parse Application: ", "Ending Tag: " + tagName);
                        if(inEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                applications.add(currentRecord);
                                inEntry = false;
                            } else if (tagName.equalsIgnoreCase("name")) {
                                currentRecord.setName(textValue);
                            } else if (tagName.equalsIgnoreCase("artist")) {
                                currentRecord.setArtist(textValue);
                            } else if (tagName.equalsIgnoreCase("releaseDate")) {
                                currentRecord.setReleaseDate(textValue);
                            }
                        }
                        break;

                    default:  //nothing to do
                }

                eventType = xpp.next();
            }



        } catch (Exception e) {
            status = false;
            e.printStackTrace();

        }


        return true;

    }


}
