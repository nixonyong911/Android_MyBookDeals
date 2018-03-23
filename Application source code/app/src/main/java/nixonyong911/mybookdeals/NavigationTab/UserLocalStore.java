package nixonyong911.mybookdeals.NavigationTab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nixonyong911 on 8/13/15.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putInt("user_id", user.User_ID);
        userLocalDatabaseEditor.putString("name", user.Name);
        userLocalDatabaseEditor.putString("email", user.Email);
        userLocalDatabaseEditor.putString("password", user.Password);
        userLocalDatabaseEditor.putString("date", user.Date);
        userLocalDatabaseEditor.putString("month", user.Month);
        userLocalDatabaseEditor.putString("year", user.Year);
        userLocalDatabaseEditor.putString("school", user.School);
        userLocalDatabaseEditor.putString("hp", user.HP);
        userLocalDatabaseEditor.commit();
    }



    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        int user_id =  userLocalDatabase.getInt("user_id", -1);
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");
        String date =  userLocalDatabase.getString("date", "");
        String month =  userLocalDatabase.getString("month", "");
        String year =  userLocalDatabase.getString("year", "");
        String school =  userLocalDatabase.getString("school", "");
        String hp =  userLocalDatabase.getString("hp", "");
        User user = new User(user_id, name, email,password, date, month, year, school, hp);
        return user;
    }

    public int getUserID(){
        return userLocalDatabase.getInt("user_id", -1);
    }

    public boolean authenticate(){
        if(userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        } else {
            return false;
        }
    }
}
