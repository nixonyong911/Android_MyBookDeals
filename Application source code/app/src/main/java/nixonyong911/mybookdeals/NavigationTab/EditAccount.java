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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import nixonyong911.mybookdeals.Categories.bookCategories;
import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.R;

public class EditAccount extends ActionBarActivity implements AdapterView.OnItemClickListener,  View.OnClickListener, AdapterView.OnItemSelectedListener{
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
    TextView Name, Email,Password, OldPassword, Phone, Date, Year, School;
    Spinner Month;
    Button Update;
    String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        userLocalStore = new UserLocalStore(this);

        //Initializing or Attaching
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);
        Name = (TextView) findViewById(R.id.EA_Name);
        Email = (TextView) findViewById(R.id.EA_Email);
        Password = (TextView) findViewById(R.id.EA_Password);
        OldPassword = (TextView) findViewById(R.id.EA_OldPassword);
        Phone = (TextView) findViewById(R.id.EA_Phone);
        Date = (TextView) findViewById(R.id.EA_Date);
        Year = (TextView) findViewById(R.id.EA_Year);
        School = (TextView) findViewById(R.id.EA_School);
        Month = (Spinner) findViewById(R.id.EA_Month);
        Update = (Button) findViewById(R.id.EA_Update);

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
        Name.setText(userLocalStore.getLoggedInUser().Name);
        Email.setText(userLocalStore.getLoggedInUser().Email);
        Phone.setText(userLocalStore.getLoggedInUser().HP);
        Date.setText(userLocalStore.getLoggedInUser().Date);
        Year.setText(userLocalStore.getLoggedInUser().Year);
        School.setText(userLocalStore.getLoggedInUser().School);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        Month.setAdapter(adapter); // Apply the adapter to the spinner
        //Listener
        Update.setOnClickListener(this);
        Month.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_norefresh, menu);
        // Associate searchable configuration with the SearchView
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
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.EA_Update:
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String oldPassword = OldPassword.getText().toString();
                String date = Date.getText().toString();
                String year = Year.getText().toString();
                String school = School.getText().toString();
                String handphone = Phone.getText().toString();
                String month = getMonth();

                //take notice
                if(month == "January" && month != getMonth()){
                    month = getMonth();
                }
                if(password.equals("")){
                    password=userLocalStore.getLoggedInUser().Password;
                }

                if(!oldPassword.isEmpty()){
                        User user = new User(userLocalStore.getLoggedInUser().User_ID, name, email,password, date, month, year, school, handphone, oldPassword);
                        updateUser(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Input Previous Password", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void updateUser(final User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.editUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser.Result.equals("Successful Update")) {
                    Intent ViewAccount = new Intent(getApplicationContext(), ViewAccount.class);
                    UpdateDetails(returnedUser);
                    startActivity(ViewAccount);
                    Toast.makeText(getApplicationContext(),returnedUser.Result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),returnedUser.Result,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tempMonth = (TextView) view;
        setMonth(tempMonth.getText().toString());
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void UpdateDetails(User returnedUser) {
        userLocalStore.clearUserData();
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
