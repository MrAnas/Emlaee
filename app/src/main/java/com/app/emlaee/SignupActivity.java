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

public class SignupActivity extends AppCompatActivity {


    private TextInputEditText Ed_FirstName;
    private TextInputEditText Ed_Email;
    private TextInputEditText Ed_Password;
    private TextInputEditText Ed_ConfirmPassword;
    private TextInputEditText Ed_PhoneNo;
    private TextView txtAlreadySignIn;

    private ImageView SignupBtn;

    private AlertDialog mPDailog;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setSharedPrefrences();
        FindViewById();


    }

    private void FindViewById() {
//        Ed_FirstName = (TextInputEditText) findViewById(R.id.first_name_id);
        Ed_Email = (TextInputEditText) findViewById(R.id.email_id);
        Ed_Password = (TextInputEditText) findViewById(R.id.password_id);
        Ed_ConfirmPassword = (TextInputEditText) findViewById(R.id.confirm_password_id);
//        Ed_PhoneNo = (TextInputEditText) findViewById(R.id.phone_no_id);

        SignupBtn = (ImageView) findViewById(R.id.signup_btn_id);
        txtAlreadySignIn = (TextView) findViewById(R.id.txtAlreadySignIn);

        SetClickListners();

    }

    private void SetClickListners() {
        txtAlreadySignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String str_FirstName = Ed_FirstName.getText().toString();
                String str_Email = Ed_Email.getText().toString();
                String str_Password = Ed_Password.getText().toString();
                String str_ConfirmPassword = Ed_ConfirmPassword.getText().toString();
//                String str_PhoneNo = Ed_PhoneNo.getText().toString();

                if (str_Email.length() == 0 ) {

                    Toast.makeText(SignupActivity.this, getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show();

                } else if(str_Password.length() == 0){
                    Toast.makeText(SignupActivity.this, getString(R.string.please_enter_password), Toast.LENGTH_SHORT).show();

                } else if( str_ConfirmPassword.length() == 0){
                    Toast.makeText(SignupActivity.this, getString(R.string.please_re_enter_password), Toast.LENGTH_SHORT).show();
                } else if(!str_ConfirmPassword.equals(str_Password)){
                    Toast.makeText(SignupActivity.this, getString(R.string.reenter_password_not_match), Toast.LENGTH_SHORT).show();
                } else {

                    if (!IsInternetConnect()) {
                        Toast.makeText(SignupActivity.this, R.string.network_connection_msg, Toast.LENGTH_SHORT).show();
                    } else

                        mPDailog = Utils.CustomProgressbarDialog(SignupActivity.this);
                    mPDailog.show();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
//                    params.add(new BasicNameValuePair("fname", str_FirstName));
                    params.add(new BasicNameValuePair("email", str_Email));
                    params.add(new BasicNameValuePair("password", str_Password));
                    params.add(new BasicNameValuePair("retypepassword", str_ConfirmPassword));
//                    params.add(new BasicNameValuePair("phone", str_PhoneNo));

                    APIThread background_thread = new APIThread(Constants.REGISTRATION_URL, Constants.METHOD_POST, params, mResponseListener, mExceptionLitener);
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
                    //{"success":1,"msg":"Done User added!","Name":"test","Email":"test@test.com","ID":2}

                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        String msg_Success = jsonObj.getString("success");
                        String msg_User_Name = jsonObj.getString("Name");
                        String msg_Email = jsonObj.getString("Email");
                        String msg_User_Id = jsonObj.getString("ID");
                        if (msg_Success.equals("1")) {

                            if (mEditor != null) {
                                mEditor.putString(Constants.KEY_PRE_USER_EMAIL, msg_Email);
                                mEditor.putString(Constants.KEY_PRE_USER_NAME, msg_User_Name);
                                mEditor.putString(Constants.KEY_PRE_USER_ID, msg_User_Id);
                                mEditor.commit();
                            }

                            Intent intentHome = new Intent(SignupActivity.this, ActivityHome.class);
                            intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentHome);
                            finish();


                        } else {
                            Toast.makeText(SignupActivity.this, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(SignupActivity.this, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();

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
