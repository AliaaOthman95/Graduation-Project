package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eg.alexu.eng.mobdev.gradprojdemo.R;

public class EntityCreator extends Activity {
    private EditText entity_desception;
    private Button ok_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_creator);
        showPopUp();

        getEntity();


    }

    private void getEntity() {
        entity_desception=(EditText) findViewById(R.id.text);
        ok_button =(Button) findViewById(R.id.ok);
        ok_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String desciption = entity_desception.getText().toString();
                Intent intent = new Intent(getBaseContext(),SceneCreator.class);
                intent.putExtra("Desciption",desciption);
                Log.d("gamal",intent.getStringExtra("Desciption"));
                startActivity(intent);


            }


        });
    }

    private void showPopUp() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.4));
    }


}
