package com.shixxels.thankgodrichard.spotpopfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.regex.Pattern;

/**
 * Created by thankgodrichard on 9/9/16.
 */


public class Helpers {
    public static final String APP_ID = "46491";
    public static final String AUTH_KEY = "r4jvmrDkMAugcbV";
    public static final String AUTH_SECRET = "ZF6afrveBeyPVEt";
    public static final String ACCOUNT_KEY = "9HCMWeVpQtrWR3ype5t7";




    private static Helpers ourInstance = new Helpers();

    public static Helpers getInstance() {
        return ourInstance;
    }

    private Helpers() {
    }
    public void initQuickBlox(Context context) {
        //initializing quickblox with credentials
        QBSettings.getInstance().init(context, APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        creatSession();
    }

    private void creatSession() {
        QBAuth.createSession(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // success
                Log.i("sesion" ,"Created");
            }

            @Override
            public void onError(QBResponseException error) {
                // errors
                Log.i("session","No created");
            }
        });
    }


    public void Register(String em, String ps , final Context context) {
        // Register new user
        final QBUser user = new QBUser(em, ps);

        QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success
                Log.i("Tag","msg");
                //TODO MOVE TO THE AVTIVITY AFTER SIGNUP
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);

            }

            @Override
            public void onError(QBResponseException error) {
                // error
                Log.e("Err","error");
                //TODO SHOW ERROR IF SIGN UP ERROR
            }
        });
    }

    // PASS WRD VALIDATIONS

    interface PasswordRule {
        boolean passRule(String password);
        String failMessage();
    }
    abstract static class BaseRule implements PasswordRule {
        private final String message;
        BaseRule(String message) {
            this.message = message;
        }

        public String failMessage() {
            return message;
        }
    }
    private static final PasswordRule[] RULES = {
            new BaseRule("Password is too short. Needs to have 6 characters") {

                @Override
                public boolean passRule(String password) {
                    return password.length() >= 6;
                }
            },

            new BaseRule("Password needs an upper case") {

                private final Pattern ucletter = Pattern.compile(".*[\\p{Lu}].*");
                @Override
                public boolean passRule(String password) {
                    return ucletter.matcher(password).matches();
                }
            },

            /// .... more rules.
    };

    public static String passwordValidations(String pass1, String pass2){
        if(pass1 == null || pass2 == null) {
            //logger.info("Passwords = null");
            return "Passwords Null <br>";
        }

        if (pass1.isEmpty() || pass2.isEmpty()) {
            return "Empty fields <br>";
        }

        if (!pass1.equals(pass2)) {
            //logger.info(pass1 + " != " + pass2);
            return "Passwords don't match<br>";
        }

        StringBuilder retVal = new StringBuilder();

        boolean pass = true;
        for (PasswordRule rule : RULES) {
            if (!rule.passRule(pass1)) {
                // logger.info(pass + "<--- " + rule.failMessage());
                retVal.append(rule.failMessage()).append(" <br>");
                pass = false;
            }
        }

        return pass ? "success" : retVal.toString();
    }

    public static class Pref {
        private static final String USER_ID = "userID";
        private static final String PREF_NAME = "SpopPOP";

        private static Pref instance;
        private SharedPreferences preferences;
        private SharedPreferences.Editor editor;

        private Pref(Context context) {
            preferences = context.getSharedPreferences(PREF_NAME, 0);
            editor = preferences.edit();
        }

        public static Pref getInstance(Context context) {
            return instance == null ? new Pref(context) : instance;
        }

        public void setUserID(String userID) {
            editor.putString(USER_ID, userID).commit();
        }

        public String getUserID() {
            return preferences.getString(USER_ID, "");
        }
    }


}
