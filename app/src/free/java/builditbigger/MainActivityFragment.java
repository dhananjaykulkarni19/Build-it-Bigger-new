package builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dhananjay.androidlib.JokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.JokeTellerTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private int clickCount;
    Button btnJoke;
    ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        clickCount = 0;

        btnJoke = (Button) root.findViewById(R.id.btnjoke);
        progressBar = (ProgressBar) root.findViewById(R.id.progressbar);

        btnJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new JokeTellerTask(getActivity(), new JokeTellerTask.JokeListener() {
                    @Override
                    public void onJokeLoaded(String joke) {

                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getActivity(), JokeActivity.class);
                        intent.putExtra("joke", joke);
                        startActivity(intent);
                    }
                }).execute(clickCount);
                clickCount ++;
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

/*    public class JokeTellerTask extends AsyncTask<Integer, Void, String>{

        private MyApi myApi = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
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
            progressBar.setVisibility(View.GONE);
            if(s == null){
                Toast.makeText(getActivity(), "End of Joke List ...!!!", Toast.LENGTH_SHORT).show();
                clickCount = 0;
            }else{
                Intent intent = new Intent(getActivity(), JokeActivity.class);
                intent.putExtra("joke", s);
                startActivity(intent);
            }
        }
    }*/
}
