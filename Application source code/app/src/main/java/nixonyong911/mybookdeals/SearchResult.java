package nixonyong911.mybookdeals;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nixonyong911.mybookdeals.NavigationTab.Book;
import nixonyong911.mybookdeals.NavigationTab.GetBookListCallback;
import nixonyong911.mybookdeals.NavigationTab.ServerRequests;
import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;


public class SearchResult extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private Toolbar toolbar;
    //Navigation bar
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;
    ViewPager drawerPager;
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    //User
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_search_result);

        //Initializing or Attaching
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);
        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);

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
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), userLocalStore); //create adapter
        recyclerView.setLayoutManager(layoutManager); //set layout manager
        recyclerView.setAdapter(recyclerViewAdapter); //set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //set animator

        handleIntent(getIntent());
    }

    //Navigation Bar
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Method to open page
        NavigationAdapter.OnClick(position);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            searchBookData(query);
        }
    }

    //get book data
    private void searchBookData(final String search) {
        final ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.searchBookDataAsyncTask(search, new GetBookListCallback() {
            @Override
            public void done(ArrayList<Book> returnedBook) {
                recyclerViewAdapter.setBookList(returnedBook);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signOut:
                userLocalStore.clearUserData();
                Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainActivity);
                break;
            case R.id.action_refresh:
                recreate();
                break;
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
