//과제명:영화리뷰 앱
//분반:02분반
//학번:20170913 성명:김다혜
//제출일:2019년 6월 26일
package ddwucom.mobile.finalreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;

    private ArrayList<Movie> myDataList;
    private MyAdapter myAdapter;
    private ListView listView;
    MovieDBHelper movieDBHelper;
    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataList = new ArrayList<Movie>();

        movieDBManager = new MovieDBManager(this);
        movieDBHelper = new MovieDBHelper(this);

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, myDataList);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) { //롱클릭시 삭제
                final int position = pos;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("영화 삭제");
                builder.setMessage(myDataList.get(position).getTitle() + "을(를) 삭제하시겠습니까?");

                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DB 삭제
                        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
                        String whereClause = MovieDBHelper.COL_ID + "=?";
                        String[] whereArgs = new String[] { String.valueOf(myDataList.get(position).get_id()) };
                        db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs);
                        movieDBHelper.close();

                        //새로운 DB 내용으로 리스트뷰 갱신
                        readAllFoods();
                        myAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //수정 기능 작성 - UpdateActivity 를 결과를 받아오는 형태로 호출
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("movieDto", myDataList.get(pos));
                startActivityForResult(intent, UPDATE_CODE);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        myDataList.clear();
        myDataList.addAll(movieDBManager.getAllMovies());
        myAdapter.notifyDataSetChanged();
    }

    private void readAllFoods() {
            myDataList.clear();

            SQLiteDatabase db = movieDBHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + movieDBHelper.TABLE_NAME, null);

            while(cursor.moveToNext()) {
                long id = cursor.getInt(cursor.getColumnIndex(movieDBHelper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(movieDBHelper.COL_TITLE));
                String director = cursor.getString(cursor.getColumnIndex(movieDBHelper.COL_DIRECTOR));
                String day = cursor.getString(cursor.getColumnIndex(movieDBHelper.COL_DAY));
                String genre = cursor.getString(cursor.getColumnIndex(movieDBHelper.COL_GENRE));
                String actor = cursor.getString(cursor.getColumnIndex(movieDBHelper.COL_ACTOR));
                myDataList.add (new Movie(id, title, director, day, genre, actor));
            }

        cursor.close();
        movieDBHelper.close();

    }

    public boolean onCreateOptionsMenu(Menu menu) { //menu inflation
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.menu_search:
                Intent intent1 = new Intent(this, SearchActivity.class);
                intent1.putExtra("movieDto", myDataList);
                startActivity(intent1);
                break;
            case R.id.menu_intro:
                Intent intent2 = new Intent(this, IntroActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_off:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("종료 확인");
                builder.setMessage("앱을 종료하시겠습니까?");

                builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       finish();
                    }
                });

                builder.setNegativeButton("취소", null);
                builder.show();
                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {  //AddActivity 호출 후 결과 확인
            switch(resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "영화 추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "영화 추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == UPDATE_CODE) {    //UpdateActivity 호출 후 결과 확인
            switch(resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "영화 수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "영화 수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
