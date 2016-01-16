package hackthemarine.coastalwearable;


public class Beach {

    public Beach(String name, String quality, double lon, double lat){
        Name = name;
        Quality = quality;
        Lon = lon;
        Lat = lat;
    }

    public String Name;

    public String Quality;

    public double Lon;

    public double Lat;

    public int getDistance(double currentLon, double currentLat){
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(Lat-currentLat);
        double dLng = Math.toRadians(Lon-currentLon);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(Lat)) * Math.cos(Math.toRadians(Lon)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        if(dist<0)
            dist =  (int)((dist*-1) / 1000);

        return  (int)dist / 1000;
    }

}
