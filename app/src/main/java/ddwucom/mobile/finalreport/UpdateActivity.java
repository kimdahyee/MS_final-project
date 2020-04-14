package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    ImageView imageView;
    EditText title;
    EditText director;
    DatePicker datePicker;
    EditText genre;
    EditText actor;

    MovieDBHelper dbHelper;
    Movie movieDto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new MovieDBHelper(this);

        imageView = findViewById(R.id.imageView2);
        title = findViewById(R.id.u_title);
        director = findViewById(R.id.u_dir);
        datePicker = findViewById(R.id.datePicker);
        genre = findViewById(R.id.u_genre);
        actor = findViewById(R.id.u_actor);

        //MainActivity 에서 전달한 intent 를 통해 movieDto 추출
        Intent intent = getIntent();
        movieDto = (Movie)intent.getSerializableExtra("movieDto");

        if (movieDto.get_id() % 5 == 1) {
            imageView.setImageResource(R.mipmap.aladin);
        }
        else if (movieDto.get_id() % 5 == 2) {
            imageView.setImageResource(R.mipmap.toy);
        }
        else if (movieDto.get_id() % 5 == 3) {
            imageView.setImageResource(R.mipmap.gi);
        }
        else if (movieDto.get_id() % 5 == 4) {
            imageView.setImageResource(R.mipmap.man);
        }
        else {
            imageView.setImageResource(R.mipmap.satan);
        }

        title.setText(movieDto.getTitle());
        director.setText(movieDto.getDirector());
        //날짜 읽어와서 datePicker 값 설정
        genre.setText(movieDto.getGenre());
        actor.setText(movieDto.getActor());
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update:
                String update_title = title.getText().toString();
                String update_dir = director.getText().toString();
                String update_date = String.format("%d/%d/%d", datePicker.getYear(), datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth());
                String update_genre = genre.getText().toString();
                String update_actor = actor.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues row = new ContentValues();

                row.put(MovieDBHelper.COL_TITLE, update_title);
                row.put(MovieDBHelper.COL_DIRECTOR, update_dir);
                row.put(MovieDBHelper.COL_DAY, update_date);
                row.put(MovieDBHelper.COL_GENRE, update_genre);
                row.put(MovieDBHelper.COL_ACTOR, update_actor);

                String whereClause = MovieDBHelper.COL_ID + "=?";
                //MainActivity 에서 전달받은 foodDto 의 id를 사용하여 수정할 데이터 식별
                String[] whereArgs = new String[] {String.valueOf(movieDto.get_id()) };

                if (title.getText().toString().length() == 0) {
                    Toast.makeText(this, "제목을 입력하세요",Toast.LENGTH_SHORT).show();
                    title.requestFocus();
                    return;
                }
                else if (director.getText().toString().length() == 0) {
                    Toast.makeText(this, "감독을 입력하세요", Toast.LENGTH_SHORT).show();
                    director.requestFocus();
                    return;
                }
                else if (genre.getText().toString().length() == 0) {
                    Toast.makeText(this, "장르를 입력하세요", Toast.LENGTH_SHORT).show();
                    genre.requestFocus();
                    return;
                }
                else if (actor.getText().toString().length() == 0) {
                    Toast.makeText(this, "주연 배우를 입력하세요", Toast.LENGTH_SHORT).show();
                    actor.requestFocus();
                    return;
                }
                else {
                    //update 메소드 사용 시 변경된 레코드 수 반환
                    long count = db.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                    if (count > 0) {
                        setResult(RESULT_OK, null);
                        dbHelper.close();
                        finish();
                    } else {
                        Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        dbHelper.close();
                    }
                    break;
                }
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
