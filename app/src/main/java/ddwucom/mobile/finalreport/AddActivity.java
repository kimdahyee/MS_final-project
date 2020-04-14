package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    ImageView imageView;
    EditText title;
    EditText director;
    DatePicker datePicker;
    EditText genre;
    EditText actor;

    MovieDBHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imageView = findViewById(R.id.imageView2);
        title = findViewById(R.id.a_title);
        director = findViewById(R.id.a_dir);
        datePicker = findViewById(R.id.datePicker);
        genre = findViewById(R.id.a_genre);
        actor = findViewById(R.id.a_actor);

        helper = new MovieDBHelper(this);

        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:

                SQLiteDatabase db = helper.getWritableDatabase();
                final ContentValues value = new ContentValues();

                value.put(MovieDBHelper.COL_TITLE, title.getText().toString());
                value.put(MovieDBHelper.COL_DIRECTOR, director.getText().toString());

                datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), null);
                String date = String.format("%d/%d/%d", datePicker.getYear(), datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth());
                value.put(MovieDBHelper.COL_DAY, date);

                value.put(MovieDBHelper.COL_GENRE, genre.getText().toString());
                value.put(MovieDBHelper.COL_ACTOR, actor.getText().toString());

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
                    //insert 메소드를 사용할 경우 데이터 삽입이 정상적으로 이루어질 경우 1 이상, 이상이 있을 경우 0 반환 확인 가능
                    long count = db.insert(MovieDBHelper.TABLE_NAME, null, value);

                    if (count > 0) {    // 정상수행에 따른 처리
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        helper.close();
                        finish();
                    } else {        // 이상에 따른 처리
                        Toast.makeText(this, "영화 추가 실패", Toast.LENGTH_SHORT).show();
                        helper.close();
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
