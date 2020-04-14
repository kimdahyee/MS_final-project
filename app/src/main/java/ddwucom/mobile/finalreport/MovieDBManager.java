package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import java.util.ArrayList;

public class MovieDBManager {
    EditText title;
    EditText director;
    EditText day;
    EditText genre;
    EditText actor;

    MovieDBHelper helper = null;
    Cursor cursor = null;

    public MovieDBManager(Context context) {
        helper = new MovieDBHelper(context);
    }

    //DB의 모든 movie를 반환
    public ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movieList = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
            String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            String day = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DAY));
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE));
            String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
            movieList.add(new Movie(id, title, director, day, genre, actor));
        }

        cursor.close();
        db.close();

        return movieList;
    }

    public void close() {
        if (helper != null) helper.close();
        if (cursor != null) cursor.close();
    };
}
