package ddwucom.mobile.finalreport;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.sql.Blob;

public class Movie implements Serializable {
    long _id;
    String title;
    String director;
    String day;
    String genre; //삽입/수정 액티비티에 표시할 정보
    String actor; //삽입/수정 액티비티에 표시할 정보

    public Movie(String title, String director, String day, String genre, String actor) {
        this.title = title;
        this.director = director;
        this.day = day;
        this.genre = genre;
        this.actor = actor;
    }

    public Movie(long _id, String title, String director, String day, String genre, String actor) {
        this._id = _id;
        this.title = title;
        this.director = director;
        this.day = day;
        this.genre = genre;
        this.actor = actor;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
