package nixonyong911.mybookdeals;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;
//Toolbar


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    //Toolbar
    private Toolbar toolbar;
    //Navigation Drawer
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;
    ViewPager drawerPager;
    //Slider Bar
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Home", "Categories"};
    int Numboftabs = 2;
    //Categories
    RecyclerView categories;
    //Sign in
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLocalStore = new UserLocalStore(this);

        //Initializing or Attaching
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);
        pager = (ViewPager) findViewById(R.id.viewPager);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Nagivation Bar
        NavigationAdapter = new NavigationAdapter(this, userLocalStore);
        listView.setAdapter(NavigationAdapter);
        listView.setOnItemClickListener(this);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerListener.syncState();
        drawerLayout.setDrawerListener(drawerListener);;

        //Sliding Bar
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs); // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        pager.setAdapter(adapter);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {  // Setting Custom Color for the Scroll bar indicator of the Tab View
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);// Setting the ViewPager For the SlidingTabsLayout
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
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
                break;
        }
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method for Navigation Drawer
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Method to open page
        NavigationAdapter.OnClick(position);
    }
}

