package nixonyong911.mybookdeals.NavigationTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://mybooksdeals.netii.net/";
    public ArrayList<Book> bookList = new ArrayList<>();

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }

    public void editUserDataInBackground(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new EditUserDataAsyncTask(user, userCallBack).execute();
    }

    public void fetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
        progressDialog.show();
        new FetchUserDataAsyncTask(user, userCallBack).execute();
    }

    public void storeBookDataInBackground(Book book, User user, GetBookCallback bookCallback) {
        progressDialog.show();
        new StoreBookDataAsyncTask(book, user, bookCallback).execute();
    }

    public void fetchBookDataAsyncTask(Book book, GetBookListCallback bookListCallback) {
        progressDialog.show();
        new FetchBookDataAsyncTask(book, bookListCallback).execute();
    }

    public void fetchAllBookDataAsyncTask(Book book, GetBookListCallback bookListCallback) {
        progressDialog.show();
        new FetchAllBookDataAsyncTask(book, bookListCallback).execute();
    }

    public void searchBookDataAsyncTask(String search, GetBookListCallback bookListCallback) {
        progressDialog.show();
        new SearchBookDataAsyncTask(search, bookListCallback).execute();
    }

    //Advanced Search
    public void searchSpecificBookDataAsyncTask(Book book, GetBookListCallback bookListCallback) {
        progressDialog.show();
        new SearchSpecificBookDataAsyncTask(book, bookListCallback).execute();
    }

    //Bidding
    public void storeBookBidInBackground(String bid, int book_id, int bidder_id, String Name, GetBookCallback bookCallback) {
        progressDialog.show();
        new StoreBookBidAsyncTask(bid, book_id, bidder_id, Name, bookCallback).execute();
    }

    public void fetchBookLocationAsyncTask(Book book, GetBookListCallback bookListCallback) {
        progressDialog.show();
        new FetchBookLocationAsyncTask(book, bookListCallback).execute();
    }

    public void fetchUserBookDataAsyncTask(User user, GetBookListCallback bookListCallback) {
        progressDialog.show();
        new FetchUserBookDataAsyncTask(user, bookListCallback).execute();
    }

    public void deleteUserBookInBackground(Book book, GetBookCallback bookCallback) {
        new DeleteUserBookAsyncTask(book, bookCallback).execute();
    }

    public void fetchBookDetailsInBackground(int user_id, int book_id, GetBookCallback bookCallback) {
        progressDialog.show();
        new FetchBookDetailsAsyncTask(user_id, book_id, bookCallback).execute();
    }

    public void increaseBookViewInBackground(Book book, User user, GetBookCallback bookCallback) {
        progressDialog.show();
        new increaseBookViewAsyncTask(book, user, bookCallback).execute();
    }

    public void decreaseBookViewInBackground(Book book, User user, GetBookCallback bookCallback) {
        progressDialog.show();
        new decreaseBookViewAsyncTask(book, user, bookCallback).execute();
    }

    /**
     * parameter sent to task upon execution progress published during
     * background computation result of the background computation
     */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.Name));
            dataToSend.add(new BasicNameValuePair("email", user.Email));
            dataToSend.add(new BasicNameValuePair("password", user.Password));
            dataToSend.add(new BasicNameValuePair("date", user.Date));
            dataToSend.add(new BasicNameValuePair("month", user.Month));
            dataToSend.add(new BasicNameValuePair("year", user.Year));
            dataToSend.add(new BasicNameValuePair("school", user.School));
            dataToSend.add(new BasicNameValuePair("hp", user.HP));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jObject = new JSONObject(result);
                String message = jObject.getString("message" + "");
                String name = jObject.getString("name" + "");
                String email = jObject.getString("email" + "");
                String password = jObject.getString("password" + "");
                String date = jObject.getString("date" + "");
                String month = jObject.getString("month" + "");
                String year = jObject.getString("year" + "");
                String school = jObject.getString("school" + "");
                String hp = jObject.getString("hp" + "");
                int user_id = jObject.getInt("user_id" + "");
                returnedUser = new User(user_id, name, email,password, date, month, year, school, hp,message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.dismiss();
            super.onPostExecute(user);
            userCallBack.done(user);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class EditUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public EditUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", user.User_ID + ""));
            dataToSend.add(new BasicNameValuePair("name", user.Name));
            dataToSend.add(new BasicNameValuePair("email", user.Email));
            dataToSend.add(new BasicNameValuePair("password", user.Password));
            dataToSend.add(new BasicNameValuePair("oldpassword", user.Result));
            dataToSend.add(new BasicNameValuePair("date", user.Date));
            dataToSend.add(new BasicNameValuePair("month", user.Month));
            dataToSend.add(new BasicNameValuePair("year", user.Year));
            dataToSend.add(new BasicNameValuePair("school", user.School));
            dataToSend.add(new BasicNameValuePair("hp", user.HP));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "EditUserData.php");
            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jObject = new JSONObject(result);
                String message = jObject.getString("message");
                String name = jObject.getString("name" + "");
                String email = jObject.getString("email" + "");
                String password = jObject.getString("password" + "");
                String date = jObject.getString("date" + "");
                String month = jObject.getString("month" + "");
                String year = jObject.getString("year" + "");
                String school = jObject.getString("school" + "");
                String hp = jObject.getString("hp" + "");
                int user_id = jObject.getInt("user_id" + "");
                returnedUser = new User(user_id, name, email,password, date, month, year, school, hp,message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.dismiss();
            super.onPostExecute(user);
            userCallBack.done(user);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallback userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", user.Email));
            dataToSend.add(new BasicNameValuePair("password", user.Password));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");
            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jObject = new JSONObject(result);
                String name = jObject.getString("name");
                String email = jObject.getString("email");
                String password = jObject.getString("password");
                String date = jObject.getString("date");
                String month = jObject.getString("month");
                String year = jObject.getString("year");
                String school = jObject.getString("school");
                String hp = jObject.getString("hp");
                int user_id = Integer.parseInt(jObject.getString("user_id"));
                returnedUser = new User(user_id, name, email,password, date, month, year, school, hp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedUser;
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.dismiss();
            super.onPostExecute(user);
            userCallBack.done(user);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class StoreBookDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Book book;
        User user;
        GetBookCallback bookCallback;

        public StoreBookDataAsyncTask(Book book, User user, GetBookCallback bookCallback) {
            this.book = book;
            this.user = user;
            this.bookCallback = bookCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", Integer.toString(user.User_ID)));
            dataToSend.add(new BasicNameValuePair("name", user.Name));
            dataToSend.add(new BasicNameValuePair("email", user.Email));
            dataToSend.add(new BasicNameValuePair("hp", user.HP));
            dataToSend.add(new BasicNameValuePair("isbn", book.ISBN));
            dataToSend.add(new BasicNameValuePair("title", book.Title));
            dataToSend.add(new BasicNameValuePair("author", book.Author));
            dataToSend.add(new BasicNameValuePair("publisher", book.Publisher));
            dataToSend.add(new BasicNameValuePair("edition", book.Edition));
            dataToSend.add(new BasicNameValuePair("price", book.Price));
            dataToSend.add(new BasicNameValuePair("category", book.Category));
            dataToSend.add(new BasicNameValuePair("location", book.Location));
            dataToSend.add(new BasicNameValuePair("book_condition", book.Condition));
            dataToSend.add(new BasicNameValuePair("summary", book.Summary));
            dataToSend.add(new BasicNameValuePair("contact_type", book.ContactType));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "SellBook.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            bookCallback.done(null);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class increaseBookViewAsyncTask extends AsyncTask<Void, Void, Void> {
        Book book;
        User user;
        GetBookCallback bookCallback;

        public increaseBookViewAsyncTask(Book book, User user, GetBookCallback bookCallback) {
            this.book = book;
            this.user = user;
            this.bookCallback = bookCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", Integer.toString(user.User_ID)));
            dataToSend.add(new BasicNameValuePair("book_id", Integer.toString(book.Book_ID)));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "IncView.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            bookCallback.done(null);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class decreaseBookViewAsyncTask extends AsyncTask<Void, Void, Void> {
        Book book;
        User user;
        GetBookCallback bookCallback;

        public decreaseBookViewAsyncTask(Book book, User user, GetBookCallback bookCallback) {
            this.book = book;
            this.user = user;
            this.bookCallback = bookCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", Integer.toString(user.User_ID)));
            dataToSend.add(new BasicNameValuePair("book_id", Integer.toString(book.Book_ID)));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "DecView.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            bookCallback.done(null);
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class FetchBookDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        Book book;
        GetBookListCallback bookListCallback;


        public FetchBookDataAsyncTask(Book book, GetBookListCallback bookListCallback) {
            this.book = book;
            this.bookListCallback = bookListCallback;
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("category", book.Category));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchBookData.php");

            Book returnedBook = null;

            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONArray jsonArray = new JSONArray(result); //to get the book array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentBook = jsonArray.getJSONObject(i);
                    int book_id = currentBook.getInt("book_id");
                    int user_id = currentBook.getInt("user_id");
                    String title = currentBook.getString("title");
                    String author = currentBook.getString("author");
                    String price = currentBook.getString("price");
                    returnedBook = new Book(book_id, user_id, title, author, price);
                    bookList.add(returnedBook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> returnedBook) {
            progressDialog.dismiss();
            super.onPostExecute(returnedBook);
            bookListCallback.done(returnedBook);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }


    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class FetchUserBookDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        User user;
        GetBookListCallback bookListCallback;


        public FetchUserBookDataAsyncTask(User user, GetBookListCallback bookListCallback) {
            this.user = user;
            this.bookListCallback = bookListCallback;
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", user.User_ID + ""));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserBookData.php");

            Book returnedBook = null;

            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONArray jsonArray = new JSONArray(result); //to get the book array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentBook = jsonArray.getJSONObject(i);
                    int book_id = currentBook.getInt("book_id");
                    int user_id = currentBook.getInt("user_id");
                    String title = currentBook.getString("title");
                    String author = currentBook.getString("author");
                    String view = currentBook.getString("view");
                    returnedBook = new Book(book_id,user_id, title, author, view);
                    bookList.add(returnedBook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> returnedBook) {
            progressDialog.dismiss();
            super.onPostExecute(returnedBook);
            bookListCallback.done(returnedBook);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }


    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class FetchAllBookDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        Book book;
        GetBookListCallback bookListCallback;

        public FetchAllBookDataAsyncTask(Book book, GetBookListCallback bookListCallback) {
            this.book = book;
            this.bookListCallback = bookListCallback;
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("category", book.Category));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchAllBookData.php");

            Book returnedBook = null;

            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONArray jsonArray = new JSONArray(result); //to get the book array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentBook = jsonArray.getJSONObject(i);
                    int book_id = currentBook.getInt("book_id");
                    int user_id = currentBook.getInt("user_id");
                    String title = currentBook.getString("title");
                    String author = currentBook.getString("author");
                    String price = currentBook.getString("price");
                    returnedBook = new Book(book_id, user_id, title, author, price);
                    bookList.add(returnedBook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> returnedBook) {
            progressDialog.dismiss();
            super.onPostExecute(returnedBook);
            bookListCallback.done(returnedBook);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class SearchBookDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        String search;
        GetBookListCallback bookListCallback;


        public SearchBookDataAsyncTask(String search, GetBookListCallback bookListCallback) {
            this.search = search;
            this.bookListCallback = bookListCallback;
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("search", search));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "SearchBookData.php");

            Book returnedBook = null;

            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONArray jsonArray = new JSONArray(result); //to get the book array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentBook = jsonArray.getJSONObject(i);
                    int book_id = currentBook.getInt("book_id");
                    int user_id = currentBook.getInt("user_id");
                    String title = currentBook.getString("title");
                    String author = currentBook.getString("author");
                    String price = currentBook.getString("price");
                    returnedBook = new Book(book_id,user_id,title, author, price);
                    //returnedBook = new Book(book_id, user_id, name, email, hp, isbn, title, author, publisher, edition, price, category, location, book_condition, summary, contact_type, view, bid, bidder_id);
                    bookList.add(returnedBook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> returnedBook) {
            progressDialog.dismiss();
            super.onPostExecute(returnedBook);
            bookListCallback.done(returnedBook);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Advanced Search
    public class SearchSpecificBookDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        Book book;
        GetBookListCallback bookListCallback;


        public SearchSpecificBookDataAsyncTask(Book book, GetBookListCallback bookListCallback) {
            this.book = book;
            this.bookListCallback = bookListCallback;
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "AdvancedSearch.php");

            Book returnedBook = null;

            try {

                post.setEntity(new UrlEncodedFormEntity(checkData(book)));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONArray jsonArray = new JSONArray(result); //to get the book array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentBook = jsonArray.getJSONObject(i);
                    int book_id = currentBook.getInt("book_id");
                    int user_id = currentBook.getInt("user_id");
                    String title = currentBook.getString("title");
                    String author = currentBook.getString("author");
                    String price = currentBook.getString("price");
                    returnedBook = new Book(book_id, user_id, title, author, price);
                    bookList.add(returnedBook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> returnedBook) {
            progressDialog.dismiss();
            super.onPostExecute(returnedBook);
            bookListCallback.done(returnedBook);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        private ArrayList<NameValuePair> checkData(Book book){
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            int counter = 1;
            if(!book.ISBN.equals("")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.ISBN));
                dataToSend.add(new BasicNameValuePair("key" + counter, "isbn"));
                counter++;
            }
            if(!book.Title.equals("")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.Title));
                dataToSend.add(new BasicNameValuePair("key" + counter, "title"));
                counter++;
            }
            if(!book.Author.equals("")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.Author));
                dataToSend.add(new BasicNameValuePair("key" + counter, "author"));
                counter++;
            }
            if(!book.Publisher.equals("")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.Publisher));
                dataToSend.add(new BasicNameValuePair("key" + counter, "publisher"));
                counter++;
            }
            if(!book.Edition.equals("")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.Edition));
                dataToSend.add(new BasicNameValuePair("key" + counter, "edition"));
                counter++;
            }
            if(!book.Category.equals("Book Categories")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.Category));
                dataToSend.add(new BasicNameValuePair("key" + counter, "category"));
                counter++;
            }
            if(!book.Condition.equals("Book Condition")) {
                dataToSend.add(new BasicNameValuePair("search" + counter, book.Condition));
                dataToSend.add(new BasicNameValuePair("key" + counter, "book_condition"));
                counter++;
            }
            return dataToSend;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class StoreBookBidAsyncTask extends AsyncTask<Void, Void, Void> {
        String bid, name;
        int book_id;
        int bidder_id;
        GetBookCallback bookCallback;

        public StoreBookBidAsyncTask(String bid,int book_id, int bidder_id, String name, GetBookCallback bookCallback) {
            this.bid = bid;
            this.book_id = book_id;
            this.bidder_id = bidder_id;
            this.name = name;
            this.bookCallback = bookCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("bid", bid));
            dataToSend.add(new BasicNameValuePair("book_id", book_id + ""));
            dataToSend.add(new BasicNameValuePair("bidder_id", bidder_id + ""));
            dataToSend.add(new BasicNameValuePair("bidder_name", name + ""));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "StoreBidData.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            bookCallback.done(null);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class FetchBookLocationAsyncTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        Book book;
        GetBookListCallback bookListCallback;


        public FetchBookLocationAsyncTask(Book book, GetBookListCallback bookListCallback) {
            this.book = book;
            this.bookListCallback = bookListCallback;
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("location", book.Category));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchBookLocationData.php");

            Book returnedBook = null;

            try {

                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONArray jsonArray = new JSONArray(result); //to get the book array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentBook = jsonArray.getJSONObject(i);
                    int book_id = currentBook.getInt("book_id");
                    int user_id = currentBook.getInt("user_id");
                    String title = currentBook.getString("title");
                    String author = currentBook.getString("author");
                    String price = currentBook.getString("price");
                    returnedBook = new Book(book_id,user_id,title,author,price);
                    //returnedBook = new Book(book_id, user_id, name, email, hp, isbn, title, author, publisher, edition, price, category, location, book_condition, summary, contact_type, view, bid, bidder_id);
                    bookList.add(returnedBook);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> returnedBook) {
            progressDialog.dismiss();
            super.onPostExecute(returnedBook);
            bookListCallback.done(returnedBook);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class DeleteUserBookAsyncTask extends AsyncTask<Void, Void, Void> {
        Book book;
        GetBookCallback bookCallback;

        public DeleteUserBookAsyncTask(Book book, GetBookCallback bookCallback) {
            this.book = book;
            this.bookCallback = bookCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("book_id", book.Book_ID + ""));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "RemoveUserBookData.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            super.onPostExecute(result);
            bookCallback.done(null);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class FetchBookDetailsAsyncTask extends AsyncTask<Void, Void, Book> {
        int user_id, book_id;
        GetBookCallback bookCallback;

        public FetchBookDetailsAsyncTask(int user_id, int book_id, GetBookCallback bookCallback) {
            this.user_id = user_id;
            this.book_id = book_id;
            this.bookCallback = bookCallback;
        }

        @Override
        protected Book doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("user_id", user_id + ""));
            dataToSend.add(new BasicNameValuePair("book_id", book_id + ""));
            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchBookDetail.php");
            Book returnedBook = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jObject = new JSONObject(result);
                int book_id = jObject.getInt("book_id");
                int user_id = jObject.getInt("user_id");
                String name = jObject.getString("name");
                String hp = jObject.getString("hp");
                String email = jObject.getString("email");
                String isbn = jObject.getString("isbn");
                String title = jObject.getString("title");
                String author = jObject.getString("author");
                String publisher = jObject.getString("publisher");
                String edition = jObject.getString("edition");
                String price = jObject.getString("price");
                String category = jObject.getString("category");
                String location = jObject.getString("location");
                String book_condition = jObject.getString("book_condition");
                String summary = jObject.getString("summary");
                String contact_type = jObject.getString("contact_type");
                String view = jObject.getString("view");
                String bid = jObject.getString("bid");
                String bidder_id = jObject.getString("bidder_id");
                String bidder_name = jObject.getString("bidder_name");
                String wishlist = jObject.getString("wishlist");
                returnedBook = new Book(book_id, user_id, name, email, hp, isbn, title, author, publisher, edition, price, category, location, book_condition, summary, contact_type, view, bid, bidder_id, bidder_name, wishlist);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedBook;
        }

        @Override
        protected void onPostExecute(Book book) {
            progressDialog.dismiss();
            super.onPostExecute(book);
            bookCallback.done(book);
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
    }
}
