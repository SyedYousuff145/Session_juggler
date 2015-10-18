package com.example.syedyousuff.customrequest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new ButtonListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {

            EditText searchBox = (EditText) findViewById(R.id.editText);
            String url = searchBox.getText().toString();

            new HttpAsyncTask().execute(url);
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            InputStream is = null;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(new HttpGet(urls[0]));

                is = response.getEntity().getContent();

                if (is == null) {
                    result = "Did not work";
                } else {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        result += line;

                    is.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            TextView display = (TextView)findViewById(R.id.textView);
            display.setText(result);

            //WebView myView = (WebView)findViewById(R.id.webView);
            //myView.setWebViewClient(new WebViewClient());
            //WebSettings webSettings = myView.getSettings();
            //webSettings.setJavaScriptEnabled(true);



        }
    }
}
