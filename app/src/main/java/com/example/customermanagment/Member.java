package com.example.customermanagment;

public class Member {
   String Name;
     String Email;
    String Address;
    String Phone;
     String Username;
      String Password;

    public  Member(){

    }
    public Member(String Name,String Email,String Address,String Phone ,String Username,String Password) {
        this.Name=Name;
        this.Email=Email;
        this.Address=Address;
        this.Phone=Phone;
        this.Username=Username;
        this.Password=Password;




    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
