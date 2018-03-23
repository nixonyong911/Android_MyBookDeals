package nixonyong911.mybookdeals.NavigationTab;

import java.util.ArrayList;

/**
 * Created by nixonyong911 on 8/13/15.
 */
public class User {
    String Email,Password,Month, School, Date, Year, HP, Result;
    ArrayList<Interest> Interests;
    public String Name = "hi";
    int User_ID = 1;

    //Register
    public User(String name, String email, String password, String date, String month, String year, String school, String hp){
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Date = date;
        this.Month = month;
        this.Year = year;
        this.School = school;
        this.HP = hp;
    }

    //Login
    public User(String email, String password){
        this.Email = email;
        this.Password = password;
    }



    //EditUser
    public User(int user_ID, String name, String email,String password, String date, String month, String year, String school, String hp){
        this.User_ID = user_ID;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Date = date;
        this.Month = month;
        this.Year = year;
        this.School = school;
        this.HP = hp;
    }

    //determine result of register or edit
    public User(int user_ID, String name, String email,String password, String date, String month, String year, String school, String hp, String Result){
        this.User_ID = user_ID;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Date = date;
        this.Month = month;
        this.Year = year;
        this.School = school;
        this.HP = hp;
        this.Result = Result;
    }

    public User(String Result){
        this.Result = Result;
    }
}
