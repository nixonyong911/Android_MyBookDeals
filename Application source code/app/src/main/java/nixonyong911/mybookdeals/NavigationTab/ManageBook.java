package nixonyong911.mybookdeals.NavigationTab;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.R;
import nixonyong911.mybookdeals.RecyclerViewAdapter;

public class ManageBook extends ActionBarActivity implements AdapterView.OnItemClickListener {

    //Toolbar
    private Toolbar toolbar;
    //Navigation bar
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private nixonyong911.mybookdeals.NavigationAdapter NavigationAdapter;
    ViewPager drawerPager;
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ManageBookAdapter manageBookAdapter;
    //User
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_categories);
        userLocalStore = new UserLocalStore(this);
        String message = getIntent().getStringExtra("title");

        //Initializing or Attaching
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);
        recyclerView = (RecyclerView) findViewById(R.id.artRecycler);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Set the drawer toggle sync state

        //Nagivation Bar
        NavigationAdapter = new NavigationAdapter(this, userLocalStore); //create navigation adapter
        listView.setAdapter(NavigationAdapter); //setting adapter
        listView.setOnItemClickListener(this); //set click listener
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close); //create drawer listener
        drawerLayout.setDrawerListener(drawerListener); // set drawer listener

        //RecyclerView
        layoutManager = new LinearLayoutManager(this); //create layout manager
        manageBookAdapter = new ManageBookAdapter(getApplicationContext(), userLocalStore); //create adapter
        recyclerView.setLayoutManager(layoutManager); //set layout manager
        recyclerView.setAdapter(manageBookAdapter); //set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //set animator

        //Get Book Data
        fetchBookData(userLocalStore.getLoggedInUser());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    //Navigation Bar
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Method to open page
        NavigationAdapter.OnClick(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    //get book data
    private void fetchBookData(final User user) {
        final ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserBookDataAsyncTask(user, new GetBookListCallback() {
            @Override
            public void done(ArrayList<Book> returnedBook) {
                manageBookAdapter.setBookList(returnedBook);
            }
        });
    }

}
