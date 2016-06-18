package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class JokeTellerTask extends AsyncTask<Integer, Void, String> {

    private MyApi myApi = null;
    private Context context;
    private JokeListener listener;

    public JokeTellerTask(Context context, JokeListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);
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
        //progressBar.setVisibility(View.GONE);
        if(s == null){
            Toast.makeText(context, "End of Joke List ...!!!", Toast.LENGTH_SHORT).show();
            //clickCount = 0;
        }else{

            listener.onJokeLoaded(s);
            /*Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra("joke", s);
            context.startActivity(intent);*/
        }
    }

    public interface JokeListener{
        public void onJokeLoaded(String joke);
    }
}
