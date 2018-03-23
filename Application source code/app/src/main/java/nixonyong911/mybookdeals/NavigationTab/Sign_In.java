package nixonyong911.mybookdeals.NavigationTab;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;

import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.R;

public class Sign_In extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    //Toolbar
    private Toolbar toolbar;
    //Navigation Drawer
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;
    private String[] leftdrawer;
    ViewPager drawerPager;
    //Sign in
    EditText Email, Password;
    Button login;
    LoginButton facebook_login;
    TextView register_here;
    UserLocalStore userLocalStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_sign__in);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Nagivation Bar
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationAdapter = new NavigationAdapter(this,userLocalStore);
        listView.setAdapter(NavigationAdapter);
        listView.setOnItemClickListener(this);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        //Sign_in
        Email = (EditText) findViewById(R.id.Sign_email);
        Password = (EditText) findViewById(R.id.Sign_password);
        login = (Button) findViewById(R.id.Sign_login);
        register_here = (TextView) findViewById(R.id.register_here);

        login.setOnClickListener(this);
        register_here.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plain, menu);
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method for Navigation Drawer
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Method to open page
        NavigationAdapter.OnClick(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Sign_login:
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                User user = new User(email, password);
                authenticate(user);
                break;
            case R.id.register_here:
                startActivity(new Intent(this, Register.class));
                break;

        }
    }

    //Sign in
    private void authenticate(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }
    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Sign_In.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.clearUserData();
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        Intent MainActivity = new Intent(getApplicationContext(), nixonyong911.mybookdeals.MainActivity.class);
        startActivity(MainActivity);
        Toast.makeText(getApplication(),"Sign In Successful", Toast.LENGTH_SHORT).show();
    }
}
