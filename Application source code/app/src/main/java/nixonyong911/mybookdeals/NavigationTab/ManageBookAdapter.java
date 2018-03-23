package nixonyong911.mybookdeals.NavigationTab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nixonyong911.mybookdeals.BookResult;
import nixonyong911.mybookdeals.MainActivity;
import nixonyong911.mybookdeals.R;

/**
 * Created by nixonyong911 on 10/15/15.
 */
public class ManageBookAdapter extends RecyclerView.Adapter<ManageBookAdapter.ManageBookViewHolder> implements PopupMenu.OnDismissListener{
    private UserLocalStore userLocalStore;
    private LayoutInflater layoutInflater;
    private User user;
    private Context context;
    private ArrayList<Book> bookList = new ArrayList<>();

    public ManageBookAdapter(Context context, UserLocalStore userLocalStore){
        layoutInflater = LayoutInflater.from(context);
        this.userLocalStore = userLocalStore;
        this.context = context;
    }

    public void setBookList(ArrayList<Book> bookList){
        this.bookList=bookList;
        notifyItemRangeChanged(0, bookList.size());
    }


    @Override
    public ManageBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create ViewHolder object
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.manage_book_card_view, parent, false);
        ManageBookViewHolder viewHolder = new ManageBookViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ManageBookViewHolder holder, int position) {
        int size = bookList.size() - position - 1;
        Book currentBook = bookList.get(size);
        holder.bookTitle.setText(currentBook.getTitle());
        holder.bookAuthor.setText(currentBook.getAuthor());
        holder.bookInterest.setText(currentBook.Price + " Interested"); //its actually view, not price
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public void onDismiss(PopupMenu popupMenu) {

    }


    class ManageBookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView bookInterest;
        private ImageButton bookRemove;

        public ManageBookViewHolder(View itemView) {
            super(itemView);
            bookTitle = (TextView) itemView.findViewById(R.id.MB_Title);
            bookAuthor = (TextView) itemView.findViewById(R.id.MB_Author);
            bookInterest = (TextView) itemView.findViewById(R.id.MB_View);
            bookRemove = (ImageButton) itemView.findViewById(R.id.MB_RemoveButton);
            bookRemove.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            boolean flag = true;
            int position = getPosition();
            int size = bookList.size() - position - 1;
            final Book currentBook = bookList.get(size);
            switch (view.getId()) {
                case R.id.MB_RemoveButton:
                    final PopupMenu remove = new PopupMenu(context, view);
                    remove.getMenuInflater().inflate(R.menu.managebookremove,remove.getMenu());
                    remove.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            removeUserBook(currentBook);
                            return true;
                        }
                    });
                    remove.show();
                    flag = false;
                    break;
            }
            if(flag == true){

                Intent intent = new Intent(context, BookResult.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_id", userLocalStore.getUserID());
                intent.putExtra("book_id", currentBook.Book_ID);
                context.startActivity(intent);
            }

        }
        private void removeUserBook(Book book){
            ServerRequests serverRequest = new ServerRequests(context);
            serverRequest.deleteUserBookInBackground(book, new GetBookCallback() {
                @Override
                public void done(Book returnedBook) {
                    Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //return returnedBook;
                }
            });
        }
    }
}
