package io.github.cs102g1j.nav;
/**
 * This is MyLesson class.
 */

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.TimeZone;

public class MyLesson implements Parcelable
{
   static final int DISTANCE_20 = 20;
   //properties
   private String lectureName;
   private Building lectureBuilding;
   private MyDate lectureTime;
   private boolean isTimeToAppearPokemon;

   //constructors
   public MyLesson( MyDate date, Building building, String lectureName )
   {
      lectureTime = date;
      lectureBuilding = building;
      this.lectureName = lectureName;
      isTimeToAppearPokemon = false;
   }


   protected MyLesson( Parcel in )
   {
      lectureName = in.readString();
      lectureBuilding = in.readParcelable( Building.class.getClassLoader() );
      lectureTime = in.readParcelable( MyDate.class.getClassLoader() );
      isTimeToAppearPokemon = in.readByte() != 0;
   }

   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( lectureName );
      dest.writeParcelable( lectureBuilding, flags );
      dest.writeParcelable( lectureTime, flags );
      dest.writeByte( (byte) ( isTimeToAppearPokemon ? 1 : 0 ) );
   }

   @Override
   public int describeContents()
   {
      return 0;
   }

   public static final Creator<MyLesson> CREATOR = new Creator<MyLesson>()
   {
      @Override
      public MyLesson createFromParcel( Parcel in )
      {
         return new MyLesson( in );
      }

      @Override
      public MyLesson[] newArray( int size )
      {
         return new MyLesson[ size ];
      }
   };

   //methods

   /**
    * Returns the name of the lecture.
    *
    * @return the name of the lecture.
    */
   public String getLectureName()
   {
      return lectureName;
   }

   /**
    * Returns the place of the lecture.
    *
    * @return the place of the lecture.
    */
   public Building getLectureBuilding()
   {
      return lectureBuilding;
   }

   /**
    * Returns the time of the lecture.
    *
    * @return the time of the lecture.
    */
   public MyDate getLectureTime()
   {
      return lectureTime;
   }

   /**
    * Returns whether it is valid for a pokemon in ARActivity.
    *
    * @return true if valid, false otherwise.
    */
   public boolean getIsTimeToAppearPokemon()
   {
      return isTimeToAppearPokemon;
   }

   /**
    * Sets the name of the lecture.
    *
    * @param name name of the lecture.
    */
   public void setLectureName( String name )
   {
      lectureName = name;
   }

   /**
    * Sets the place of the lecture.
    *
    * @param building place of the lecture.
    */
   public void setLectureBuilding( Building building )
   {
      lectureBuilding = building;

   }

   /**
    * Sets the date of the lecture.
    *
    * @param date date of the lecture.
    */
   public void setLectureTime( MyDate date )
   {
      lectureTime = date;
   }

   /**
    * Returns whether the current time and location is valid to launch AR. Also updates the
    * isTimeToAppearPokemon variable.
    *
    * @param currentLocation the current location.
    *
    * @return true if valid, false otherwise.
    */
   public boolean isNow( Location currentLocation )
   {
      // getting the current time
      Calendar calendar = Calendar.getInstance( TimeZone.getDefault() );
      MyDate currentDate = new MyDate( calendar.get( Calendar.DAY_OF_WEEK ),
                                       calendar.get( Calendar.HOUR_OF_DAY ),
                                       calendar.get( Calendar.MINUTE ),
                                       calendar.get( Calendar.MINUTE )
      );


      isTimeToAppearPokemon = lectureBuilding.isNearer( DISTANCE_20, currentLocation ) &&
                              lectureTime.isIncludes( currentDate );

      return lectureBuilding.isNearer( DISTANCE_20, currentLocation ) && lectureTime.isIncludes(
         currentDate );
   }

   /**
    * Returns the name of the lecture.
    * @return name of the lecture.
    */
   public String getLecture()
   {
      return lectureName;
   }

   /**
    * Returns the place of the lecture.
    * @return place of the lecture.
    */
   public String getPlace()
   {
      return lectureBuilding.toString();
   }

   /**
    * Returns the date of the lecture.
    * @return date of the lecture.
    */
   public String getDate()
   {
      return lectureTime.normalize( lectureTime.getStartTime() );
   }

   @Override
   public String toString()
   {
      return lectureName +
             " at " +
             lectureBuilding.toString() +
             "\nbetween " +
             lectureTime.normalize( lectureTime.getStartTime() ) +
             " and " +
             lectureTime.normalize( lectureTime.getEndTime() );
   }
} // END OF the class
