package com.mobile.harsoft.mymoviecatalogues.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.harsoft.mymoviecatalogues.R;

public class DbMovies extends SQLiteOpenHelper {
    public DbMovies(Context context) {
        super(context, "movies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_movie = "create table tb_movie (id integer primary key autoincrement, title text ," +
                "ilustration blob, year text, synopsis text, category text, rate double, metascore double)";
        db.execSQL(tb_movie);

        ContentValues values = new ContentValues();
        values.put("id", "1");
        values.put("title", "Aquaman");
        values.put("ilustration", R.drawable.poster_aquaman);
        values.put("year", "2018");
        values.put("synopsis", "Arthur Curry, the human-born heir to the underwater kingdom " +
                "of Atlantis, goes on a quest to prevent a war between the worlds of ocean " +
                "and land.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Fantasy");
        values.put("rate", 7.1);
        values.put("metascore", 55);
        db.insert("tb_movie", "id", values);

        values.put("id", "2");
        values.put("title", "Avenger Infinity War");
        values.put("ilustration", R.drawable.poster_avengerinfinity);
        values.put("year", "2018");
        values.put("synopsis", "The Avengers and their allies must be willing to sacrifice all in " +
                "an attempt to defeat the powerful Thanos before his blitz of devastation and ruin " +
                "puts an end to the universe.\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Sci-Fi");
        values.put("rate", 8.5);
        values.put("metascore", 68);
        db.insert("tb_movie", "id", values);

        values.put("id", "3");
        values.put("title", "Bird Box");
        values.put("ilustration", R.drawable.poster_birdbox);
        values.put("year", "2018");
        values.put("synopsis", "Five years after an ominous unseen presence drives most of society " +
                "to suicide, a mother and her two children make a desperate bid to reach safety." +
                "\n(Source: IMDb)");
        values.put("category", "Drama, Horror, Sci-Fi");
        values.put("rate", 6.6);
        values.put("metascore", 51);
        db.insert("tb_movie", "id", values);

        values.put("id", "4");
        values.put("title", "Bohemian Rhapsody");
        values.put("ilustration", R.drawable.poster_bohemian);
        values.put("year", "2018");
        values.put("synopsis", "The story of the legendary rock band Queen and lead singer Freddie" +
                " Mercury, leading up to their famous performance at Live Aid (1985).\n(Source: IMDb)");
        values.put("category", "Biography, Drama, Music");
        values.put("rate", 8);
        values.put("metascore", 49);
        db.insert("tb_movie", "id", values);

        values.put("id", "5");
        values.put("title", "Bumblebee");
        values.put("ilustration", R.drawable.poster_bumblebee);
        values.put("year", "2018");
        values.put("synopsis", "On the run in the year 1987, Bumblebee finds refuge in a junkyard " +
                "in a small California beach town. On the cusp of turning 18 and trying to find her" +
                " place in the world, Charlie Watson discovers Bumblebee, battle-scarred and broken." +
                "\n(Source: IMDb)");
        values.put("category", "Action, Adventure, Sci-Fi");
        values.put("rate", 6.9);
        values.put("metascore", 66);
        db.insert("tb_movie", "id", values);

        values.put("id", "6");
        values.put("title", "Creed II");
        values.put("ilustration", R.drawable.poster_creed);
        values.put("year", "2018");
        values.put("synopsis", "Under the tutelage of Rocky Balboa, newly crowned heavyweight " +
                "champion Adonis Creed faces off against Viktor Drago, the son of Ivan Drago." +
                "\n(Source: IMDb)");
        values.put("category", "Drama, Sport");
        values.put("rate", 7.2);
        values.put("metascore", 66);
        db.insert("tb_movie", "id", values);

        values.put("id", "7");
        values.put("title", "Deadpool");
        values.put("ilustration", R.drawable.poster_deadpool);
        values.put("year", "2016");
        values.put("synopsis", "A wisecracking mercenary gets experimented on and becomes immortal " +
                "but ugly, and sets out to track down the man who ruined his looks.\n(Source: IMDb))");
        values.put("category", "Action, Adventure, Comedy ");
        values.put("rate", 8.0);
        values.put("metascore", 65);
        db.insert("tb_movie", "id", values);

        values.put("id", "8");
        values.put("title", "How to Train Your Dragon");
        values.put("ilustration", R.drawable.poster_how_to_train);
        values.put("year", "2018");
        values.put("synopsis", "When Hiccup discovers Toothless isn't the only Night Fury, he must " +
                "seek \"The Hidden World\", a secret Dragon Utopia before a hired tyrant named " +
                "Grimmel finds it first.\n(Source: IMDb)");
        values.put("category", "Animation, Action, Adventure");
        values.put("rate", 7.6);
        values.put("metascore", 71);
        db.insert("tb_movie", "id", values);

        values.put("id", "9");
        values.put("title", "Dragon Ball Broly");
        values.put("ilustration", R.drawable.poster_dragonball);
        values.put("year", "2018");
        values.put("synopsis", "Goku and Vegeta encounter Broly, a Saiyan warrior unlike any " +
                "fighter they've faced before.\n(Source: IMDb)");
        values.put("category", "Animation, Action, Adventure");
        values.put("rate", 8.0);
        values.put("metascore", 59);
        db.insert("tb_movie", "id", values);

        values.put("id", "10");
        values.put("title", "Glass");
        values.put("ilustration", R.drawable.poster_glass);
        values.put("year", "2019");
        values.put("synopsis", "Security guard David Dunn uses his supernatural abilities to track " +
                "Kevin Wendell Crumb, a disturbed man who has twenty-four personalities.\n(Source: IMDb)");
        values.put("category", "Drama, Sci-Fi, Thriller");
        values.put("rate", 6.7);
        values.put("metascore", 43);
        db.insert("tb_movie", "id", values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_movie");
        onCreate(db);
    }
}
