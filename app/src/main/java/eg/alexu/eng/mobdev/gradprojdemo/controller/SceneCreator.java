package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.model.Entity;

public class SceneCreator extends AppCompatActivity {

    private float scalediff;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private ViewGroup rootLayout;
    private  ImageButton addEntity;
    private EditText entity_desception;
    private  AlertDialog dialog;
    private ArrayList<Entity> entities ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creator);
        getSupportActionBar().getDisplayOptions();


        addEntity = (ImageButton) findViewById(R.id.send);
        // entityName=(EditText) findViewById(R.id.text);
        addEntity.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                dialogPopUp();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scene_creator,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_story:

                return true;

        }
        return true;
    }


    private void dialogPopUp() {

        AlertDialog.Builder mbuilder = new AlertDialog.Builder(SceneCreator.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog,null);

        // fileds of dialog
        entity_desception=(EditText) mview.findViewById(R.id.text);
        Button ok_button =(Button) mview.findViewById(R.id.ok);


        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get entity from server
                if(!entity_desception.getText().toString().isEmpty()){
                    showEntity(entity_desception.getText().toString().toLowerCase());
                    dialog.dismiss();
                }
            }

        });

        ImageButton speech =(ImageButton) mview.findViewById(R.id.speech);
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                try{
                    startActivityForResult(intent,200);
                }catch (ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"Speech Intent problem",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancel = (Button) mview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mbuilder.setView(mview);
        dialog = mbuilder.create();
        dialog.show();
        return;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 200: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    entity_desception.setText(result.get(0));
                }
                break;
            }
        }
    }

    private void showEntity(String descreption) {
        final Context context =getApplicationContext();
        rootLayout=(ViewGroup) findViewById(R.id.board_scene);
        entities= new ArrayList<>();

        // get image of name equal of descreption
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



    // darg and zoom and rotate
    public class newChoiceTouchListener implements View.OnTouchListener {
        RelativeLayout.LayoutParams parms;
        int startwidth;
        int startheight;
        float dx = 0, dy = 0, x = 0, y = 0;
        float angle = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final ImageView view = (ImageView) v;

            ((BitmapDrawable) view.getDrawable()).setAntiAlias(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    parms = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    startwidth = parms.width;
                    startheight = parms.height;
                    dx = event.getRawX() - parms.leftMargin;
                    dy = event.getRawY() - parms.topMargin;
                    mode = DRAG;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    if (oldDist > 10f) {
                        mode = ZOOM;
                    }

                    d = rotation(event);

                    break;
                case MotionEvent.ACTION_UP:

                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {

                        x = event.getRawX();
                        y = event.getRawY();

                        parms.leftMargin = (int) (x - dx);
                        parms.topMargin = (int) (y - dy);

                        parms.rightMargin = 0;
                        parms.bottomMargin = 0;
                        parms.rightMargin = parms.leftMargin + (5 * parms.width);
                        parms.bottomMargin = parms.topMargin + (10 * parms.height);

                        view.setLayoutParams(parms);

                    } else if (mode == ZOOM) {

                        if (event.getPointerCount() == 2) {

                            newRot = rotation(event);
                            float r = newRot - d;
                            angle = r;

                            x = event.getRawX();
                            y = event.getRawY();

                            float newDist = spacing(event);
                            if (newDist > 10f) {
                                float scale = newDist / oldDist * view.getScaleX();
                                if (scale > 0.6) {
                                    scalediff = scale;
                                    view.setScaleX(scale);
                                    view.setScaleY(scale);

                                }
                            }

                            view.animate().rotationBy(angle).setDuration(0).setInterpolator(new LinearInterpolator()).start();

                            x = event.getRawX();
                            y = event.getRawY();

                            parms.leftMargin = (int) ((x - dx) + scalediff);
                            parms.topMargin = (int) ((y - dy) + scalediff);

                            parms.rightMargin = 0;
                            parms.bottomMargin = 0;
                            parms.rightMargin = parms.leftMargin + (5 * parms.width);
                            parms.bottomMargin = parms.topMargin + (10 * parms.height);

                            view.setLayoutParams(parms);


                        }
                    }
                    break;
            }

            return true;

        }
        private float spacing(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }

        private float rotation(MotionEvent event) {
            double delta_x = (event.getX(0) - event.getX(1));
            double delta_y = (event.getY(0) - event.getY(1));
            double radians = Math.atan2(delta_y, delta_x);
            return (float) Math.toDegrees(radians);
        }
    }

























}
