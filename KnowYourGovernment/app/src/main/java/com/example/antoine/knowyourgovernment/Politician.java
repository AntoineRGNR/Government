package com.example.antoine.knowyourgovernment;

import android.gesture.GestureOverlayView;
import android.provider.ContactsContract;

import java.io.Serializable;

/**
 * Created by antoine on 4/3/17.
 */

public class Politician implements Serializable {

    //Politician Var Declaration
    private String Office;
    private String Name;
    private String Party;
    private String OfficeAddress;
    private String PhoneNumber;
    private String EmailAddress;
    private String Website;
    private String Facebook;
    private String Twitter;
    private String GooglePlus;
    private String YouTube;

    public Politician(String Office, String Name, String Party, String OfficeAddress, String PhoneNumber,String EmailAddress, String Website, String Facebook, String Twitter, String GooglePlus, String YouTube){
        this.Office = Office;
        this.Name = Name;
        this.Party = Party;
        this.OfficeAddress = OfficeAddress;
        this.PhoneNumber = PhoneNumber;
        this.EmailAddress = EmailAddress;
        this.Website = Website;
        this.Facebook = Facebook;
        this.Twitter = Twitter;
        this.GooglePlus = GooglePlus;
        this.YouTube = YouTube;
    }

    public String getOffice(){
        return Office;
    }

    public void setOffice(String Office){
        this.Office = Office;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public String getParty(){
        return Party;
    }

    public void setParty(String Party){
        this.Party = Party;
    }

    public String getOfficeAddress(){
        return OfficeAddress;
    }

    public void setOfficeAddress(String OfficeAddress){
        this.OfficeAddress = OfficeAddress;
    }

    public String getPhoneNumber(){
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmailAddress(){
        return EmailAddress;
    }

    public void setEmailAddress(String EmailAddress){
        this.EmailAddress = EmailAddress;
    }

    public String getWebsite(){
        return Website;
    }

    public void setWebsite(String Website){
        this.Website = Website;
    }

    public String getFacebook(){
        return Facebook;
    }

    public void setFacebook(String Facebook){
        this.Facebook = Facebook;
    }

    public String getTwitter(){
        return Twitter;
    }

    public void setTwitter(String Twitter){
        this.Twitter = Twitter;
    }

    public String getGooglePlus(){
        return GooglePlus;
    }

    public void setGooglePlus(String GooglePlus){
        this.GooglePlus = GooglePlus;
    }

    public String getYouTube(){
        return YouTube;
    }

    public void setYouTube(String YouTube){
        this.YouTube = YouTube;
    }

    @Override
    public String toString(){
        return Office + Name + Party + OfficeAddress + PhoneNumber + EmailAddress + Website + Facebook + Twitter + GooglePlus + YouTube;
    }



}
