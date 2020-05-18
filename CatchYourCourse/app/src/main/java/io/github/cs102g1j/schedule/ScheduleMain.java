/**
*This is the ScheduleMain class.
*@author Muhammed Can Küçükaslan
*@author Melis Atun
*@author Deniz Özay
*/
package io.github.cs102g1j.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import io.github.cs102g1j.R;
import io.github.cs102g1j.nav.MyLessons;

public class ScheduleMain extends Fragment
{
   private MyLessons myLessons;
   private RecyclerView recyclerView;
   private MyListAdapter adapter;

   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
                           )
   {
      // Inflate the layout for this fragment
      return inflater.inflate( R.layout.schedule_main, container, false );
   }

   public void onViewCreated( @NonNull View view, Bundle savedInstanceState )
   {
      super.onViewCreated( view, savedInstanceState );
      // initiate MyLesson
      myLessons = new MyLessons();
      // add test values
      myLessons.addTest();

      // RecyclerView setup
      recyclerView = view.findViewById( R.id.recyclerView );

      adapter = new MyListAdapter( myLessons );

      //      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager( getContext()) );
      recyclerView.setAdapter( adapter);

      // Add FAB "+" button which navigates to scheduleAdd fragment
      FloatingActionButton fab = view.findViewById( R.id.fab );
      fab.setOnClickListener( new View.OnClickListener()
      {
         @Override
         public void onClick( View view )
         {
            NavHostFragment.findNavController( ScheduleMain.this )
                           .navigate( R.id.action_scheduleMain_to_scheduleAdd );
            /*
            //System.out.println( "\n\nline 49 fab clicked\n\n");
            Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                    .setAction( "Action", null ).show();
            //navController.navigate( R.id.action_scheduleMain_to_scheduleAdd );
            */
         }
      } );

      //no more needed, to be totally deleted soon
      /*
      view.findViewById( R.id.button_first ).setOnClickListener( new View.OnClickListener()
      {
         @Override
         public void onClick( View view )
         {
            NavHostFragment.findNavController( ScheduleMain.this )
                           .navigate( R.id.action_scheduleMain_to_scheduleAdd );
         }
      } );

       */
   }// this is end of METHOD onViewCreated(), I assure myself.
} // this is end of the CLASS, for sure
