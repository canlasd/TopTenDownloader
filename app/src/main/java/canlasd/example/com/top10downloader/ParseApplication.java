package canlasd.example.com.top10downloader;

import java.util.ArrayList;

/**
 * Created by canlasd on 2016-05-20.
 */
public class ParseApplication {
    private String xmlData;
    private ArrayList<Application> application;

    public ParseApplication(String xmlData) {
        this.xmlData = xmlData;
        // initialize application variable in the constructor
        application = new ArrayList<Application>();
            }

    public ArrayList<Application> getApplication() {
        return application;
    }




}
