package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import eg.alexu.eng.mobdev.gradprojdemo.R;

public class SceneCreator extends AppCompatActivity {


    private ViewGroup rootLayout;
    private int xDelta;
    private  int yDelta;
    private  ImageButton sendWord;
    private EditText entityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creator);
        getSupportActionBar().getDisplayOptions();
        getEntity();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scene_creator,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
    }

    private void getEntity() {
        final Context context =getApplicationContext();
        rootLayout=(ViewGroup) findViewById(R.id.board_scene);
        sendWord = (ImageButton) findViewById(R.id.send);
        entityName=(EditText) findViewById(R.id.text);
        sendWord.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                // getentity
                   String descreption = entityName.getText().toString();
                   int imageID = getResources().getIdentifier(descreption,
                           "drawable", getPackageName());
                   ImageView image = new ImageView(getApplicationContext());
                   Bitmap mIconBitmap = BitmapFactory.decodeResource(getResources(), imageID);
                   image.setImageBitmap(mIconBitmap);
                   RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
                   image.setLayoutParams(layoutParams);
                   image.setOnTouchListener(new newChoiceTouchListener());
                   rootLayout.addView(image);


            }
        });
    }

    public class newChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int x= (int) event.getRawX();
            final int y= (int) event.getRawY();
            Log.d("Point" ,"*****************"+ x +"*******" +y);
            switch (event.getAction() & MotionEvent.ACTION_MASK){

                case  MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    xDelta=x-lParams.leftMargin;
                    yDelta=y-lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    lParams.leftMargin=x-xDelta;
                    lParams.topMargin=y-yDelta;
                    lParams.rightMargin=-250;
                    v.setLayoutParams(lParams);
                    break;

            }
            Log.d("View" ,"*****************"+ event.getRawX() +"*******" +event.getRawY());
            rootLayout.invalidate();

            return true;
        }
    }
























}
