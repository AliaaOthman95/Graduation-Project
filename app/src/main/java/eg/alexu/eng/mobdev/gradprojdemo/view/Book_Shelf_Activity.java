package eg.alexu.eng.mobdev.gradprojdemo.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import eg.alexu.eng.mobdev.gradprojdemo.R;
import eg.alexu.eng.mobdev.gradprojdemo.controller.adaptors.BookShelfAdaptor;
import eg.alexu.eng.mobdev.gradprojdemo.controller.factories.StoryFactory;
import eg.alexu.eng.mobdev.gradprojdemo.model.Story;

public class Book_Shelf_Activity extends AppCompatActivity {

    private RecyclerView bookShelfRV ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__shelf_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBookShelf();

        setBookShelfContent(StoryFactory.createRandomStories());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "lesa shwaya", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
