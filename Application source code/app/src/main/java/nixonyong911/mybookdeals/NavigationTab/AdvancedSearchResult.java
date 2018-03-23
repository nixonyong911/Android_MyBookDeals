package nixonyong911.mybookdeals.NavigationTab;

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

import java.util.ArrayList;

import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.R;
import nixonyong911.mybookdeals.RecyclerViewAdapter;

public class AdvancedSearchResult extends ActionBarActivity implements AdapterView.OnItemClickListener{

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
    private RecyclerViewAdapter recyclerViewAdapter;
    //User
    UserLocalStore userLocalStore;
    //Book Result
    private ArrayList<Book> bookArrayList;

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


        //Book
        bookArrayList = getIntent().getParcelableArrayListExtra("Result");
        recyclerViewAdapter.setBookList(bookArrayList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        NavigationAdapter.OnClick(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signOut:
                userLocalStore.clearUserData();
                Intent MainActivity = new Intent(getApplicationContext(), nixonyong911.mybookdeals.MainActivity.class);
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
