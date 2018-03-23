package nixonyong911.mybookdeals;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nixonyong911.mybookdeals.NavigationTab.Book;
import nixonyong911.mybookdeals.NavigationTab.User;
import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;

/**
 * Created by nixonyong911 on 10/1/15.
 */
public class AdapterIndex extends RecyclerView.Adapter<AdapterIndex.ViewHolderIndex> {
    private UserLocalStore userLocalStore;
    private LayoutInflater layoutInflater;
    private ArrayList<Book> bookList = new ArrayList<>();
    private Context context;

    public AdapterIndex(Context context, UserLocalStore userLocalStore) {
        layoutInflater = LayoutInflater.from(context);
        this.userLocalStore = userLocalStore;
        this.context = context;
    }

    public void setBookList(ArrayList<Book> bookList){
        this.bookList=bookList;
        notifyItemRangeChanged(0, bookList.size());
    }

    @Override
    public ViewHolderIndex onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.book_layout, parent, false);
        ViewHolderIndex viewHolderIndex = new ViewHolderIndex(view);
        return viewHolderIndex;
    }

    @Override
    public void onBindViewHolder(ViewHolderIndex holder, int position) {
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

    public class ViewHolderIndex extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView bookPrice;
        public ViewHolderIndex(View itemView) {
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
            intent.putExtra("user_id", userLocalStore.getUserID());
            intent.putExtra("book_id", currentBook.Book_ID);
            context.startActivity(intent);
        }
    }
}
