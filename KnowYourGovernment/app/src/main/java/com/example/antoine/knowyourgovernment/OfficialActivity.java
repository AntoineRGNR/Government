package com.example.antoine.knowyourgovernment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.*;

/**
 * Created by antoine on 4/6/17.
 */

public class OfficialActivity extends AppCompatActivity {

    //Politician Declaration
    Politician politician;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log
        Log.d(this.getString(R.string.TAGOA), "onCreate Creation");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        //Getting Note intent
        politician = (Politician)getIntent().getSerializableExtra("POLITICIAN");

        //Text View Initialization
        ((TextView)findViewById(R.id.politicianFunction)).setText(politician.getOffice());
        ((TextView)findViewById(R.id.politicianName)).setText(politician.getName());
        ((TextView)findViewById(R.id.politicianParty)).setText(politician.getParty());
        ((TextView)findViewById(R.id.politicianAddressText)).setText(politician.getOfficeAddress());
        ((TextView)findViewById(R.id.politicianPhoneText)).setText(politician.getPhoneNumber());
        ((TextView)findViewById(R.id.politicianEmailText)).setText(politician.getEmailAddress());
        ((TextView)findViewById(R.id.politicianWebsiteText)).setText(politician.getWebsite());

        //Set Visibility for the Different Social Network Icon
        socialNetworkVisibility();
    }

    public void socialNetworkVisibility(){
        //ImageView Declaration and Initialization
        ImageView facebook = (ImageView)findViewById(R.id.facebookIcon);
        ImageView google = (ImageView)findViewById(R.id.googleIcon);
        ImageView twitter = (ImageView)findViewById(R.id.twitterIcon);
        ImageView youtube = (ImageView)findViewById(R.id.youtubeIcon);

        if(politician.getFacebook().equals(""))
            facebook.setVisibility(View.INVISIBLE);
        else
            facebook.setVisibility(View.VISIBLE);

        if(politician.getGooglePlus().equals(""))
            google.setVisibility(View.INVISIBLE);
        else
            google.setVisibility(View.VISIBLE);

        if(politician.getTwitter().equals(""))
            twitter.setVisibility(View.INVISIBLE);
        else
            twitter.setVisibility(View.VISIBLE);

        if(politician.getYouTube().equals(""))
            youtube.setVisibility(View.INVISIBLE);
        else
            youtube.setVisibility(View.VISIBLE);

    }

}
