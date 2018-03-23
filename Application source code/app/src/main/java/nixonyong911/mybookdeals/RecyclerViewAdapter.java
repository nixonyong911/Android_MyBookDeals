package nixonyong911.mybookdeals;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import nixonyong911.mybookdeals.NavigationTab.Book;
import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;

/**
 * Created by nixonyong911 on 10/6/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private UserLocalStore userLocalStore;
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Book> bookList = new ArrayList<>();

    public RecyclerViewAdapter(Context context, UserLocalStore userLocalStore){
        layoutInflater = LayoutInflater.from(context);
        this.userLocalStore = userLocalStore;
        this.context = context;
    }

    public void setBookList(ArrayList<Book> bookList){
        this.bookList=bookList;
        notifyItemRangeChanged(0, bookList.size());
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create ViewHolder object
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.book_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int size = bookList.size() - position - 1;
        Book currentBook = bookList.get(size);
        holder.bookTitle.setText(currentBook.getTitle());
        holder.bookAuthor.setText(currentBook.getAuthor());
        holder.bookPrice.setText("RM " + currentBook.getPrice());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView bookPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            bookAuthor = (TextView) itemView.findViewById(R.id.bookAuthor);
            bookPrice = (TextView) itemView.findViewById(R.id.bookPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getPosition();
            int size = bookList.size() - position - 1;
            Book currentBook = bookList.get(size);
            Intent intent = new Intent(context, BookResult.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("user_id", userLocalStore.getUserID());
            intent.putExtra("book_id", currentBook.Book_ID);
            context.startActivity(intent);
        }
    }
}
