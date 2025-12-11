package com.project.model;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.Serializable ;
/**
 *
 * @author ponlawatchangto
 */
public class UserInfo implements Serializable {
    private String username ;
    private String email ;
    private String tier ;
    private int id ;
    private String phone ;
    
    public UserInfo() {}
    public UserInfo(String username , String tier , int id) {
        this.username = username ;
        this.tier = tier ;
        this.id = id ;
    }
    
    public void setUsername(String username) { this.username = username ; }
    public String getUsername() { return this.username ; }
    
    public void setTier(String tier) { this.tier = tier ; }
    public String getTier() { return this.tier ; }
    
    public void setID(int id) { this.id = id ; }
    public int getID() { return this.id ; }
    
    public String getEmail() {return email ;}
    public void setEmail(String email) { this.email = email ; }
    
    public String getPhone() {return phone ;}
    public void setPhone(String phone) { this.phone = phone ; }
}
