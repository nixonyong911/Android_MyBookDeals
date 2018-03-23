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

import java.util.ArrayList;

import nixonyong911.mybookdeals.NavigationAdapter;
import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.R;

public class AdvancedSearch extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    //Navigation bar
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;


    //Sell Book
    EditText ISBN, Title, Author, Publisher, Edition;
    Spinner Category, Condition;
    String category, condition;
    Button Search;
    UserLocalStore userLocalStore;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalStore = new UserLocalStore(this);
        setContentView(R.layout.activity_advanced_search);

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
        ISBN = (EditText) findViewById(R.id.AS_ISBN);
        Title = (EditText) findViewById(R.id.AS_Title);
        Author = (EditText) findViewById(R.id.AS_Author);
        Publisher = (EditText) findViewById(R.id.AS_Publisher);
        Edition = (EditText) findViewById(R.id.AS_Edition);
        //Spinner
        Category = (Spinner) findViewById(R.id.AS_Category);
        Condition = (Spinner) findViewById(R.id.AS_Condition);
        //Sell button
        Search = (Button) findViewById(R.id.AS_Search);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> Category_adapter = ArrayAdapter.createFromResource(this, R.array.book_categories, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> Condition_adapter = ArrayAdapter.createFromResource(this, R.array.book_condition, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        Category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Condition_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Category.setAdapter(Category_adapter);
        Condition.setAdapter(Condition_adapter);
        Category.setOnItemSelectedListener(this);
        Condition.setOnItemSelectedListener(this);
        //sell button Click Listener
        Search.setOnClickListener(this);

        //User
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_norefresh, menu);
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
            case R.id.AS_Category:
                TextView tempCategory = (TextView) view;
                setCategory(tempCategory.getText().toString());
                break;
            case R.id.AS_Condition:
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
            case R.id.AS_Search:
                String isbn = ISBN.getText().toString();
                String title = Title.getText().toString();
                String author = Author.getText().toString();
                String publisher = Publisher.getText().toString();
                String edition = Edition.getText().toString();
                Book book = new Book(isbn, title, author, publisher, edition, getCategory(), getCondition());
                searchBook(book);
                break;
        }
    }

    private void searchBook(final Book book) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.searchSpecificBookDataAsyncTask(book, new GetBookListCallback() {
            @Override
            public void done(ArrayList<Book> returnedBook) {
                Intent AdvancedSearchResult = new Intent(getApplicationContext(), AdvancedSearchResult.class);
                AdvancedSearchResult.putParcelableArrayListExtra("Result", returnedBook);
                startActivity(AdvancedSearchResult);
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

}