package com.mobile.harsoft.mymoviecatalogues.SQLiteDatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.harsoft.mymoviecatalogues.R;

public class DbTvShows extends SQLiteOpenHelper {
    public DbTvShows(Context context) {
        super(context, "tvshow.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_tvShow = "create table tb_tvShow (id integer primary key autoincrement, title text ," +
                "ilustration blob, year text, synopsis text, category text, rate double, stars text)";
        db.execSQL(tb_tvShow);

        ContentValues values = new ContentValues();
        values.put("id", "1");
        values.put("title", "Arrow");
        values.put("ilustration", R.drawable.poster_arrow);
        values.put("year", "2012");
        values.put("synopsis", "Spoiled billionaire playboy Oliver Queen is missing and presumed " +
                "dead when his yacht is lost at sea. He returns five years later a changed man, " +
                "determined to clean up the city as a hooded vigilante armed with a bow.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Crime");
        values.put("rate", 7.6);
        values.put("stars", "Stephen Amell, Katie Cassidy, David Ramsey");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "2");
        values.put("title", "Doom Patrol");
        values.put("ilustration", R.drawable.poster_doom_patrol);
        values.put("year", "2019");
        values.put("synopsis", "The adventures of an idealistic mad scientist and his field team of " +
                "superpowered outcasts.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Comedy");
        values.put("rate", 8.2);
        values.put("stars", "Diane Guerrero, April Bowlby, Alan Tudyk ");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "3");
        values.put("title", "Flash");
        values.put("ilustration", R.drawable.poster_flash);
        values.put("year", "2014");
        values.put("synopsis", "After being struck by lightning, Barry Allen wakes up from his coma" +
                " to discover he's been given the power of super speed, becoming the Flash, fighting " +
                "crime in Central City.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Drama");
        values.put("rate", 7.8);
        values.put("stars", "Grant Gustin, Candice Patton, Danielle Panabaker");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "4");
        values.put("title", "Gotham");
        values.put("ilustration", R.drawable.poster_gotham);
        values.put("year", "2014");
        values.put("synopsis", "The story behind Detective James Gordon's rise to prominence in " +
                "Gotham City in the years before Batman's arrival.\n(Source: IMDb)");
        values.put("category", "Action, Crime, Drama");
        values.put("rate", 7.9);
        values.put("stars", "Ben McKenzie, Jada Pinkett Smith, Donal Logue");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "5");
        values.put("title", "Grey's Anatomy");
        values.put("ilustration", R.drawable.poster_grey_anatomy);
        values.put("year", "2005");
        values.put("synopsis", "A drama centered on the personal and professional lives of five " +
                "surgical interns and their supervisors.\n(Source: IMDb)");
        values.put("category", " Drama, Romance");
        values.put("rate", 7.6);
        values.put("stars", "Justin Chambers, Ellen Pompeo, Chandra Wilson");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "6");
        values.put("title", "Hanna");
        values.put("ilustration", R.drawable.poster_hanna);
        values.put("year", "2019");
        values.put("synopsis", "In equal parts high-concept thriller and coming-of-age drama, HANNA " +
                "follows the journey of an extraordinary young girl raised in the forest, as " +
                "she evades the relentless pursuit of an off-book CIA agent and tries to unearth " +
                "the truth behind who she is.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Crime");
        values.put("rate", 7.6);
        values.put("stars", "Esme Creed-Miles, Mireille Enos, Joel Kinnaman");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "7");
        values.put("title", "Iron Fist");
        values.put("ilustration", R.drawable.poster_iron_fist);
        values.put("year", "2017");
        values.put("synopsis", "A young man is bestowed with incredible martial arts skills and a" +
                " mystical force known as the Iron Fist.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Crime");
        values.put("rate", 6.6);
        values.put("stars", "Finn Jones, Jessica Henwick, Jessica Stroup");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "8");
        values.put("title", "NCIS");
        values.put("ilustration", R.drawable.poster_ncis);
        values.put("year", "2003");
        values.put("synopsis", "The cases of the Naval Criminal Investigative Service's Washington," +
                " D.C. Major Case Response Team, led by Special Agent Leroy Jethro " +
                "Gibbs.\n(Source: IMDb)");
        values.put("category", "Action, Crime, Drama");
        values.put("rate", 7.8);
        values.put("stars", "Mark Harmon, David McCallum, Sean Murray");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "9");
        values.put("title", "Riverdale");
        values.put("ilustration", R.drawable.poster_riverdale);
        values.put("year", "2017");
        values.put("synopsis", "While navigating the troubled waters of romance, school and family," +
                " Archie and his gang become entangled in dark Riverdale mysteries\n(Source: IMDb)");
        values.put("category", "Crime, Drama, Mystery");
        values.put("rate", 7.3);
        values.put("stars", "K.J. Apa, Lili Reinhart, Camila Mendes");
        db.insert("tb_tvShow", "id", values);

        values.put("id", "10");
        values.put("title", "Supernatural");
        values.put("ilustration", R.drawable.poster_supernatural);
        values.put("year", "2005");
        values.put("synopsis", "Two brothers follow their father's footsteps as hunters, fighting " +
                "evil supernatural beings of many kinds, including monsters, demons, and gods that " +
                "roam the earth.\n(Source: IMDb)");
        values.put("category", "Drama, Fantasy, Horror");
        values.put("rate", 8.5);
        values.put("stars", "Jared Padalecki, Jensen Ackles, Jim Beaver");
        db.insert("tb_tvShow", "id", values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_tvShow");
        onCreate(db);
    }
}
