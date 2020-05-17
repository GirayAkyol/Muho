package io.github.cs102g1j.ar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import io.github.cs102g1j.R;

public class ARActivity extends AppCompatActivity
{
   private ArFragment arFragment;
   private ModelRenderable andyRenderable;

   @Override
   @SuppressWarnings( { "AndroidApiChecker", "FutureReturnValueIgnored" } )
   // CompletableFuture requires api level 24
   // FutureReturnValueIgnored is not valid
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );


      setContentView( R.layout.activity_ar );
      arFragment = (ArFragment) getSupportFragmentManager().findFragmentById( R.id.ux_fragment );

      // When you build a Renderable, Sceneform loads its resources in the background while
      // returning
      // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
      ModelRenderable.builder()
                     .setSource( this, R.raw.andy )
                     .build()
                     .thenAccept( renderable ->
                                     andyRenderable
                                        = renderable )
                     .exceptionally( throwable ->
                                     {
                                        Toast toast = Toast.makeText( this,
                                                                      "Unable to load andy " +
                                                                      "renderable",
                                                                      Toast.LENGTH_LONG
                                                                    );
                                        toast.setGravity( Gravity.CENTER, 0, 0 );
                                        toast.show();
                                        return null;
                                     } );

      arFragment.setOnTapArPlaneListener( ( HitResult hitResult, Plane plane,
                                            MotionEvent motionEvent ) ->
                                          {
                                             if ( andyRenderable == null )
                                             {
                                                return;
                                             }

                                             // Create the Anchor.
                                             Anchor anchor = hitResult.createAnchor();
                                             AnchorNode anchorNode = new AnchorNode( anchor );
                                             anchorNode.setParent( arFragment.getArSceneView()
                                                                             .getScene() );

                                             // Create the transformable andy and add it to the
                                             // anchor.
                                             TransformableNode andy = new TransformableNode(
                                                arFragment.getTransformationSystem() );
                                             andy.setParent( anchorNode );
                                             andy.setRenderable( andyRenderable );
                                             andy.select();
                                          } );
   }

}

