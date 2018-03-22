package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.SceneCreator;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.SceneAdapter;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.SceneFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;

public class SceneEngine extends AppCompatActivity {

    private RecyclerView recyclerView;

    SceneFactory sceneFactory ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_engine);

        setRecyclerView();

        // to make new scene
        ImageButton newScene= (ImageButton)findViewById(R.id.newScene);
        newScene.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(getBaseContext(), SceneCreator.class);
                startActivity(myintent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_scenes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.save_story :
                // save story
                return true;
            case R.id.search_scene:
                // search
                return true;
            case R.id.delete_scene:
                // delete
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // make grid layout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.scrollToPosition(2);

        // set grid layout to adapter
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // create scences and set them to adapter
        SceneAdapter adapter = new SceneAdapter(sceneFactory.createScenes());
        recyclerView.setAdapter(adapter);
    }
}