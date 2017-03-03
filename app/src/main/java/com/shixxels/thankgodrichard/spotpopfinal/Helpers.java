package com.shixxels.thankgodrichard.spotpopfinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qb.gson.reflect.TypeToken;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBProvider;
import com.quickblox.auth.model.QBSession;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.core.request.QBRequestUpdateBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by thankgodrichard on 9/9/16.
 */


public class Helpers {
    public static final String APP_ID = "46491";
    public static final String AUTH_KEY = "r4jvmrDkMAugcbV";
    public static final String AUTH_SECRET = "ZF6afrveBeyPVEt";
    public static final String ACCOUNT_KEY = "9HCMWeVpQtrWR3ype5t7";
    public static final String Pref = "com.shixxels.thankgodrichard.spotpopfinal";
    private static final String PREFIX ="Spop" ;
    private static final java.lang.String SUFFIX = "pop";
    public QBUser usel;


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
                Log.i("sesion", "Created");
            }

            @Override
            public void onError(QBResponseException error) {
                // errors
                Log.i("session", "No created");
            }
        });
    }

    // register users
    public void Register(String em, final String ps, final String username, final TextView v, final Context context) {
        // Register new
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.profile_img);
        final File file = bitmapToFile(largeIcon,context);
        final QBUser user = new QBUser(username, ps);
        user.setEmail(em);
        QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                addtoList(user.getId()+"");
                followersGet(user.getId() + "",context);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("info", user);
                intent.putExtra("login", new String[]{username, ps});
                uploadprofilePic(user.getId(),file);
                context.startActivity(intent);


            }

            @Override
            public void onError(QBResponseException error) {
                // error
                v.setText(String.valueOf(error).substring(49));
                Log.e("Err", "error");
            }
        });
    }


    public void startIntent(Context context1, Class aclass) {
        Intent intent = new Intent(context1, aclass);
        context1.startActivity(intent);
    }

    // to sign-in Users
    public void SignIn(final String username, final String ps, final TextView v, final Context context) {
        // Register new user
        final QBUser user = new QBUser(username, ps);

        QBUsers.signIn(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                addtoList(user.getId()+"");
                followersGet(user.getId() + "",context);
                usel = user;
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("info", user);
                intent.putExtra("login", new String[]{username, ps});
                context.startActivity(intent);


            }

            @Override
            public void onError(QBResponseException error) {
                // error
                v.setVisibility(View.VISIBLE);
                v.setText(String.valueOf(error).substring(49));
                Log.e("Err", String.valueOf(error));
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

    public static String passwordValidations(String pass1, String pass2) {
        if (pass1 == null || pass2 == null) {
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

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }

    }


    public void forgotPassword(String email, final TextView msgSet) {
        QBUsers.resetPassword(email, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                msgSet.setText(R.string.after_passwor_request_onsuccess);
            }

            @Override
            public void onError(QBResponseException e) {
                msgSet.setText("Error: " + String.valueOf(e).substring(49));
            }
        });

    }

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {
            Log.d(LoginActivity.TAG, "no internet connection");
            return false;
        } else {
            if (info.isConnected()) {
                Log.d(LoginActivity.TAG, " internet connection available...");
                return true;
            } else {
                Log.d(LoginActivity.TAG, " internet connection");
                return true;
            }

        }
    }

    public QBUser readpreference(Context context) {
        SharedPreferences preference = context.getSharedPreferences(Pref, context.MODE_PRIVATE);
        String jsonString = preference.getString("Spotpop", "");
        Log.i(LoginActivity.TAG, jsonString);
        if (jsonString.length() > 1) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            QBUser user = gson.fromJson(jsonString, QBUser.class);

            return user;
        } else
            return null;
    }

    public boolean searchStr(String search, String what) {
        if (!search.replaceAll(what, "_").equals(search)) {
            return true;
        }
        return false;
    }

    public void fetchSpots(final Context c) {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();

        requestGetBuilder.setPagesLimit(999);
        QBCustomObjects.getObjects("Spots", requestGetBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>() {
                }.getType();
                SharedPreferences preference = c.getSharedPreferences(Pref, c.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                Gson gson = new Gson();
                String c = gson.toJson(customObjects, listOfObjects);
                editor.putString("Spotpop-content", c);
                editor.commit();
                //  ArrayList<QBCustomObject> g = gson.fromJson(c, listOfObjects);
                //  Log.i("srr", String.valueOf(customObjects));
                //  Log.i("srr",c);
                // Log.i("srr", String.valueOf(g));

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.i("sewx", String.valueOf(errors));
            }
        });

    }
    //Todo fetch content for notification
    private void fetch3(QBUser user,final Context c) {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.eq("user_id",user.getId());
        requestGetBuilder.setPagesLimit(999);
        QBCustomObjects.getObjects("notifications", requestGetBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>() {
                }.getType();
                SharedPreferences preference = c.getSharedPreferences(Pref, c.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                Gson gson = new Gson();
                String c = gson.toJson(customObjects, listOfObjects);
                editor.putString("notifications", c);
                editor.commit();
                //  ArrayList<QBCustomObject> g = gson.fromJson(c, listOfObjects);
                //  Log.i("srr", String.valueOf(customObjects));
                //  Log.i("srr",c);
                // Log.i("srr", String.valueOf(g));

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.i("sewx", String.valueOf(errors));
            }
        });

    }
    //Todo fetch content for request create all adapters and models too

    private void fetch4(QBUser user,final Context c) {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.eq("user_id",user.getId());
        requestGetBuilder.setPagesLimit(999);
        QBCustomObjects.getObjects("FriendRequests", requestGetBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>() {
                }.getType();
                SharedPreferences preference = c.getSharedPreferences(Pref, c.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                Gson gson = new Gson();
                String c = gson.toJson(customObjects, listOfObjects);
                editor.putString("frndrequest", c);
                editor.commit();
                //  ArrayList<QBCustomObject> g = gson.fromJson(c, listOfObjects);
                //  Log.i("srr", String.valueOf(customObjects));
                //  Log.i("srr",c);
                // Log.i("srr", String.valueOf(g));

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.i("sewx", String.valueOf(errors));
            }
        });

    }


    //fetch feeds accorfing to people the user is foARing
    //TOdo posible delete
    private void fetchFeeds(final Context c, String[] folowing) {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        for(int i = 0; i< folowing.length; i++){
            requestGetBuilder.in("followingIds",folowing[i]);
        }
        requestGetBuilder.setPagesLimit(999);
        QBCustomObjects.getObjects("Feeds", requestGetBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>() {
                }.getType();
                SharedPreferences preference = c.getSharedPreferences(Pref, c.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                Gson gson = new Gson();
                String c = gson.toJson(customObjects, listOfObjects);
                editor.putString("Feeds", c);
                editor.commit();
                //  ArrayList<QBCustomObject> g = gson.fromJson(c, listOfObjects);
                //  Log.i("srr", String.valueOf(customObjects));
                //  Log.i("srr",c);
                // Log.i("srr", String.valueOf(g));

            }

            @Override
            public void onError(QBResponseException errors) {
                Log.i("sewx", String.valueOf(errors));
            }
        });

    }





    //Create a reacodrs on quickBlox
    //TODO potentail usage or Delete this func
    public void createRecord(Context context, String clasName, String[][] fieldAndData, String[] details, List<String> images) {
        final QBCustomObject object = new QBCustomObject();
        object.setClassName(clasName);
        for (int i = 0; i < fieldAndData.length; i++) {
            for (int j = 0; j < fieldAndData[i].length; j++) {
                object.putString(fieldAndData[i][0], fieldAndData[i][1]);
            }
        }
        object.put("galery", images);
        if (details.length > 1) {
            QBUser user = new QBUser(details[0], details[1]);

            QBUsers.signIn(user, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(final QBUser user, Bundle args) {
                    QBCustomObjects.createObject(object, new QBEntityCallback<QBCustomObject>() {
                        @Override
                        public void onSuccess(QBCustomObject createdObject, Bundle params) {


                        }

                        @Override
                        public void onError(QBResponseException errors) {

                        }
                    });
                }

                @Override
                public void onError(QBResponseException error) {
                    // error
                }
            });
        } else if (details.length == 1) {
            QBUsers.signInUsingSocialProvider(QBProvider.FACEBOOK, details[0], null, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    final String Name = user.getFullName();
                    final String username = user.getLogin();
                    QBCustomObjects.createObject(object, new QBEntityCallback<QBCustomObject>() {
                        @Override
                        public void onSuccess(QBCustomObject createdObject, Bundle params) {
                            updateFeeds(Name, username, createdObject);
                        }

                        @Override
                        public void onError(QBResponseException errors) {

                        }
                    });


                }

                @Override
                public void onError(QBResponseException errors) {

                }
            });

        }

    }


    //Convert Drawbles to string
    public String convertDrawabletoString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    //Convert Strings to Drawable
    public Bitmap converrtStringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }



    //Update feeds on quickblox
    private void updateFeeds(String fullname, String username, QBCustomObject createdObject) {
        final QBCustomObject feedsUpdate = new QBCustomObject();
        feedsUpdate.setClassName("Feeds");
        feedsUpdate.setParentId(createdObject.getCustomObjectId());
        feedsUpdate.putString("feedTitle", fullname);
        feedsUpdate.putString("feedType", "2");
        feedsUpdate.putString("usertag", "@" + username);
        feedsUpdate.put("description", "added a spot " + createdObject.get("ssid") + " near");
        feedsUpdate.put("images", createdObject.getString("galery"));

        QBCustomObjects.createObject(feedsUpdate, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }


    // Pop a spot on quickBlox
    public void popSpot(String parentid, QBUser user, final Context context) {
        final QBCustomObject feedsUpdate = new QBCustomObject();
        feedsUpdate.setClassName("Feeds");
        feedsUpdate.setParentId(parentid);
        feedsUpdate.putString("feedType", "1");
        feedsUpdate.putString("description", "poped a spot");
        feedsUpdate.putString("feedTitle", user.getFullName());
        feedsUpdate.putString("usertag", user.getLogin());
        QBCustomObjects.createObject(feedsUpdate, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {
                toastCenter("You just poped " + qbCustomObject.get("business"), context);
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });


    }


    //Cooment a spot on quickblox
    public void comentSpot(String parentid, QBUser user) {
        final QBCustomObject feedsUpdate = new QBCustomObject();
        feedsUpdate.setClassName("Feeds");
        feedsUpdate.setParentId(parentid);
        feedsUpdate.putString("feedType", "3");
        feedsUpdate.putString("description", "commented on a spot");
        feedsUpdate.putString("feedTitle", user.getFullName());
        feedsUpdate.putString("usertag", user.getLogin());
        QBCustomObjects.createObject(feedsUpdate, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }


    //Stop a spot on quickblox
    public void disLikeSpotTracker(String parentid, QBUser user, final Context context) {
        final QBCustomObject feedsUpdate = new QBCustomObject();
        feedsUpdate.setClassName("DislikeTracker");
        feedsUpdate.setParentId(parentid);
        feedsUpdate.putString("usertag", user.getLogin());
        QBCustomObjects.createObject(feedsUpdate, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });


    }


    //Upload a file to quicblox
    public void uploadPic(File filePhoto) {
        Boolean is = false;
        QBContent.uploadFileTask(filePhoto, is, null, new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                Log.i("pro", "done");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.i("pro", e.getMessage());
            }
        });


    }


    //Convert bitmap to a file
    public File bitmapToFile(Bitmap bmp,Context c) {
        try {
            int size = 5;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
            bmp.compress(Bitmap.CompressFormat.PNG, 80, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = c.openFileOutput("spotpopPropic.png", Context.MODE_WORLD_WRITEABLE);
            fos.write(bArr);
            fos.flush();
            fos.close();

            File mFile = new File(c.getFilesDir().getAbsolutePath(), "spotpopPropic.png");
            return mFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //make a toast at the center of the screen
    public static void toastCenter(String msg, Context context) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //Get the user
    //Todo get followers
    public void followersGet(String userId, final Context c) {
        QBRequestGetBuilder requestGetBuilder = new QBRequestGetBuilder();
        requestGetBuilder.eq("userId", userId);
        QBCustomObjects.getObjects("Followers", requestGetBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {
                Log.i("texr", String.valueOf(customObjects));
                Type listOfObjects = new TypeToken<ArrayList<QBCustomObject>>() {
                }.getType();
                SharedPreferences preference = c.getSharedPreferences(Pref, c.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                Gson gson = new Gson();
                String c = gson.toJson(customObjects, listOfObjects);
                editor.putString("Followers", c);
                editor.commit();
            }

            @Override
            public void onError(QBResponseException errors) {
                Log.i("FolowingGet", errors.getMessage());
            }
        });

    }


    // To create a follower and unfollow on quikblox
    public void createFollowAction(final int userId, Context c, final String action) {
        SharedPreferences preferences = c.getSharedPreferences(Pref, c.MODE_PRIVATE);
        String jsonStr = preferences.getString("login", "");
        Gson gson = new Gson();
        String[] loginDetails = gson.fromJson(jsonStr, String[].class);
        if (loginDetails.length == 1) {
            QBUsers.signInUsingSocialProvider(QBProvider.FACEBOOK, loginDetails[0], null, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    if (action == "follow") {
                        followAction(qbUser, userId);
                        followingAction(qbUser, userId);
                    } else if (action == "unfollow") {
                        unFollowAction(qbUser, userId);
                        unFollowingAction(qbUser, userId);
                    }
                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        } else if (loginDetails.length > 1) {
            QBUser user3 = new QBUser(loginDetails[0], loginDetails[1]);
            QBUsers.signIn(user3, new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    if (action == "follow") {
                        followAction(qbUser, userId);
                        followingAction(qbUser, userId);
                    } else if (action == "unfollow") {
                        unFollowAction(qbUser, userId);
                        unFollowingAction(qbUser, userId);
                    }


                }

                @Override
                public void onError(QBResponseException e) {

                }
            });
        }

    }

  // Folow action
    private void followAction(QBUser user, int whoId) {
        QBCustomObject record = new QBCustomObject();
        record.setClassName("Followers");
        record.setUserId(whoId);
        QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
        updateBuilder.addToSet("followersId", user.getId());
        QBCustomObjects.updateObject(record, updateBuilder, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject object, Bundle params) {
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }


    //following action
    private void followingAction(QBUser user, int whoId) {
        QBCustomObject record = new QBCustomObject();
        record.setClassName("Followers");
        record.setUserId(user.getId());
        QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
        updateBuilder.addToSet("followingids", whoId);
        QBCustomObjects.updateObject(record, updateBuilder, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject object, Bundle params) {
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }


    //unfollow action
    private void unFollowAction(QBUser user, int whoId) {
        QBCustomObject record = new QBCustomObject();
        record.setClassName("Followers");
        record.setUserId(whoId);
        QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
        updateBuilder.pull("followersId", user.getId());
        QBCustomObjects.updateObject(record, updateBuilder, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject object, Bundle params) {
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }


    //Unfollowing action
    private void unFollowingAction(QBUser user, int whoId) {
        QBCustomObject record = new QBCustomObject();
        record.setClassName("Followers");
        record.setUserId(user.getId());
        QBRequestUpdateBuilder updateBuilder = new QBRequestUpdateBuilder();
        updateBuilder.pull("followingIds", whoId);
        QBCustomObjects.updateObject(record, updateBuilder, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject object, Bundle params) {
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }


    //get online status
    public void onlineStatus(int userId, final View v, final Context c) {
        QBUsers.getUser(userId, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                long currentTime = System.currentTimeMillis();
                long userLastRequestAtTime = user.getLastRequestAt().getTime();
                if ((currentTime - userLastRequestAtTime) > 5 * 60 * 1000) {

                }
                else {
                    v.setBackgroundColor(v.getResources().getColor(R.color.grey));                }

            }


            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }


    //Upload prrofile pic of user
    public void uploadprofilePic(final int userId, File file1){
        Boolean fileIsPublic = false;

        QBContent.uploadFileTask(file1, fileIsPublic, null, new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle params) {

                int uploadedFileID = qbFile.getId();
                Log.i("propic", uploadedFileID + "");

                // Connect image to user
                QBUser user = new QBUser();
                user.setId(userId);
                user.setFileId(uploadedFileID);

                QBUsers.updateUser(user, new QBEntityCallback<QBUser>(){
                    @Override
                    public void onSuccess(QBUser user, Bundle args) {

                    }

                    @Override
                    public void onError(QBResponseException errors) {

                    }
                });
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        }, new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {

            }
        });
    }

    //Download Pic from quickBlox
    public void downloadProfilePic(int id, final ImageView v, final Context c, final Activity ac){
        QBUsers.getUser(id, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                Log.i("propix", String.valueOf(user.getFileId()));
                QBContent.downloadFileById(user.getFileId(), new QBEntityCallback<InputStream>(){

                    @Override
                    public void onSuccess(final InputStream inputStream, Bundle params) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                                ac.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        v.setImageBitmap(bmp);
                                    }
                                });
                            }
                        }).start();



                    }

                    @Override
                    public void onError(QBResponseException errors) {
                        Log.i("propic",errors.getMessage());
                        v.setImageResource(R.mipmap.profile_img);
                    }
                }, new QBProgressCallback() {
                    @Override
                    public void onProgressUpdate(int progress) {

                    }
                });


            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });

    }

    //add the user to the following and followers table only on registration either on facebook only
    public void addtoList(String userId){
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.eq("userId", userId);
        QBCustomObjects.getObjects("Followers", requestBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {

            }
            @Override
            public void onError(QBResponseException errors) {
                QBCustomObject object = new QBCustomObject();
                object.setClassName("Followers");
                QBCustomObjects.createObject(object, new QBEntityCallback<QBCustomObject>() {
                    @Override
                    public void onSuccess(QBCustomObject createdObject, Bundle params) {

                    }

                    @Override
                    public void onError(QBResponseException errors) {

                    }
                });
            }
        });


    }





}






