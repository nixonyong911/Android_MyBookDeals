package nixonyong911.mybookdeals.NavigationTab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nixonyong911 on 9/14/15.
 */
public class Book implements Parcelable{
    public String Name;
    public String Email;
    public String HP;
    public String ISBN;
    public String Title;
    public String Author;
    public String Publisher;
    public String Edition;
    public String Price;
    public String Category;
    public String Location;
    public String Condition;
    public String Summary;
    public String View;
    public String ContactType;
    public String Bid;
    public String Bidder_ID;
    public String Bidder_Name;
    public String Wishlist;
    public int User_ID;
    public int Book_ID;
    public int counter;




    //Book Details
    public Book(int Book_ID, int User_ID, String Name, String Email, String HP, String ISBN, String Title, String Author, String Publisher,
                String Edition, String Price, String Category, String Location, String Condition, String Summary, String ContactType, String View, String Bid,
                String Bidder_ID, String Bidder_Name, String Wishlist) {
        this.Book_ID = Book_ID;
        this.User_ID = User_ID;
        this.Name = Name;
        this.Email = Email;
        this.HP = HP;
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.Edition = Edition;
        this.Price = Price;
        this.Category = Category;
        this.Location = Location;
        this.Condition = Condition;
        this.Summary = Summary;
        this.ContactType = ContactType;
        this.View = View;
        this.Bid = Bid;
        this.Bidder_ID = Bidder_ID;
        this.Bidder_Name = Bidder_Name;
        this.Wishlist = Wishlist;
    }

    //RecyclerView, Adapter book properties
    public Book(int Book_id, int User_ID, String Title, String Author, String Price){
        this.Book_ID = Book_id;
        this.User_ID = User_ID;
        this.Title = Title;
        this.Author = Author;
        this.Price = Price;
    }


    //Sell Book
    public Book(String ISBN, String Title, String Author, String Publisher, String Edition, String Price, String Category,
                String Location, String Condition, String Summary, String ContactType) {
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.Edition = Edition;
        this.Price = Price;
        this.Category = Category;
        this.Location = Location;
        this.Condition = Condition;
        this.Summary = Summary;
        this.ContactType = ContactType;
    }

    //Advanced Search
    public Book(String ISBN, String Title, String Author, String Publisher, String Edition, String Category, String Condition){
        this.ISBN = ISBN;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.Edition = Edition;
        this.Category = Category;
        this.Condition = Condition;
    }

    //Search
    public Book(String Category){
        this.Category = Category;
    }

    //Index Page
    public Book(){
    }

    protected Book(Parcel in) {
        Name = in.readString();
        Email = in.readString();
        HP = in.readString();
        ISBN = in.readString();
        Title = in.readString();
        Author = in.readString();
        Publisher = in.readString();
        Edition = in.readString();
        Price = in.readString();
        Category = in.readString();
        Location = in.readString();
        Condition = in.readString();
        Summary = in.readString();
        View = in.readString();
        Bid = in.readString();
        ContactType = in.readString();
        User_ID = in.readInt();
        Book_ID = in.readInt();
        counter = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "book_id: " + Book_ID+
                "\nuser_id: " + User_ID+
                "\nname: " + Name+
                "\nemail: " + Email+
                "\nhp: " + HP+
                "\nISBN" + ISBN +
                "\nTitle: " + Title+
                "\nAuthor: " + Author+
                "\nPrice: " +Price +
                "\npublisher: " + Publisher+
                "\nEdition: " + Edition+
                "\nCategory: " + Category+
                "\nLocation: " + Location+
                "\nCondition: " + Condition+
                "\nSummary: " + Summary+
                "\nContactType: " + ContactType;
    }


    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getPrice() {
        return Price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Email);
        parcel.writeString(HP);
        parcel.writeString(ISBN);
        parcel.writeString(Title);
        parcel.writeString(Author);
        parcel.writeString(Publisher);
        parcel.writeString(Edition);
        parcel.writeString(Price);
        parcel.writeString(Category);
        parcel.writeString(Location);
        parcel.writeString(Condition);
        parcel.writeString(Summary);
        parcel.writeString(View);
        parcel.writeString(Bid);
        parcel.writeString(ContactType);
        parcel.writeInt(User_ID);
        parcel.writeInt(Book_ID);
        parcel.writeInt(counter);
    }
}
