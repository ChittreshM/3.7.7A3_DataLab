/*
 * Arrays of objects
 */

import core.data.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Welcome03_List {
   public static void main(String[] args) {
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
      ArrayList<WeatherStation> allstns = ds.fetchList("WeatherStation", "station/station_name", 
             "station/station_id", "station/state",
             "station/latitude", "station/longitude");
      System.out.println("Total stations: " + allstns.size());
      
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter a state abbreviation: ");
      String state = sc.next();
      System.out.println("Stations in " + state);

      WeatherStation southernmostStation = null;

      for (WeatherStation ws : allstns) {
         if (ws.isLocatedInState(state)) {
            System.out.println("  " + ws.getId() + ": " + ws.getName());

            // New code to find the southernmost station
            if (southernmostStation == null || ws.getLatitude() < southernmostStation.getLatitude()) {
               southernmostStation = ws;
           }
         }
      }

      if (southernmostStation != null) {
         System.out.println("The southernmost station in " + state + " is " 
                            + southernmostStation.getName() 
                            + " (" + southernmostStation.getId() + ")");
     } else {
         System.out.println("No stations found in " + state);
     }
   }
}