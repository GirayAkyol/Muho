package io.github.cs102g1j.nav;
/**
 * This is Building class.
 */

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Building implements Parcelable
{
   //constants locations
   public final static Building BUILDING_EE = new Building( 39.872094, 32.750913, "EE" );
   public final static Building BUILDING_EA = new Building( 39.871195, 32.750058, "EA" );
   public final static Building BUILDING_S = new Building( 39.867909, 32.748267, "S" );
   public final static Building BUILDING_B = new Building( 39.868667, 32.748309, "B" );
   public final static Building BUILDING_MA = new Building( 39.867379025065425,
                                                            32.75014751011084,
                                                            "MA"
   );


   //properties
   String nameOfBuilding;
   double latitudeOfBuilding;
   double longitudeOfBuilding;


   //constructors
   public Building( double latitude, double longitude, String name )
   {
      nameOfBuilding = name;
      latitudeOfBuilding = latitude;
      longitudeOfBuilding = longitude;

   }

   public Building( Building building )
   {
      nameOfBuilding = building.getNameOfBuilding();
      latitudeOfBuilding = building.getLatitudeOfBuilding();
      longitudeOfBuilding = building.getLongitudeOfBuilding();

   }


   /**
    * This is the constructor that we recreate/clone our object using the parcels coming from the
    * writeToParcel method. This method will be used in the
    *
    * @param in is the information coming in.
    */
   protected Building( Parcel in )
   {
      nameOfBuilding = in.readString();
      latitudeOfBuilding = in.readDouble();
      longitudeOfBuilding = in.readDouble();
   }

   /*This is the method that we convert the properties of our building class to Strings.
    * Later on we will take these information to recreating object from its "parcels". ( in the Building( Parcel in) constructor)
    */
   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( nameOfBuilding );
      dest.writeDouble( latitudeOfBuilding );
      dest.writeDouble( longitudeOfBuilding );
   }

   @Override
   public int describeContents()
   {
      return 0;
   }

   // Creating a Building object invoking the Building( Parcel in) constructor.
   // Also creates an array of Buildings in case of using a Building array in program.
   public static final Creator<Building> CREATOR = new Creator<Building>()
   {
      @Override
      public Building createFromParcel( Parcel in )
      {
         return new Building( in );
      }

      @Override
      public Building[] newArray( int size )
      {
         return new Building[ size ];
      }
   };

   // Getter-setter methods
   public String getNameOfBuilding()
   {
      return nameOfBuilding;
   }

   public double getLatitudeOfBuilding()
   {
      return latitudeOfBuilding;
   }

   public double getLongitudeOfBuilding()
   {
      return longitudeOfBuilding;
   }

   public double setLatitudeOfBuilding( double latitude )
   {
      latitudeOfBuilding = latitude;
      return latitude;
   }

   public double setLongitudeOfBuilding( double longtitude )
   {
      longitudeOfBuilding = longtitude;
      return longtitude;
   }

   /**
    * This is the method that finds the distance between our currentLocation and the building
    * object.
    *
    * @param currentLocation is the currentLocation of the user.
    *
    * @return returns the distance as an double value.
    */
   private double getDistance( Location currentLocation )
   {
      double theta = longitudeOfBuilding - currentLocation.getLongitude();
      double dist = Math.sin( deg2rad( latitudeOfBuilding ) ) *
                    Math.sin( deg2rad( currentLocation.getLatitude() ) ) + Math.cos( deg2rad(
         latitudeOfBuilding ) ) * Math.cos( deg2rad( currentLocation.getLatitude() ) ) * Math.cos(
         deg2rad( theta ) );
      dist = Math.acos( dist );
      dist = rad2deg( dist );
      dist = dist * 60 * 1.1515;
      return ( dist );
   }

   /**
    * One of the methods that helps to getDistance method Turns the degree input to the radian.
    *
    * @param deg is the deg input.
    *
    * @return returns the radian value as a double.
    */
   private double deg2rad( double deg )
   {
      return ( deg * Math.PI / 180.0 );
   }

   /**
    * One of the methods that helps to getDistance method Turns the radian input to the degree.
    *
    * @param rad is the rad input.
    *
    * @return returns the degree value as a double.
    */
   private double rad2deg( double rad )
   {
      return ( rad * 180.0 / Math.PI );
   }

   /**
    * This is the method that checks whether we are close enough to the Building. Method returns
    * true if the user is closer to the Building than specified distance,otherwise return false.
    *
    * @param meter is the specified distance.
    * @param currentLocation is the current location of the user.
    *
    * @return returns the boolean value
    */
   public boolean isNearer( double meter, Location currentLocation )
   {
      if ( getDistance( currentLocation ) <= meter / 1000 )
      {
         return true;
      }
      else
      {
         return false;
      }
   }


   /**
    * A toString method that returns the name of the building.
    *
    * @return returns the name of the building as a String.
    */
   @Override
   public String toString()
   {
      return nameOfBuilding;
   }


   /**
    * This is the method that return a building object according to the user input in application.
    *
    * @param buildingCode is the input that users enter in the app when creating their schedule
    *
    * @return return the appropriate building, if input is invalid return null.
    */
   public static Building getBuilding( String buildingCode )
   {
      if ( buildingCode.equals( "EE" ) )
      {
         return BUILDING_EE;
      }
      else if ( buildingCode.equals( "EA" ) )
      {
         return BUILDING_EA;
      }
      else if ( buildingCode.equals( "S" ) )
      {
         return BUILDING_S;
      }
      else if ( buildingCode.equals( "B" ) )
      {
         return BUILDING_B;
      }
      else if ( buildingCode.equals( "MA" ) )
      {
         return BUILDING_MA;
      }
      //At this point, we assume user will not under invalid Building name.
      else
      {
         return null;
      }


   }
}