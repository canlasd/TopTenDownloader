package canlasd.example.com.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String
            URL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws" +
            "/RSS/topfreeapplications/limit=10/xml";


    private Button btnParse;
    private ListView parseListView;
    private String mFileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = (Button) findViewById(R.id.btnParse);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseApplication parseApplication= new ParseApplication(mFileContents);
                parseApplication.Process();

                ArrayAdapter<Application> adapter = new ArrayAdapter<Application>(MainActivity.this,
                        R.layout.list_item,parseApplication.getApplication());

                parseListView.setAdapter(adapter);
            }
        });
        parseListView = (ListView) findViewById(R.id.parseListView);

        DownloadData download = new DownloadData();
        download.execute(URL);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXmlFile(params[0]);

            if (mFileContents == null) {
                Log.d("DownloadData", "Error downloading data");
            }

            return mFileContents;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("DownloadData", "Result: " + result);

        }

        private String downloadXmlFile(String urlFile) {
            StringBuilder stringBuffer = new StringBuilder();
            try {

                URL url = new URL(urlFile);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData", " Response Code: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];

                while (true) {
                    charRead = isr.read(inputBuffer);

                    if (charRead <= 0) {
                        break;
                    }

                    stringBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
                }

                return stringBuffer.toString();


            } catch (IOException e) {
                Log.d("DownloadData", "Error Message " + e.getMessage());
                e.printStackTrace();
            } catch (SecurityException e) {
                Log.d("DownloadData", "Security Exception: " + e.getMessage());
                e.printStackTrace();
            }


            // return null if there is an exception found
            return null;
        }

    }

}
