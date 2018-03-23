package nixonyong911.mybookdeals;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import nixonyong911.mybookdeals.NavigationTab.Book;
import nixonyong911.mybookdeals.NavigationTab.GetBookCallback;
import nixonyong911.mybookdeals.NavigationTab.ServerRequests;
import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;

public class BookResult extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    //Toolbar
    private Toolbar toolbar;
    //Navigation bar
    private DrawerLayout drawerLayout;
    private ListView listView;
    public ActionBarDrawerToggle drawerListener;
    private NavigationAdapter NavigationAdapter;
    ViewPager drawerPager;
    //Book Detail
    TextView title, author, publisher, category, location, summary, name, contactType, contact, readMore, isbn, edition, condition, bidAmount, bidder, biddedAmount, interest;
    String interestClicked;
    ImageButton interestButton, locationButton, categoryButton;
    Button bid, seller;
    private Book book;
    //User
    UserLocalStore userLocalStore;
    int book_id, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);
        userLocalStore = new UserLocalStore(this);

        Bundle bundle = getIntent().getExtras();
        book_id = bundle.getInt("book_id");
        user_id = bundle.getInt("user_id");
        book = new Book();
        String interestClicked = "";
        FetchBookDetails(user_id, book_id);

        //Initializing or Attaching
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        listView = (ListView) findViewById(R.id.leftDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPager = (ViewPager) findViewById(R.id.viewPager);
        title = (TextView) findViewById(R.id.BR_Title);
        author = (TextView) findViewById(R.id.BR_Author);
        publisher = (TextView) findViewById(R.id.BR_Publisher);
        category = (TextView) findViewById(R.id.BR_Category);
        location = (TextView) findViewById(R.id.BR_Location);
        summary = (TextView) findViewById(R.id.BR_Sumamry);
        readMore = (TextView) findViewById(R.id.BR_ReadMore);
        isbn = (TextView) findViewById(R.id.BR_ISBN);
        edition = (TextView) findViewById(R.id.BR_Edition);
        condition = (TextView) findViewById(R.id.BR_Condition);
        interest = (TextView) findViewById(R.id.BR_View);
        bid = (Button) findViewById(R.id.BR_Bid);
        seller = (Button) findViewById(R.id.BR_Seller);
        interestButton = (ImageButton) findViewById(R.id.BR_interestButton);
        categoryButton = (ImageButton) findViewById(R.id.BR_CategoryButton);
        locationButton = (ImageButton) findViewById(R.id.BR_LocationButton);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Set the drawer toggle sync state

        //Nagivation Bar
        NavigationAdapter = new NavigationAdapter(this, userLocalStore); //create navigation adapter
        listView.setAdapter(NavigationAdapter); //setting adapter
        listView.setOnItemClickListener(this); //set click listener
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close); //create drawer listener
        drawerLayout.setDrawerListener(drawerListener); // set drawer listener


        bid.setOnClickListener(this); //set click listener for button
        seller.setOnClickListener(this);
        readMore.setOnClickListener(this);
        interestButton.setOnClickListener(this);
        locationButton.setOnClickListener(this);
        categoryButton.setOnClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
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

    private void FetchBookDetails(int user_id, int book_id){
        final ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchBookDetailsInBackground(user_id,book_id, new GetBookCallback() {
            @Override
            public void done(Book returnedBook) {
                getSupportActionBar().setTitle(returnedBook.Category);
                title.setText(returnedBook.Title);
                author.setText(returnedBook.Author);
                publisher.setText(returnedBook.Publisher);
                category.setText(returnedBook.Category);
                location.setText(returnedBook.Location);
                summary.setText(returnedBook.Summary);
                isbn.setText(returnedBook.ISBN);
                edition.setText(returnedBook.Edition);
                condition.setText(returnedBook.Condition);
                seller.setText("RM " + returnedBook.Price);
                interest.setText(returnedBook.View);
                interestClicked = returnedBook.Wishlist;
                if(interestClicked.equals("false")) {
                    interestButton.setBackground(getDrawable(R.mipmap.interest));
                }
                else {
                    interestButton.setBackground(getDrawable(R.mipmap.interested));
                }
                setBook(returnedBook);
            }
        });
    }



    public void setBook(Book returnedBook){

        Book newBook = new Book(returnedBook.Book_ID, returnedBook.User_ID, returnedBook.Name, returnedBook.Email, returnedBook.HP, returnedBook.ISBN,
                returnedBook.Title, returnedBook.Author, returnedBook.Publisher, returnedBook.Edition, returnedBook.Price, returnedBook.Category, returnedBook.Location,
                returnedBook.Condition, returnedBook.Summary, returnedBook.ContactType, returnedBook.View, returnedBook.Bid, returnedBook.Bidder_ID, returnedBook.Bidder_Name, returnedBook.Wishlist);
        this.book = newBook;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BR_Bid:
                showBid();
                break;
            case R.id.BR_Seller:
                showSellerDetails();
                break;
            case R.id.BR_ReadMore:
                showSummary();
                break;
            case R.id.BR_interestButton:
                modifyInterest();
                break;
            case R.id.BR_CategoryButton:
                finish();
                break;
            case R.id.BR_LocationButton:
                startLocation();
                break;
        }
    }

    private void showBid(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookResult.this);
        View root = getLayoutInflater().inflate(R.layout.bid,null);
        bidAmount = (TextView) root.findViewById(R.id.BRD_Bid);
        bidder = (TextView) root.findViewById(R.id.BRD_bidder);
        biddedAmount = (TextView) root.findViewById(R.id.BRD_bidAmount);
        bidder.setText(book.Bidder_Name);
        biddedAmount.setText(book.Bid);
        dialogBuilder.setView(root);
        dialogBuilder.setTitle("Bid");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookResult.this);
                dialogBuilder.setTitle("Warning");
                dialogBuilder.setMessage("Are you sure?");
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Integer.parseInt(bidAmount.getText().toString()) > Integer.parseInt(book.Bid)) {
                            updateBid(bidAmount.getText().toString());
                            recreate();
                        } else {
                            Toast.makeText(getApplicationContext(),"Amount is too low", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", null);
                dialogBuilder.show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.show();
    }

    private void updateBid(String bid) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeBookBidInBackground(bid, book.Book_ID, book.User_ID, userLocalStore.getLoggedInUser().Name, new GetBookCallback() {
            @Override
            public void done(Book returnedBook) {
                Toast.makeText(getApplicationContext(), "Bid successful", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showSellerDetails() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookResult.this);
        View root = getLayoutInflater().inflate(R.layout.seller_detail,null);
        name = (TextView)root.findViewById(R.id.BRD_Name);
        contact = (TextView) root.findViewById(R.id.BRD_Contact);
        contactType = (TextView) root.findViewById(R.id.BRD_ContactType);
        name.setText(book.Name);
        contactType.setText(book.ContactType);
        if(book.ContactType.equals("E-Mail")){
            contact.setText(book.Email);
        } else{
            contact.setText(book.HP);
        }
        dialogBuilder.setTitle("Seller Detail");
        dialogBuilder.setView(root);
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void showSummary(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookResult.this);
        View root = getLayoutInflater().inflate(R.layout.book_summary,null);
        summary = (TextView) root.findViewById(R.id.BRD_Summary);
        summary.setText(book.Summary);
        dialogBuilder.setTitle("Summary");
        dialogBuilder.setView(root);
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void modifyInterest(){
        ServerRequests serverRequest = new ServerRequests(this);
        if(interestClicked.equals("false")) {
            if(userLocalStore.getUserID() == -1){
                Toast.makeText(getApplicationContext(),"Please sign in to add interest", Toast.LENGTH_SHORT).show();
            } else {
                serverRequest.increaseBookViewInBackground(book, userLocalStore.getLoggedInUser(), new GetBookCallback() {
                    @Override
                    public void done(Book returnedBook) {
                        recreate();
                    }
                });
                interestButton.setBackground(getDrawable(R.mipmap.interested));
                interestClicked = "true";
            }
        }
        else {
            serverRequest.decreaseBookViewInBackground(book, userLocalStore.getLoggedInUser(), new GetBookCallback() {
                @Override
                public void done(Book returnedBook) {
                    recreate();
                }
            });
            interestButton.setBackground(getDrawable(R.mipmap.interest));
            interestClicked = "false";
        }
    }
    public void startLocation(){
        Intent intent = new Intent(getApplicationContext(),BookLocation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("location", book.Location);
        startActivity(intent);
    }
}
