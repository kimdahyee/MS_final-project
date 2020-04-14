package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.sql.Blob;

public class MovieDBHelper extends SQLiteOpenHelper {
    final static String TAG = "MovieDBHelper";

    final static String DB_NAME = "movies.db";
    public final static String TABLE_NAME = "movie_table";

    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_DIRECTOR = "director";
    public final static String COL_DAY = "day";
    public final static String COL_GENRE = "genre";
    public final static String COL_ACTOR = "actor";

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, "
                + COL_TITLE + " TEXT, " +  COL_DIRECTOR + " TEXT, " +  COL_DAY + " TEXT, " +  COL_GENRE + " TEXT, " + COL_ACTOR + " TEXT)";
        db.execSQL(sql);

        db.execSQL("INSERT INTO " + MovieDBHelper.TABLE_NAME + " VALUES (NULL, '알라딘', '가이 리치', '2019/5/23', '판타지', '메나 마수드');");
        db.execSQL("INSERT INTO " + MovieDBHelper.TABLE_NAME + " VALUES (NULL, '토이스토리4', '조시 쿨리', '2019/6/20', '애니메이션', '톰 행크스');");
        db.execSQL("INSERT INTO " + MovieDBHelper.TABLE_NAME + " VALUES (NULL, '기생충', '봉준호', '2019/5/30', '드라마', '송강호');");
        db.execSQL("INSERT INTO " + MovieDBHelper.TABLE_NAME + " VALUES (NULL, '맨인블랙', 'F.게리 그레이', '2019/6/12', '액션', '크리스 헴스워스');");
        db.execSQL("INSERT INTO " + MovieDBHelper.TABLE_NAME + " VALUES (NULL, '사탄의 인형', '라스 클리브버그', '2019/6/20', '공포', '마크 해밀');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
