package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import eg.alexu.eng.mobdev.gradprojdemo.R;

public class SceneCreator extends AppCompatActivity {


    private ViewGroup rootLayout;
    private int xDelta;
    private  int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creator);
        getSupportActionBar().getDisplayOptions();

        rootLayout=(ViewGroup) findViewById(R.id.board_scene);
       // Bitmap mIconBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.animal1);

        ImageView image = (ImageView)findViewById(R.id.imageView);
         //image.setImageBitmap(mIconBitmap);

        ImageView image2 = (ImageView)findViewById(R.id.imageView2);
        ImageView image3 = (ImageView)findViewById(R.id.imageView4);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200,200);
        //System.out.print("before **************** " + layoutParams.leftMargin);
        image.setLayoutParams(layoutParams);
        image.setOnTouchListener(new newChoiceTouchListener());
       // System.out.print("after *****************   "+layoutParams.leftMargin);

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(150,150);
        image2.setLayoutParams(layoutParams2);
        image2.setOnTouchListener(new newChoiceTouchListener());

        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(150,150);
        image3.setLayoutParams(layoutParams3);
        image3.setOnTouchListener(new newChoiceTouchListener());



    }

    public class newChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int x= (int) event.getRawX();
            final int y= (int) event.getRawY();
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
            rootLayout.invalidate();

            return true;
        }
    }
























}
