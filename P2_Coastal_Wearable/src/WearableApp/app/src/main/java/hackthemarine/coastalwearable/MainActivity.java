package hackthemarine.coastalwearable;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private double easting = 368362;
    private double northing = 073572;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                ViewStub stub1 = (ViewStub) findViewById(R.id.stub1);
                final View inflated1 = stub1.inflate();

                ViewStub stub2 = (ViewStub) findViewById(R.id.stub2);
                final View inflated2 = stub2.inflate();

                ViewStub stub3 = (ViewStub) findViewById(R.id.stub3);
                final View inflated3 = stub3.inflate();

                new CallAPI(new OnCompleted<Beach[]>() {
                    @Override
                    public void OnCompleted(Beach[] result) {

                        if(result.length > 0) {
                            ((TextView) inflated1.findViewById(R.id.beach_name)).setText(result[0].Name);
                            ((TextView) inflated1.findViewById(R.id.beach_distance)).setText(result[0].getDistance(easting, northing) + "km");

                            inflated1.findViewById(R.id.circle).setBackground(getDrawable(result[0].Quality));
                        }

                        if(result.length > 1){
                            ((TextView) inflated2.findViewById(R.id.beach_distance)).setText(result[1].Name);
                            ((TextView) inflated2.findViewById(R.id.beach_distance)).setText(result[1].getDistance(easting, northing) +"km");
                            inflated2.findViewById(R.id.circle).setBackground(getDrawable(result[1].Quality));
                        }else{
                            inflated1.setVisibility(View.INVISIBLE);
                        }

                        if(result.length > 2){
                            ((TextView) inflated3.findViewById(R.id.beach_name)).setText(result[2].Name);
                            ((TextView) inflated3.findViewById(R.id.beach_distance)).setText(result[2].getDistance(easting, northing) +"km");
                            inflated3.findViewById(R.id.circle).setBackground(getDrawable(result[2].Quality));
                        }else{
                            inflated3.setVisibility(View.INVISIBLE);
                        }
                    }
                }).execute("http://hackthemarine.azurewebsites.net/get-nearest-bathing-water/347405/442769");
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

    private Drawable getDrawable(String quality){
        if(quality=="Excellent")
            return getResources().getDrawable(R.drawable.shape_green_circle);

        if(quality=="Poor")
            return getResources().getDrawable(R.drawable.shape_red_circle);

        return getResources().getDrawable(R.drawable.shape_gray_circle);
    }

    private void makeUseOfNewLocation(Location location){
        //lat = location.getLatitude();
        //lon = location.getLongitude();
    }
}