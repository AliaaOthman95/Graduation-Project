package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.SceneAdapter;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.SceneFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Scene;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class SceneActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static Story story;
    private int story_index ;
    private Engine engine;
    private SceneFactory sceneFactory ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scene_engine);
        engine = Engine.getInstance();
        story_index = (int) getIntent().getSerializableExtra("Integer");
        story = Book_Shelf_Activity.stories.get(story_index);

//        setRecyclerView();

        // to make new scene
        ImageButton newScene= (ImageButton)findViewById(R.id.newScene);
        newScene.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                Intent myintent = new Intent(getBaseContext(), SceneCreator.class);
//                myintent.putExtra("Scene",new Scene());
//                startActivity(myintent);


                List<Scene> scenes  = story.getScenes();
                if(scenes == null) scenes = new ArrayList<Scene>();
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

        updateScenesListView();

    }
    private void updateScenesListView(){

        // create scences and set them to adapter
        SceneAdapter adapter = new SceneAdapter(story.getScenes(),this);

        recyclerView.setAdapter(adapter);
    }

    public void onOptionsClick(int position, ImageView sceneCover) {

        Toast.makeText(this,"fataaa7 ya ged3an", Toast.LENGTH_SHORT).show();
        Intent myintent = new Intent(this,SceneCreator.class);
        myintent.putExtra("Integer",position);
        startActivity(myintent);
    }
}