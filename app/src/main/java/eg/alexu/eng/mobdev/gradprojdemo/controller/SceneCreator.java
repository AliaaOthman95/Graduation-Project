package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.model.Entity;

public class SceneCreator extends AppCompatActivity {


    private ViewGroup rootLayout;
    private int xDelta;
    private  int yDelta;
    private  ImageButton sendWord;
    private EditText entity_desception;
    private  AlertDialog dialog;
    private ArrayList<Entity> entities ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creator);
        getSupportActionBar().getDisplayOptions();

        createNewEntity();
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

    private void createNewEntity() {


        sendWord = (ImageButton) findViewById(R.id.send);
       // entityName=(EditText) findViewById(R.id.text);
        sendWord.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

               dialogPopUp();
            }
        });
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
