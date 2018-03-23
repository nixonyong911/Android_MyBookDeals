package nixonyong911.mybookdeals.NavigationTab;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.R;

public class Register extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    //Navigation bar
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;
    ViewPager drawerPager;
    //Register
    Button register;
    EditText Name, Email, Pass1, Pass2, School, HP, Date, Year;
    Spinner Month;
    String month;
    //User
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_register);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Nagivation Bar
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationAdapter = new NavigationAdapter(this, userLocalStore);
        listView.setAdapter(NavigationAdapter);
        listView.setOnItemClickListener(this);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        // Drawer Listener set to the Drawer Loyout
        drawerLayout.setDrawerListener(drawerListener);
        //Set the drawer toggle sync state
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);

        //Register
        register = (Button) findViewById(R.id.register);
        Name = (EditText) findViewById(R.id.Reg_name);
        Email = (EditText) findViewById(R.id.Reg_email);
        Pass1 = (EditText) findViewById(R.id.Reg_password1);
        Pass2 = (EditText) findViewById(R.id.Reg_password2);
        Date = (EditText) findViewById(R.id.Reg_Date);
        Year = (EditText) findViewById(R.id.Reg_Year);
        School = (EditText) findViewById(R.id.Reg_School);
        HP = (EditText) findViewById(R.id.Reg_Number);

        Month = (Spinner) findViewById(R.id.Reg_Month);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner

        Month.setAdapter(adapter);
        Month.setOnItemSelectedListener(this);
        register.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_norefresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    //Navigation Bar
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Method to open page
        NavigationAdapter.OnClick(position);

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

    //Register
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password1 = Pass1.getText().toString();
                String password2 = Pass2.getText().toString();
                String date = Date.getText().toString();
                String year = Year.getText().toString();
                String school = School.getText().toString();
                String handphone = HP.getText().toString();
                User user = new User(name, email, password1, date, getMonth(), year, school, handphone);

                if (!name.isEmpty() && !email.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && !date.isEmpty() && !month.isEmpty() && !year.isEmpty()
                        && !school.isEmpty() && !handphone.isEmpty()) {
                    if (password1.matches(password2)) {
                        if (handphone.length() == 10 && handphone.startsWith("01")) {
                            if(validate(date, year)) {
                                registerUser(user);
                            } else {
                                Toast.makeText(getApplicationContext(), "D.O.B is incorrect",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Phone details is incomplete", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private void registerUser(final User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

                if(returnedUser.Result.equals("Successful Update")){
                    Intent MainActivity = new Intent(getApplicationContext(), nixonyong911.mybookdeals.MainActivity.class);
                    logUserIn(user);
                    startActivity(MainActivity);
                    Toast.makeText(getApplication(),"Register Successful", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(),returnedUser.Result,Toast.LENGTH_SHORT).show();
            }
        });
    }



    //validator for date
    public boolean validate(String date, String year) {
        int intDate = Integer.parseInt(date);
        int intYear = Integer.parseInt(year);
        if(intDate <=31 && (intYear >= 1900 && intYear <= 2100)) {
            if (intDate == 31 && (month.equals("April") || month.equals("June") || month.equals("September") || month.equals("November"))) {
                return false;
            } else if (month.equals("February")) {
                if (intYear % 4 == 0) {
                    if (intDate == 30 || intDate == 31) {
                        return false;
                    } else
                        return true;
                } else {
                    if (intDate == 29 || intDate == 30 || intDate == 31) {
                        return false;
                    } else
                        return true;

                }
            } else
                return true;
        }else
            return false;
    }

    //Log User In
    private void logUserIn(User returnedUser) {
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