package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.SceneAdapter;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.SceneFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class SceneActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static Story story;
    private List<Scene> scenes;
    private int story_index ;
    private Engine engine;


    SceneFactory sceneFactory ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scene_engine);
        engine = Engine.getInstance();
        story_index = (int) getIntent().getSerializableExtra("Integer");
        story = Book_Shelf_Activity.stories.get(story_index);

        scenes  = story.getScenes();
        if(scenes == null) scenes = new ArrayList<Scene>();

//        setRecyclerView();

        logScenes();

        // to make new scene
        ImageButton newScene= (ImageButton)findViewById(R.id.newScene);
        newScene.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                Intent myintent = new Intent(getBaseContext(), SceneCreator.class);
//                myintent.putExtra("Scene",new Scene());
//                startActivity(myintent);




                Log.d("scene info","new scene will be created");
                Scene scene = getNewScene() ;

                scenes.add(scene);
                story.setScenes(scenes);

                engine.saveStroies(story);
                scene.setId(engine.getLastSceneId());
                updateScenesListView();

                // update Bookshelf stories

                //Book_Shelf_Activity.stories.set(story_index,story);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    private Scene getNewScene() {
        return SceneFactory.createBlackScene();
    }




    public void onOptionsClick(final int index , View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(this, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_my_scenes);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.story_menu_del:
                        Toast.makeText(getBaseContext(),"you deleted the scene"+scenes.get(index)
                                ,Toast.LENGTH_LONG).show();
                        engine.deleteScene(story.getStoryId(),index);
                        // update view *************************************************
                        scenes.remove(index);
                        updateScenesListView();

                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
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

        updateScenesListView();

    }
    private void updateScenesListView(){

        // create scences and set them to adapter
        SceneAdapter adapter = new SceneAdapter(story.getScenes(),this);

        recyclerView.setAdapter(adapter);
    }

    public void onSceneClick(int position, ImageView sceneCover) {

        Toast.makeText(this,"fataaa7 ya ged3an", Toast.LENGTH_SHORT).show();
        Intent myintent = new Intent(this,SceneCreator.class);
        myintent.putExtra("Integer",position);
        startActivity(myintent);
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("scene info","sceneActivity paused");
        //engine.saveStroies(story);
    }

    private void logScenes() {

        for (Scene scene : scenes) {
            Log.d("scene info",scene.getId()+"");
        }
    }
}