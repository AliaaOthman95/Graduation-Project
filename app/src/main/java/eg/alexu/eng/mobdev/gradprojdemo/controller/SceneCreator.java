package eg.alexu.eng.mobdev.gradprojdemo.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eg.alexu.eng.mobdev.gradprojdemo.R;

public class SceneCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creator);

        getSupportActionBar().getDisplayOptions();
    }
}
