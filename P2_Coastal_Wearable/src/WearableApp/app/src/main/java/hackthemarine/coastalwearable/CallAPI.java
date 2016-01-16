package hackthemarine.coastalwearable;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class CallAPI extends AsyncTask<String, String, Beach[]> {

    private OnCompleted<Beach[]> onCompleted;

    public CallAPI(OnCompleted<Beach[]> onCompleted){
        this.onCompleted = onCompleted;
    }

    @Override
    protected Beach[] doInBackground(String... params) {

        return new Beach[]{
                new Beach("Fleetwood","Excellent", 333120, 333120),
                new Beach("Cleveleys","Poor", 331200, 443300),
                new Beach("Bispham","Sufficient", 330700, 439700),
                new Beach("Blackpool North","Good", 330534, 436055),
                new Beach("Blackpool Central","Sufficient", 1, 2)
        };

       /* String urlString=params[0];



        try {

            URL url = new URL(urlString);

            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            try
            {
                JSONObject j = new JSONObject(sb.toString());
                return gson.fromJson(j.toString(), Beach[].class);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return null;
        }*/
    }

    protected void onPostExecute(Beach[] result) {
        onCompleted.OnCompleted(result);
    }

}