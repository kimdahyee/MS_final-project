package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText editText;
    EditText editText2;

    ArrayList<Movie> myDataList;
    MovieDBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText = (EditText)findViewById(R.id.s_title);
        editText2 = (EditText)findViewById(R.id.s_result);

        Intent intent = getIntent();
        myDataList = (ArrayList<Movie>) intent.getSerializableExtra("movieDto");

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String s_title = editText.getText().toString();

                if (s_title == null) {
                    Toast.makeText(this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    String result = "";
                    for (int i = 0; i < myDataList.size(); i++) {
                        if (s_title.equals(myDataList.get(i).getTitle())) {
                            String title = myDataList.get(i).getTitle();
                            String dir = myDataList.get(i).getDirector();
                            String day = myDataList.get(i).getDay();
                            String genre = myDataList.get(i).getGenre();
                            String actor = myDataList.get(i).getActor();

                            result += "제목 : " + title + "\n감독 : " + dir + "\n개봉일 : " + day + "\n장르 : "
                                    + genre + "\n주연 배우 : " + actor + "\n\n";
                        }
                        else {
                            Toast.makeText(this, "존재하지 않는 영화입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    editText2.setText(result);
                }
                break;
        }
    }
}
