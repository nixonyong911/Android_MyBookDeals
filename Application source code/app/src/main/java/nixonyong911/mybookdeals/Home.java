package nixonyong911.mybookdeals;

/**
 * Created by nixonyong911 on 14/05/2015.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import nixonyong911.mybookdeals.NavigationTab.Book;
import nixonyong911.mybookdeals.NavigationTab.GetBookListCallback;
import nixonyong911.mybookdeals.NavigationTab.ServerRequests;
import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Home extends Fragment{
    private UserLocalStore userLocalStore;
    //RecyclerView
    private RecyclerView indexRecycler;
    //Initiate LayoutManager
    private RecyclerView.LayoutManager indexLayoutManager;
    private AdapterIndex adapterIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        indexRecycler = (RecyclerView) view.findViewById(R.id.indexRecycler);
        indexRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterIndex = new AdapterIndex(getActivity(), new UserLocalStore(getActivity()));
        indexRecycler.setAdapter(adapterIndex);
        fetchBookData(new Book());
        return view;
    }

    //get book data
    private void fetchBookData(final Book book) {
        final ServerRequests serverRequest = new ServerRequests(getActivity());
        serverRequest.fetchAllBookDataAsyncTask(book, new GetBookListCallback() {
            @Override
            public void done(ArrayList<Book> returnedBook) {
                adapterIndex.setBookList(returnedBook);
            }

        });
    }
}