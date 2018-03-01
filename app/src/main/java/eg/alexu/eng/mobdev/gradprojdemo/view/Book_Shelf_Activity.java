package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.BookShelfAdaptor;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.StoryFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class Book_Shelf_Activity extends AppCompatActivity {

    private RecyclerView bookShelfRV ;
    private List<Story> stories ;
    private Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__shelf_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupEngine();

        loadStories();

        setupBookShelf();
        setBookShelfContent(stories);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Story  story = StoryFactory.createRandomStories(1).get(0);
                stories.add(story);

                setBookShelfContent(stories);

                engine.saveStroies(story);



           //
          //      Intent myintent = new Intent(getBaseContext(),SceneEngine.class);
            //    startActivity(myintent);
               // Snackbar.make(view, "lesa shwaya", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });
    }

    private void loadStories() {
        stories = engine.loadStroies();
        if(stories==null)
            stories = new ArrayList<Story>();
        Log.d("loaded stories",stories.size()+"");
    }

    private void setupEngine() {
        engine = Engine.getInstance();
    }

    private void setBookShelfContent(List<Story> stories) {

        BookShelfAdaptor adapter = new BookShelfAdaptor(stories);

        bookShelfRV.setAdapter(adapter);
    }

    private void setupBookShelf() {

        bookShelfRV = (RecyclerView)findViewById(R.id.book_shelf_rv);
        bookShelfRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        bookShelfRV.setLayoutManager(llm);

    }



}
