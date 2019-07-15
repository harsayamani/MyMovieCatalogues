package com.mobile.harsoft.mymoviecatalogues;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.mobile.harsoft.mymoviecatalogues.Adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {

//Submission 1
//    private ListView listView;
//    private ArrayList<Movie> movies;
//    private MovieAdapter adapter;
//    private DbMovies dbMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareView();
        tabLayout();

//Submission 1
//        addItem();
//        parsingItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.languages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Submission 1
//        listView = findViewById(R.id.list_movie);
//        movies = new ArrayList<>();
//        adapter = new MovieAdapter(this);
//        dbMovies = new DbMovies(this);
    }

    private void tabLayout() {
        final ViewPager viewPager = findViewById(R.id.pager);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager, tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//Submission 1
//    private void parsingItem() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Movie movie = new Movie();
//                movie.setTitle(movies.get(position).getTitle());
//                movie.setIlustration(movies.get(position).getIlustration());
//                movie.setCategory(movies.get(position).getCategory());
//                movie.setYear(movies.get(position).getYear());
//                movie.setSynopsis(movies.get(position).getSynopsis());
//                movie.setRate(movies.get(position).getRate());
//                movie.setMetascore(movies.get(position).getMetascore());
//                Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
//                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
//                startActivity(intent);
//            }
//        });
//    }

// Submission 1
//    private void addItem() {
//        SQLiteDatabase readData = dbMovies.getReadableDatabase();
//        @SuppressLint("Recycle") Cursor cursor = readData.rawQuery("select * from tb_movie", null);
//        cursor.moveToFirst();
//        for (int i=0; i<cursor.getCount(); i++){
//            cursor.moveToPosition(i);
//            Movie movie = new Movie();
//            movie.setTitle(cursor.getString(1));
//            movie.setIlustration(cursor.getInt(2));
//            movie.setYear(cursor.getString(3));
//            movie.setCategory(cursor.getString(5));
//            movie.setSynopsis(cursor.getString(4));
//            movie.setRate(cursor.getDouble(6));
//            movie.setMetascore(cursor.getDouble(7));
//            movies.add(movie);
//        }
//        adapter.setMovies(movies);
//        listView.setAdapter(adapter);
//    }
}
