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
import android.support.v7.widget.RecyclerView;
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
    private int story_index ;
    private Engine engine;
    private AlertDialog dialog;
    SceneFactory sceneFactory ;

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
                dialogPopUp();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogPopUp() {


        AlertDialog.Builder mbuilder = new AlertDialog.Builder(SceneActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog,null);

        // fileds of dialog
        final EditText story_name = (EditText) mview.findViewById(R.id.text);
        Button ok_button =(Button) mview.findViewById(R.id.ok);


        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get entity from server
                if(!story_name.getText().toString().isEmpty()){
                   String name_of_story = story_name.getText().toString();
                    story.setStoryName(name_of_story);
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