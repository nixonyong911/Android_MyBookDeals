package nixonyong911.mybookdeals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import nixonyong911.mybookdeals.NavigationTab.AdvancedSearch;
import nixonyong911.mybookdeals.NavigationTab.ManageBook;
import nixonyong911.mybookdeals.NavigationTab.Register;
import nixonyong911.mybookdeals.NavigationTab.Sell_Book;
import nixonyong911.mybookdeals.NavigationTab.Sign_In;
import nixonyong911.mybookdeals.NavigationTab.UserLocalStore;
import nixonyong911.mybookdeals.NavigationTab.ViewAccount;

/**
 * Created by nixonyong911 on 10/6/15.
 */
public class NavigationAdapter extends BaseAdapter {
    private Context context;
    private UserLocalStore userLocalStore;
    String[] drawerListLoggedIn;
    String[] drawerListLoggedOut;
    int[] imagesLoggedIn = {R.drawable.ic_search, R.drawable.ic_account, R.drawable.ic_manage, R.drawable.ic_sell};
    int[] imagesLoggedOut = {R.drawable.ic_search, R.drawable.ic_register, R.drawable.ic_signin};


    public NavigationAdapter(Context context, UserLocalStore userLocalStore) {
        this.context = context;
        this.userLocalStore = userLocalStore;
        drawerListLoggedIn = context.getResources().getStringArray(R.array.leftDrawerLoggedIn);
        drawerListLoggedOut = context.getResources().getStringArray(R.array.leftDrawerLoggedOut);
    }


    @Override
    public int getCount() {
        if(userLocalStore.authenticate() == true){
            return drawerListLoggedIn.length;
        } else {
            return drawerListLoggedOut.length;
        }
    }

    @Override
    public Object getItem(int position) {
        if(userLocalStore.authenticate() == true){
            return drawerListLoggedIn[position];
        } else {
            return drawerListLoggedOut[position];
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.left_drawer, parent, false);
        TextView leftTextView = (TextView) row.findViewById(R.id.textView);
        ImageView leftImageView = (ImageView) row.findViewById(R.id.imageView);
        //Logged In
        if(userLocalStore.authenticate() == true){
            leftTextView.setText(drawerListLoggedIn[position]);
            leftImageView.setImageResource(imagesLoggedIn[position]);
        } else { //Logged Out
            leftTextView.setText(drawerListLoggedOut[position]);
            leftImageView.setImageResource(imagesLoggedOut[position]);
        }
        return row;
    }

    //based on position open different page
    public void OnClick(int position) {
        //Logged In
        if(userLocalStore.authenticate() == true){
            if (position == 0)
                context.startActivity(new Intent(context, AdvancedSearch.class));
            if (position == 1)
                context.startActivity(new Intent(context, ViewAccount.class));
            if (position == 2)
                context.startActivity(new Intent(context, ManageBook.class));
            if (position == 3)
                context.startActivity(new Intent(context, Sell_Book.class));
        } else { //Logged Out
            if (position == 0)
                context.startActivity(new Intent(context, AdvancedSearch.class));
            if (position == 1)
                context.startActivity(new Intent(context, Sign_In.class));
            if (position == 2)
                context.startActivity(new Intent(context, Register.class));
        }

    }
}