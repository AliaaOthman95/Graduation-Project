package eg.alexu.eng.mobdev.gradprojdemo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream ;

import eg.alexu.eng.mobdev.gradprojdemo.controller.Engine;
import eg.alexu.eng.mobdev.gradprojdemo.view.MainActivity;

public class SQLiteDBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StoryBuilder";

    public static final String STORY_TABLE = "story";
    public static final String STORY_ID = "story_id";
    public static final String STORY_NAME = "story_name";
    public static final String STORY_COVER = "story_cover";
    public static final String STORY_COVER_COLOR = "story_cover_color";
    public static final String STORY_DATE = "story_date";

    public static final String SCENE_TABLE = "scene";
    public static final String SCENE_ID = "scene_id";
    public static final String SCENE_NARRATION = "scene_narration";
    public static final String SCENE_COVER = "scene_cover";

    public static final String ENTITY_TABLE = "entity";
    public static final String ENTITY_ID = "entity_id";
    public static final String ENTITY_NAME = "entity_name";
    public static final String ENTITY_CLASSIFICATION = "entity_classification";
    public static final String ENTITY_POSITION_X = "entity_position_x";
    public static final String ENTITY_POSITION_Y = "entity_position_y";
    public static final String ENTITY_ROTATION_ANGLE = "entity_rotation_angle";
    public static final String ENTITY_SCALE = "entity_scale";
    public static final String ENTITY_IMAGE = "entity_image";

    //Create story Table Query
    private static final String SQL_CREATE_STORY =
            "CREATE TABLE STORY (" + STORY_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + STORY_NAME + " TEXT, " + STORY_COVER + "  TEXT, "
                    + STORY_COVER_COLOR + "  TEXT, " + STORY_DATE + "  DATE NOT NULL);";

    //Create scene Table Query
    private static final String SQL_CREATE_SCENE =
            "CREATE TABLE SCENE (" + SCENE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SCENE_NARRATION + " TEXT, "+ SCENE_COVER +  " TEXT, " +STORY_ID + "  INTEGER, " +"FOREIGN KEY ( " + STORY_ID +" ) " +
                    "REFERENCES "+ "STORY" + " ( " + STORY_ID + " ) );";

    //Create entity Table Query
    private static final String SQL_CREATE_ENTITY =
            "CREATE TABLE ENTITY (" + ENTITY_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ENTITY_NAME+ " TEXT, "+ ENTITY_CLASSIFICATION + " TEXT, "
                    + ENTITY_POSITION_X+ " REAL, " + ENTITY_POSITION_Y+ " REAL, "
                    + ENTITY_ROTATION_ANGLE+ " REAL, " + ENTITY_SCALE+ " REAL, "
                    + ENTITY_IMAGE + " BLOB , "
                    +  SCENE_ID + " INTEGER , "
                    + "FOREIGN KEY (" + SCENE_ID +") "
                    +"REFERENCES "+ SCENE_TABLE + "(" + SCENE_ID + ") );";

    private static final String SQL_DELETE_STORY =
            "DROP TABLE IF EXISTS " + STORY_TABLE;
    private static final String SQL_DELETE_SCENE =
            "DROP TABLE IF EXISTS " + SCENE_TABLE;
    private static final String SQL_DELETE_ENTITY =
            "DROP TABLE IF EXISTS " + ENTITY_TABLE;
    private static final String FOREIGN = "PRAGMA foreign_keys = 1";
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("StoryDB", "onCreate: "+SQL_CREATE_STORY );
        Log.d("SceneDB", "onCreate: "+SQL_CREATE_SCENE );
        db.execSQL(FOREIGN);
        db.execSQL(SQL_CREATE_STORY);
        db.execSQL(SQL_CREATE_SCENE);
        db.execSQL(SQL_CREATE_ENTITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop the table while upgrading the database
        // such as adding new column or changing existing constraint
        db.execSQL(SQL_DELETE_STORY);
        db.execSQL(SQL_DELETE_SCENE);
        db.execSQL(SQL_DELETE_ENTITY);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }

    //Adds a new story
    public boolean addStory(Story story) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Story Name" , story.getStoryName());
        //Create a map having movie details to be inserted
        ContentValues story_details = new ContentValues();
        story_details.put(STORY_NAME, story.getStoryName());
        story_details.put(STORY_COVER, story.getCover());
        story_details.put(STORY_COVER_COLOR, story.getCoverColor());
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        story_details.put(STORY_DATE, date.format(story.getCreationDate()));

        long newRowId = db.insert(STORY_TABLE, null, story_details);
        if(story.getScenes()!=null) {

            for (Scene scene : story.getScenes()) {

                ContentValues scene_details = new ContentValues();
                scene_details.put(SCENE_NARRATION, scene.getNarration());
                scene_details.put(SCENE_COVER, scene.getCover());
                scene_details.put(STORY_ID, story.getStoryId());
                db.insert(SCENE_TABLE, null, scene_details);

                for (Entity entity : scene.getEntities()) {

                    ContentValues entity_details = new ContentValues();
                    entity_details.put(ENTITY_ID, entity.getId());
                    entity_details.put(ENTITY_NAME, entity.getName());
                    entity_details.put(ENTITY_CLASSIFICATION, entity.getClassification());
                    entity_details.put(ENTITY_POSITION_X, entity.getPositionX());
                    entity_details.put(ENTITY_POSITION_Y, entity.getPositionY());
                    entity_details.put(ENTITY_SCALE, entity.getScale());
                    entity_details.put(ENTITY_ROTATION_ANGLE, entity.getRotationAngle());
                    entity_details.put(ENTITY_IMAGE, getBitmapAsByteArray(entity.getImage()));
                    entity_details.put(SCENE_ID, scene.getId());
                    db.insert(ENTITY_TABLE, null, entity_details);
                }

            }
        }

        db.close();
        return newRowId != -1;

    }

    //Retrieves details all stories
    public ArrayList<Story> getAllStories() {

        ArrayList <Story> storyList = new ArrayList<Story>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + STORY_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {

                //story.setStoryId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_ID))));
                //story.setStoryName(cursor.getString(cursor.getColumnIndex(STORY_NAME)));
                //Log.d("dbName ", "name----*"+cursor.getString(cursor.getColumnIndex(STORY_NAME)));
                //story.setCover(cursor.getString(cursor.getColumnIndex(STORY_COVER)));
                //story.setCoverColor(cursor.getString(cursor.getColumnIndex(STORY_COVER_COLOR)));

                Date date1= null;
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(cursor.getColumnIndex(STORY_DATE)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
               // story.setCreationDate(date1);
                Story story = new Story(Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_ID))),cursor.getString(cursor.getColumnIndex(STORY_NAME)),
                        cursor.getString(cursor.getColumnIndex(STORY_COVER)), cursor.getString(cursor.getColumnIndex(STORY_COVER_COLOR)),date1,getAllScenes(Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_ID)))));
                //TODO uncomment tis line when creating the  SCENE_TABLE



                // Adding story to list
                storyList.add(story);
            } while (cursor.moveToNext());
        }
        Log.d("grb", "here");
        // return story list
        return storyList;

    }
    //Retrieves details all stories
    public int getLastStoryId() {

        ArrayList <Story> storyList = new ArrayList<Story>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + STORY_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();

    }
    //Retrieves details all stories
    public int getLastSceneId() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SCENE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();

    }
    //Retrieves details all stories
    public int getLastEntityId() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ENTITY_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();

    }
    //Retrieves details all scenes
    public ArrayList<Scene> getAllScenes(int storyId) {

        ArrayList<Scene> sceneList = new ArrayList<Scene>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + SCENE_TABLE +" WHERE "+ STORY_ID +  " = " + storyId ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Log.d("sceneList", "yaraaaaaaaaaaab");
                Scene scene = new Scene(getAllEntities(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SCENE_ID)))),cursor.getString(cursor.getColumnIndex(SCENE_NARRATION)),Integer.parseInt(cursor.getString(cursor.getColumnIndex(SCENE_ID))),storyId,cursor.getString(cursor.getColumnIndex(SCENE_COVER)));

                // Adding scene to list
                sceneList.add(scene);
            } while (cursor.moveToNext());
        }


        // return scene list
        return sceneList;

    }

    //Retrieves details all entities
    public ArrayList<Entity> getAllEntities(int sceneId) {

        ArrayList<Entity> entityList = new ArrayList<Entity>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ENTITY_TABLE +" WHERE "+ SCENE_ID +  " = " + sceneId ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex(ENTITY_IMAGE));
                Entity entity = new Entity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ENTITY_ID))),cursor.getString(cursor.getColumnIndex(ENTITY_CLASSIFICATION)),
                        cursor.getString(cursor.getColumnIndex(ENTITY_NAME)) ,BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length),
                        Float.valueOf(cursor.getString(cursor.getColumnIndex(ENTITY_POSITION_X))),Float.valueOf(cursor.getString(cursor.getColumnIndex(ENTITY_POSITION_Y))),
                        Float.valueOf(cursor.getString(cursor.getColumnIndex(ENTITY_ROTATION_ANGLE))) ,Float.valueOf(cursor.getString(cursor.getColumnIndex(ENTITY_SCALE))));

                // Adding entity to list
               entityList.add(entity);
            } while (cursor.moveToNext());
        }

        // return entity list
        return entityList;

    }


    //Returns details of a particular story
    public Story getStory(Integer storyId) {

        if(storyId == null) return null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(STORY_TABLE, new String[] { STORY_ID,
                        STORY_NAME, STORY_COVER, STORY_COVER_COLOR, STORY_DATE }, STORY_ID + "=?",
                new String[] { String.valueOf(storyId) }, null, null, null, null);
        Story story = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            try {

                story = new Story(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(4)),getAllScenes(cursor.getColumnIndex(STORY_ID)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return story;

    }

    public boolean sceneExists(Integer sceneId) {

        if(sceneId == null) return false;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SCENE_TABLE, new String[] { SCENE_ID,
                        SCENE_NARRATION, SCENE_COVER, STORY_ID }, SCENE_ID + "=?",
                new String[] { String.valueOf(sceneId) }, null, null, null, null);
        Story story = null;
        if (cursor != null && cursor.getCount() > 0) {
           return true;
        }

        return false;
    }
    public boolean entityExists(Integer entityId) {

        if(entityId == null) return false;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ENTITY_TABLE, new String[] { ENTITY_ID}, ENTITY_ID + "=?",
                new String[] { String.valueOf(entityId) }, null, null, null, null);
        Story story = null;
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    //Update the details of story
    public void updateStory(Story story) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Create a map having story details to be inserted
        ContentValues story_details = new ContentValues();
        story_details.put(STORY_NAME, story.getStoryName());
        story_details.put(STORY_COVER, story.getCover());
        story_details.put(STORY_COVER_COLOR, story.getCoverColor());
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        story_details.put(STORY_DATE, date.format(story.getCreationDate()));

        db.update(STORY_TABLE, story_details, STORY_ID + " = ?",
                new String[]{String.valueOf(story.getStoryId())});
        if(story.getScenes()!= null ) {
            for (Scene scene : story.getScenes()) {

                ContentValues scene_details = new ContentValues();
                scene_details.put(SCENE_NARRATION, scene.getNarration());
                scene_details.put(SCENE_COVER, scene.getCover());
                scene_details.put(STORY_ID, story.getStoryId());
                if (scene.getId() != null && sceneExists(scene.getId())) {
                    db.update(SCENE_TABLE, scene_details, SCENE_ID + " = ?",
                            new String[]{String.valueOf(scene.getId())});
                } else {
                    db.insert(SCENE_TABLE, null, scene_details);
                }
                if (scene.getEntities() != null) {
                    for (Entity entity : scene.getEntities()) {

                        ContentValues entity_details = new ContentValues();
                        entity_details.put(ENTITY_NAME, entity.getName());
                        entity_details.put(ENTITY_CLASSIFICATION, entity.getClassification());
                        entity_details.put(ENTITY_POSITION_X, entity.getPositionX());
                        entity_details.put(ENTITY_POSITION_Y, entity.getPositionY());
                        entity_details.put(ENTITY_SCALE, entity.getScale());
                        entity_details.put(ENTITY_ROTATION_ANGLE, entity.getRotationAngle());
                        entity_details.put(ENTITY_IMAGE, getBitmapAsByteArray(entity.getImage()));
                        entity_details.put(SCENE_ID, scene.getId());

                        if (entity.getId() != null && entityExists(entity.getId())) {
                            db.update(ENTITY_TABLE, entity_details, ENTITY_ID + " = ?",
                                    new String[]{String.valueOf(entity.getId())});
                        } else {
                            db.insert(ENTITY_TABLE, null, entity_details);
                        }
                    }
                }

            }
        }
    }

    //Deletes the specified story
    public void deleteStory(int storyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Scene> scenes = getAllScenes(storyId);
        for(Scene scene : scenes){

            ArrayList<Entity> entities = getAllEntities(scene.getId());
            for(Entity entity : entities){
                deleteEntity(entity.getId());
            }
            deleteScene(scene.getId());
        }

        db.delete(STORY_TABLE, STORY_ID + " = ?",
                new String[] { String.valueOf(storyId) });
        db.close();
    }
    //Deletes the specified scene
    public void deleteScene(int sceneId){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SCENE_TABLE, SCENE_ID + " = ?",
                new String[] { String.valueOf(sceneId) });
        db.close();
    }
    //Deletes the specified entity
    public void deleteEntity(int entityId){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ENTITY_TABLE, ENTITY_ID + " = ?",
                new String[] { String.valueOf(entityId) });
        db.close();
    }

    //change bitmap into byte array
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }


}
