package hackthemarine.coastalwearable;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {

    private TextView mTextView;
    private GoogleApiClient mGoogleApiClient;
    private MessageHelper messageHelper;

    private double easting = 368362;
    private double northing = 073572;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        messageHelper = new MessageHelper();

        //Connect the GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();

        Wearable.MessageApi.addListener(mGoogleApiClient, new MessageApi.MessageListener() {
            @Override
            public void onMessageReceived(MessageEvent messageEvent) {

                try {
                    String json = new String(messageEvent.getData());

                    GsonBuilder gsonb = new GsonBuilder();
                    Gson gson = gsonb.create();

                    JSONArray j = new JSONArray(json);

                    Beach[] beaches = gson.fromJson(j.toString(), Beach[].class);


                    if(beaches.length > 0) {
                        ((TextView) findViewById(R.id.subTree1).findViewById(R.id.beach_name)).setText(beaches[0].Name);
                        ((TextView) findViewById(R.id.subTree1).findViewById(R.id.beach_distance)).setText(beaches[0].getDistance(easting, northing) + "km");
                        findViewById(R.id.subTree1).findViewById(R.id.circle).setBackground(getDrawable(beaches[0].Quality));
                    }

                    if(beaches.length > 1){
                        ((TextView) findViewById(R.id.subTree2).findViewById(R.id.beach_name)).setText(beaches[1].Name);
                        ((TextView) findViewById(R.id.subTree2).findViewById(R.id.beach_distance)).setText(beaches[1].getDistance(easting, northing) +"km");
                        findViewById(R.id.subTree2).findViewById(R.id.circle).setBackground(getDrawable(beaches[1].Quality));
                    }else{
                        findViewById(R.id.subTree2).setVisibility(View.INVISIBLE);
                    }

                    if(beaches.length > 2){
                        ((TextView) findViewById(R.id.subTree3).findViewById(R.id.beach_name)).setText(beaches[2].Name);
                        ((TextView) findViewById(R.id.subTree3).findViewById(R.id.beach_distance)).setText(beaches[2].getDistance(easting, northing) +"km");
                        findViewById(R.id.subTree3).findViewById(R.id.circle).setBackground(getDrawable(beaches[2].Quality));
                    }else{
                        findViewById(R.id.subTree3).setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                ViewStub stub1 = (ViewStub) findViewById(R.id.stub1);
                stub1.inflate();

                ViewStub stub2 = (ViewStub) findViewById(R.id.stub2);
                stub2.inflate();

                ViewStub stub3 = (ViewStub) findViewById(R.id.stub3);
                stub3.inflate();

            }
        });

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    private Drawable getDrawable(String quality) {
        if (quality == "Excellent")
            return getResources().getDrawable(R.drawable.shape_green_circle);

        if (quality == "Poor")
            return getResources().getDrawable(R.drawable.shape_red_circle);

        return getResources().getDrawable(R.drawable.shape_gray_circle);
    }

    private void makeUseOfNewLocation(Location location) {
        //lat = location.getLatitude();
        //lon = location.getLongitude();
    }

    @Override
    public void onConnected(Bundle bundle) {
        messageHelper.resolveNode(mGoogleApiClient, new OnDone() {
            @Override
            public void Done() {
                messageHelper.sendMessage(mGoogleApiClient, "GetBeaches", "");
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}