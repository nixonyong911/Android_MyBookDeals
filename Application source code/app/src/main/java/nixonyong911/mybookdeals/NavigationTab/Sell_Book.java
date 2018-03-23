package nixonyong911.mybookdeals.NavigationTab;

import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.R;

public class Sell_Book extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    //Navigation bar
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;


    //Sell Book
    EditText ISBN, Title, Author, Publisher, Edition, Price, Summary;
    Spinner Category, Location, Condition;
    String category, location, condition;
    Button Sell;
    RadioGroup ContactType;
    RadioButton contactType;
    UserLocalStore userLocalStore;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_sell__book);

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


        //Sell Book
        ISBN = (EditText) findViewById(R.id.Sell_ISBN);
        Title = (EditText) findViewById(R.id.Sell_Title);
        Author = (EditText) findViewById(R.id.Sell_Author);
        Publisher = (EditText) findViewById(R.id.Sell_Publisher);
        Edition = (EditText) findViewById(R.id.Sell_Edition);
        Price = (EditText) findViewById(R.id.Sell_Price);
        Summary = (EditText) findViewById(R.id.Sell_Summary);
        //Radio Group
        ContactType = (RadioGroup) findViewById(R.id.Sell_ContactType);
        //Spinner
        Category = (Spinner) findViewById(R.id.Sell_Category);
        Location = (Spinner) findViewById(R.id.Sell_Location);
        Condition = (Spinner) findViewById(R.id.Sell_Condition);
        //Sell button
        Sell = (Button) findViewById(R.id.Sell_Button);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> Category_adapter = ArrayAdapter.createFromResource(this, R.array.book_categories, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> Location_adapter = ArrayAdapter.createFromResource(this, R.array.book_location, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> Condition_adapter = ArrayAdapter.createFromResource(this, R.array.book_condition, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        Category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Condition_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Category.setAdapter(Category_adapter);
        Location.setAdapter(Location_adapter);
        Condition.setAdapter(Condition_adapter);
        Category.setOnItemSelectedListener(this);
        Location.setOnItemSelectedListener(this);
        Condition.setOnItemSelectedListener(this);
        //sell button Click Listener
        Sell.setOnClickListener(this);

        //User
        userLocalStore = new UserLocalStore(this);
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

    //Navigation Bar
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Method to open page
        NavigationAdapter.OnClick(position);

    }

    //Sell Book

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.Sell_Category:
                TextView tempCategory = (TextView) view;
                setCategory(tempCategory.getText().toString());
                break;
            case R.id.Sell_Location:
                TextView tempLocation = (TextView) view;
                setLocation(tempLocation.getText().toString());
                break;
            case R.id.Sell_Condition:
                TextView tempCondition = (TextView) view;
                setCondition(tempCondition.getText().toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Sell_Button:
                int selectedId = ContactType.getCheckedRadioButtonId();
                contactType = (RadioButton) findViewById(selectedId);
                String isbn = ISBN.getText().toString();
                String title = Title.getText().toString();
                String author = Author.getText().toString();
                String publisher = Publisher.getText().toString();
                String edition = Edition.getText().toString();
                String price = Price.getText().toString();
                String summary = Summary.getText().toString();
                String Type = contactType.getText().toString();
                String tempType = "";
                if(Type.equals("E-Mail")){
                    tempType = "E-Mail";
                } else {
                    tempType = "Phone";}

                Book book = new Book(isbn, title, author, publisher, edition, price, getCategory(), getLocation(), getCondition(), summary, tempType);
                if (!isbn.isEmpty() && !title.isEmpty() && !author.isEmpty() && !publisher.isEmpty() && !edition.isEmpty() && !price.isEmpty() && !summary.isEmpty() && !Type.isEmpty()){
                    if(!getCategory().equals("Book Categories") && !getLocation().equals("Location") && !getCondition().equals("Book Condition")){
                        sellBook(book);
                    } else{
                        Toast.makeText(getApplicationContext(), "Please fill in correctly from the list", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void sellBook(Book book) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeBookDataInBackground(book, userLocalStore.getLoggedInUser(), new GetBookCallback() {
            @Override
            public void done(Book returnedBook) {
                Intent mainIntent = new Intent(Sell_Book.this, MainActivity.class);
                startActivity( mainIntent);
                //return returnedBook;
            }
        });
    }
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
}