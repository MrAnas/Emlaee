package com.app.emlaee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.emlaee.interfaces.ExceptionListener;
import com.app.emlaee.interfaces.ResponseListener;
import com.app.emlaee.modal.LevelItemModal;
import com.app.emlaee.utils.Constants;
import com.app.emlaee.utils.Utils;
import com.app.emlaee.views.PieGraph;
import com.app.emlaee.webServices.APIThread;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends AppCompatActivity {

    public static final String TAG = "ResultActivity";
    public Context context = ResultActivity.this;
    TextView txtTestAgain, txtMainMenu;
    String testname, quiz_id, TestTimeLimit, strUserID;
    int total_question, correct_answers, wrong_answers, skipped;
    LevelItemModal levelItemModal;
    private AlertDialog mPDailog;
    View viewTrue,viewFalse;
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setSharedPrefrences();

        if (getIntent().getExtras() != null) {
            levelItemModal = (LevelItemModal) getIntent().getSerializableExtra("MODAL");
            total_question = getIntent().getIntExtra("total_question", 0);
            correct_answers = getIntent().getIntExtra("correct_answers", 0);
            wrong_answers = getIntent().getIntExtra("wrong_answers", 0);
            skipped = getIntent().getIntExtra("skipped", 0);
        }
        strUserID = mPref.getString(Constants.KEY_PRE_USER_ID, "");
        quiz_id = levelItemModal.getId();

        /****Set Widgets****/
        setWidgetsIDs();

       /******Set Pie Chart*******/
        setPieChart();

        /*Set Click Listner*/
        setCLickListner();


        /*****Execute Questions API*****/
        if (!IsInternetConnect()) {
            Toast.makeText(context, R.string.network_connection_msg, Toast.LENGTH_SHORT).show();
        } else {
            /******execute Categories Test******/
            executeLevelTestsAPI();
        }
    }

    private void setPieChart() {
        PieGraph pie = (PieGraph) findViewById(R.id.pie_chart);
        float[] data = {correct_answers,wrong_answers};
        View[] viewsArray = {viewTrue,viewFalse};

        pie.setData(data,viewsArray);
    }


    private void executeLevelTestsAPI() {
        mPDailog = Utils.CustomProgressbarDialog(context);
        mPDailog.show();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("total_question", "" + total_question));
        params.add(new BasicNameValuePair("correct", "" + 5));
        params.add(new BasicNameValuePair("wrong", "" + wrong_answers));
        params.add(new BasicNameValuePair("not_attempt", "" + skipped));
        params.add(new BasicNameValuePair("userid", strUserID));
        params.add(new BasicNameValuePair("testid", quiz_id));

        APIThread background_thread = new APIThread(Constants.SAVE_RESULT_URL, Constants.METHOD_POST, params, mResponseListener, mExceptionLitener);
        background_thread.start();
    }

    private void setCLickListner() {
        txtTestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StartTestActivity.class);
                intent.putExtra("MODAL", levelItemModal);
                startActivity(intent);
                finish();
            }
        });
        txtMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityHome.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setWidgetsIDs() {
        txtTestAgain = (TextView) findViewById(R.id.txtTestAgain);
        txtMainMenu = (TextView) findViewById(R.id.txtMainMenu);
        viewTrue = (View) findViewById(R.id.viewTrue);
        viewFalse = (View) findViewById(R.id.viewFalse);
    }


    public Boolean IsInternetConnect() {
        ConnectivityManager connMgr1 = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo1 = connMgr1.getActiveNetworkInfo();
        if (networkInfo1 != null && networkInfo1.isConnected()) {
            return true;
        } else
            return false;
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
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String data = jsonObject.getString("msg");

                        if (success.equals("1")) {
                            Toast.makeText(context, getString(R.string.doneresultadded), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (mPDailog != null && mPDailog.isShowing()) {
                        mPDailog.dismiss();
                    }

                    break;
                case 2:
                    Bundle bb1 = msg.getData();
                    String response1 = bb1.getString("RESPONSE");

                    Toast.makeText(context, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();

                    if (mPDailog != null && mPDailog.isShowing()) {
                        mPDailog.dismiss();
                    }
                    break;

            }
        }
    };

    private void setSharedPrefrences() {
        mPref = this.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Result Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

}
