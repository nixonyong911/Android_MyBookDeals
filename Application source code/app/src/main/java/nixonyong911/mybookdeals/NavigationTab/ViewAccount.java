package nixonyong911.mybookdeals.NavigationTab;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.R;

public class ViewAccount extends ActionBarActivity implements AdapterView.OnItemClickListener,  View.OnClickListener{
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
    //User
    UserLocalStore userLocalStore;
    TextView name, email, phone, dob, school, update;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
        userLocalStore = new UserLocalStore(this);

        //Initializing or Attaching
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);
        name = (TextView) findViewById(R.id.VA_Name);
        email = (TextView) findViewById(R.id.VA_Email);
        dob = (TextView) findViewById(R.id.VA_DOB);
        phone = (TextView) findViewById(R.id.VA_Phone);
        school = (TextView) findViewById(R.id.VA_School);
        update = (TextView) findViewById(R.id.VA_Edit);
        signOut = (Button) findViewById(R.id.VA_SignOut);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Set the drawer toggle sync state
        //Nagivation Bar
        NavigationAdapter = new NavigationAdapter(this, userLocalStore); //create navigation adapter
        listView.setAdapter(NavigationAdapter); //setting adapter
        listView.setOnItemClickListener(this); //set click listener
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close); //create drawer listener
        drawerLayout.setDrawerListener(drawerListener); // set drawer listener
        //User Details
        name.setText(userLocalStore.getLoggedInUser().Name);
        email.setText(userLocalStore.getLoggedInUser().Email);
        phone.setText(userLocalStore.getLoggedInUser().HP);
        dob.setText(userLocalStore.getLoggedInUser().Date + " " + userLocalStore.getLoggedInUser().Month + " " + userLocalStore.getLoggedInUser().Year);
        school.setText(userLocalStore.getLoggedInUser().School);

        //Listener
        update.setOnClickListener(this);
        signOut.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.VA_SignOut:
                userLocalStore.clearUserData();
                Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainActivity);
                break;
            case R.id.VA_Edit:
                Intent EditAccount = new Intent(getApplicationContext(), EditAccount.class);
                startActivity(EditAccount);
                break;
        }
    }
}
