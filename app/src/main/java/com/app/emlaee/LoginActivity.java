package com.app.emlaee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.emlaee.interfaces.ExceptionListener;
import com.app.emlaee.interfaces.ResponseListener;
import com.app.emlaee.utils.Constants;
import com.app.emlaee.utils.Utils;
import com.app.emlaee.webServices.APIThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {


    private TextInputEditText editText_email;
    private TextInputEditText editText_password;
    private TextView TextView_RegisterNow;

    private ImageView mLoginBtn_img;

    private AlertDialog mPDailog;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSharedPrefrences();
        FindViews();

    }

    private void FindViews() {

        editText_email = (TextInputEditText) findViewById(R.id.email_id);
        editText_password = (TextInputEditText) findViewById(R.id.password_id);
        TextView_RegisterNow = (TextView) findViewById(R.id.register_now_id);
        mLoginBtn_img = (ImageView) findViewById(R.id.login_btn_id);

        FindClickListener();

    }

    private void FindClickListener() {
        TextView_RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterNowIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(RegisterNowIntent);
            }
        });


        mLoginBtn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_email = editText_email.getText().toString();
                String str_password = editText_password.getText().toString();

                if (str_email.length() == 0 && str_password.length() == 0) {

                    Toast.makeText(LoginActivity.this, R.string.toast_email_password, Toast.LENGTH_SHORT).show();

                } else if (str_email.length() == 0) {
                    Toast.makeText(LoginActivity.this, R.string.toast_email, Toast.LENGTH_SHORT).show();

                } else if (str_password.length() == 0) {
                    Toast.makeText(LoginActivity.this, R.string.toast_password, Toast.LENGTH_SHORT).show();

                } else {

                    if(!IsInternetConnect()){
                        Toast.makeText(LoginActivity.this, R.string.network_connection_msg, Toast.LENGTH_SHORT).show();
                    }else

                    mPDailog = Utils.CustomProgressbarDialog(LoginActivity.this);
                    mPDailog.show();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("uname", str_email));
                    params.add(new BasicNameValuePair("password", str_password));

                    APIThread background_thread = new APIThread(Constants.LOGIN_URL, Constants.METHOD_POST, params, mResponseListener, mExceptionLitener);
                    background_thread.start();
                }

            }
        });

    }


    //    ******************** HTTP RESPONSE HENDLER LISTENER *************************
    ResponseListener mResponseListener = new ResponseListener() {
        @Override
        public void handleResponse(String res) {
            Log.e("Response ", "" + res);
            try {

                Bundle b = new Bundle();
                Message msg = new Message();
                msg.what = 1;
                msg.setTarget(handler);
                b.putString("RESPONSE", res);
                msg.setData(b);

                handler.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    //    ******************** HTTP EXCEPTION HANDLER LISTENER *************************
    ExceptionListener mExceptionLitener = new ExceptionListener() {
        @Override
        public void handleException(String exception) {
            Log.e("Exception ", "" + exception);
            try {
                Bundle b = new Bundle();
                Message msg = new Message();
                msg.what = 2;
                msg.setTarget(handler);
                b.putString("RESPONSE", exception);
                msg.setData(b);

                handler.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    //  ***************** HANDLER THE MESSAGE FROM INTERFACE CLASSES ***********************
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    Bundle bb = msg.getData();
                    String response = bb.getString("RESPONSE");
                    Log.e("Response", response);

                    //{"success":1,"msg":"User Logged In","data":"2","name":"test","email":"test@test.com"}

                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        String msg_Success = jsonObj.getString("success");
                        if (msg_Success.equals("1")){
                        String msg_User_Name = jsonObj.getString("name");
                        String msg_Email = jsonObj.getString("email");
                        String msg_User_Id = jsonObj.getString("data");


                            if (mEditor != null) {
                                mEditor.putString(Constants.KEY_PRE_USER_EMAIL, msg_Email);
                                mEditor.putString(Constants.KEY_PRE_USER_NAME, msg_User_Name);
                                mEditor.putString(Constants.KEY_PRE_USER_ID, msg_User_Id);
                                mEditor.commit();
                            }

                            Intent intentHome = new Intent(LoginActivity.this, ActivityHome.class);
                            intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentHome);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (mPDailog != null && mPDailog.isShowing()) {
                        mPDailog.dismiss();
                    }


                    break;
                case 2:
                    Bundle bb1 = msg.getData();
                    String response1 = bb1.getString("RESPONSE");

                    Toast.makeText(LoginActivity.this, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();

                    if (mPDailog != null && mPDailog.isShowing()) {
                        mPDailog.dismiss();
                    }

                    break;


            }
        }
    };


    public Boolean IsInternetConnect() {
        ConnectivityManager connMgr1 = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo1 = connMgr1.getActiveNetworkInfo();
        if (networkInfo1 != null && networkInfo1.isConnected()) {
            return true;
        } else
            return false;
    }

    private void setSharedPrefrences() {
        mPref = this.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();
    }




}
