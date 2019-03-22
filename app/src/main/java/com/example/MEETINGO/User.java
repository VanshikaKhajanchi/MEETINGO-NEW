package com.example.MEETINGO;

public class User {
    public String Name;
    public String Email;
    public String Phone;
    public String Userid;

   public User(){//default constructor
         }

   public User(String Name,String Email,String Userid,String Phone){

            this.Name=Name;
            this.Email=Email;
            this.Phone=Phone;
            this.Userid=Userid;
         }
}
