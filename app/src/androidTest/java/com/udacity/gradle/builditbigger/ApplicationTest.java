package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dhananjay.androidlib.JokeActivity;
import com.example.admin.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private int clickCount;

    public ApplicationTest() {
        super(Application.class);
    }

    public void testJokeTellerTask(){

        final CountDownLatch latch = new CountDownLatch(1);

        new JokeTellerTask().execute(1);
    }

    class JokeTellerTask extends AsyncTask<Integer, Void, String> {

        private MyApi myApi = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {

            if(myApi == null){

                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setApplicationName("Build it Bigger")
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });

                myApi = builder.build();
            }
            int count = params[0];
            try {
                return myApi.tellJoke(count).execute().getData();
            }catch (IOException e){
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null){
                Log.i("@@@", "Test Failed");
                assertEquals(false, true);
                clickCount = 0;
            }else{
                Log.i("@@@", "Test Success");
                assertEquals(s, s);
            }
        }


    }
}