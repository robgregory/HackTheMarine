package hackthemarine.coastalwearable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks  {

    private GoogleApiClient mGoogleApiClient;
    private MessageHelper messageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageHelper = new MessageHelper();

        mGoogleApiClient = new GoogleApiClient.Builder( this )
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();

        mGoogleApiClient.connect();

        Wearable.MessageApi.addListener(mGoogleApiClient, new MessageApi.MessageListener() {
            @Override
            public void onMessageReceived(MessageEvent messageEvent) {

                // TODO: Get from webservice
                String message = "[{\"Name\":\"Fleetwood\",\"Quality\":\"Excellent\",\"Easting\":\"333120\",\"Northing\":\"448310\"},{\"Name\":\"Cleveleys\",\"Quality\":\"Poor\",\"Easting\":\"331200\",\"Northing\":\"443300\"},{\"Name\":\"Bispham\",\"Quality\":\"Sufficient\",\"Easting\":\"330700\",\"Northing\":\"439700\"},{\"Name\":\"Blackpool North\",\"Quality\":\"Good\",\"Easting\":\"330534\",\"Northing\":\"436055\"},{\"Name\":\"Blackpool Central\",\"Quality\":\"Sufficient\",\"Easting\":\"330536\",\"Northing\":\"434556\"}]";

                messageHelper.sendMessage(mGoogleApiClient, "Beaches", message);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        messageHelper.resolveNode(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
