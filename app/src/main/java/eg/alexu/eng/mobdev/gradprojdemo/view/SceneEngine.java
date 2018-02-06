package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.SceneCreator;

public class SceneEngine extends AppCompatActivity {

    ImageButton newScene;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_engine);

        newScene= (ImageButton)findViewById(R.id.newScene);

        newScene.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(getBaseContext(), SceneCreator.class);
                startActivity(myintent);

            }
        });

    }
}
