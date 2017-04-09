package com.example.antoine.knowyourgovernment;

import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.graphics.*;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by antoine on 4/6/17.
 */

public class OfficialActivity extends AppCompatActivity {

    //Politician Declaration
    Politician politician;

    //Var int Declaration
    private static final int ADD_REQ = 1;

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

    public void facebook(View v){
        String FACEBOOK_URL = "https://www.facebook.com/";
        String urlToUse;
        PackageManager packageManager = getPackageManager(); try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "lol";
                // "fb://page/" + channels.get("Facebook");
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void twitter(View v) {
        Intent intent = null;
        String name = ""; try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name)); }
        startActivity(intent);
    }

    public void google(View v) {
        String name = "";
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", name);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void youtube(View v) {
        String name = "";
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/" + name)));
        }
    }

    public void detailActivity(View v) {
        Log.d(this.getString(R.string.TAGOA), "detailActivity Start");
        //Intent Declaration and Initialization
        Intent intentOfficialActivity = new Intent(OfficialActivity.this, DetailActivity.class);
        //Get Position of selected Official to put in Intent
        intentOfficialActivity.putExtra("DETAIL", politician);
        //Start Intent with selected Official
        Log.d(this.getString(R.string.TAGOA), "detailActivity Before Intent");
        startActivityForResult(intentOfficialActivity, ADD_REQ);
    }

}
