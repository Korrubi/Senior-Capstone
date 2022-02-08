package com.jjcc.dishdiscovery.activities;

import android.content.Context;
import com.amazonaws.regions.Regions;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;



public class CognitoSettings {
    private String userPoolID = "us-west-2_WN61x5obI";
    private String clientID = "67a97t873k88ssooo2fs8ae3hs";
    private String clientSecret = "1acf4d2srkp1srk4bb74vld2pva74a8pve5egqb4misi8gg3omjj";
    private Regions cognitoRegion = Regions.US_WEST_2;
    private Context context;


    //User pool tokens
    //Establish Connection


    public CognitoSettings(Context context){
        this.context = context;
    }

    public String getUserPoolID(){
        return userPoolID;
    }

    public String getClientID(){
        return clientID;
    }

    public String getClientSecret(){
        return clientSecret;
    }

    public Regions getCognitoRegion(){
        return cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolID, clientID,
                clientSecret, cognitoRegion);
    }
}
