package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.EntityFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Entity;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;
import eg.alexu.eng.mobdev.gradprojdemo.view.SceneCreator;
import static eg.alexu.eng.mobdev.gradprojdemo.view.SceneEngine.story;


public class SceneCreator extends AppCompatActivity {

    private float scalediff;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private ViewGroup rootLayout ;
    private ImageButton addEntity;
    private EditText entity_desception;
    private AlertDialog dialog;
    private List<Entity> entities ;
    private Scene scene;
    private int sceneIndex ;
    private Engine engine;
    private Map<ImageView,Entity> imageEntityMap ;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creator);
        getSupportActionBar().getDisplayOptions();
        sceneIndex = (int) getIntent().getSerializableExtra("Integer");
        scene = story.getScenes().get(sceneIndex);
        entities =  scene.getEntities();
        engine= Engine.getInstance();
        imageEntityMap = new HashMap<ImageView,Entity>();
        if(entities == null){
            entities = new ArrayList<Entity>();
        }

        rootLayout=(ViewGroup) findViewById(R.id.board_scene);

        loadEntity();

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

        /*
        if(true) {
            Random random = new Random();
            Boolean randomBool = random.nextBoolean();
            String desc = randomBool?"elephant":"giraffe";
            createEntity(desc);
            return;
        }
        */

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
                    dialog.dismiss();
                    /*View mview = getLayoutInflater().inflate(R.layout.activity_scene_creator,null);
                    progressBar = (ProgressBar) findViewById(R.id.progressbar);
                    progressBar.setVisibility(mview.VISIBLE);*/
                    createEntity(entity_desception.getText().toString().toLowerCase());
                   // progressBar.setVisibility(mview.INVISIBLE);

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

    private void  createEntity(String descreption){
        int imageID = getResources().getIdentifier(descreption,"drawable", getPackageName());
        SendHttpRequestTask task = new SendHttpRequestTask();
        Bitmap result = null;
        Entity entity = null;
        try {
            descreption = descreption.replaceAll(" ","%20");
            Log.d("get image",descreption);
            result = task.execute("http://35.229.126.53:5000/"+descreption).get();
            //result = task.execute("https://vignette.wikia.nocookie.net/disney/images/0/0a/ElsaPose.png/revision/latest?cb=20170221004839").get();
            Log.d("get image","wasal ya m3alam");

            entity = new Entity(null , null,descreption, result, (float)0,(float) 0 , (float)0 , (float)1,(float)1);

        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        showEntity(entity);
        entities.add(entity);
        scene.setEntities(entities);
        engine.saveStroies(SceneEngine.story);
        entity.setId(engine.getLastEntityId());
    }
    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {

       //ProgressDialog progressDialog = new ProgressDialog(SceneCreator.this);//getApplicationContext()
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
           /* this.progressDialog.setMessage("Please wait...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();*/

        }
            @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Thread.sleep(5000);
                return myBitmap;
            }catch (Exception e){
                Log.d("imageError",e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
           /* if (progressDialog.isShowing())
                progressDialog.dismiss();*/
           /* View mview = getLayoutInflater().inflate(R.layout.activity_scene_creator,null);
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            progressBar.setVisibility(mview.INVISIBLE);*/
        }


    }

    private void loadEntity() {
        if(entities != null ){
            for(Entity entity : entities){
              showEntity(entity);
            }
        }
    }

    private void showEntity(Entity entity){

        ImageView image = new ImageView(getApplicationContext());
        image.setImageBitmap(entity.getImage());
        image.setX(entity.getPositionX());
        image.setY(entity.getPositionY());
        image.setRotation(entity.getRotationAngle());
        image.setScaleX(entity.getScaleX());
        image.setScaleY(entity.getScaleY());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
        image.setLayoutParams(layoutParams);

        image.setOnTouchListener(new newChoiceTouchListener());
        rootLayout.addView(image);

        // keep mapping between image view and its
        // corresponding entity object
        // to relate the action listeners on the image view to entities

        imageEntityMap.put(image,entity);

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
            Entity e = imageEntityMap.get(v);
            x=e.getPositionX();
            y=e.getPositionY();
            angle=e.getRotationAngle();
            Log.d("touched" , e.getClassification()+" "+e.getPositionX()+" "+e.getPositionY());
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

            e.setPositionX(v.getX());
            e.setPositionY(v.getY());
            e.setRotationAngle(v.getRotation());
            e.setScaleY(v.getScaleX());
            e.setScaleX(v.getScaleY());



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

    @Override
    protected void onPause() {
        Toast.makeText(this,"da5aaal on pause",Toast.LENGTH_SHORT).show();
        super.onPause();
        View v1 = rootLayout;
        v1.setDrawingCacheEnabled(true);
        Bitmap sceneCoverbitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        scene.setCover(sceneCoverbitmap);

    }
}
