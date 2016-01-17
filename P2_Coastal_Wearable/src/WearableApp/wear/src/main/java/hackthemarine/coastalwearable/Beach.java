package hackthemarine.coastalwearable;


public class Beach {
    public String Name;

    public String Quality;

    public double Easting;

    public double Northing;

    public int getDistance(double currentEasting, double currentNorthing){

        double dist = Math.sqrt(Math.pow(currentNorthing - Northing, 2) + Math.pow(currentEasting - Easting, 2)) /100;

        if(dist<0)
            dist =  (int)((dist*-1) / 1000);

        return  (int)dist / 1000;
    }
}
