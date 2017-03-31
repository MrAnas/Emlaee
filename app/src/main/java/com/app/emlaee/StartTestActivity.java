package com.app.emlaee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.emlaee.interfaces.ExceptionListener;
import com.app.emlaee.interfaces.ResponseListener;
import com.app.emlaee.modal.LevelItemModal;
import com.app.emlaee.modal.TestQustionsModal;
import com.app.emlaee.utils.Constants;
import com.app.emlaee.utils.SingletonDataCollection;
import com.app.emlaee.utils.Utils;
import com.app.emlaee.webServices.APIThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StartTestActivity extends AppCompatActivity {

    public static final String TAG = "";
    public Context context = StartTestActivity.this;
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    SingletonDataCollection singletonDataCollection = SingletonDataCollection.getInstance();
    ArrayList<TestQustionsModal> questionarr;

    String getIntentModal = "";
    LevelItemModal levelItemModal;

    TextView txtTestName, txtQustionWithNumber, txtQustionLevel, txtQuestion, txtPrevious, txtSaveandNext, txtFinish;
    CheckBox chk_CheckA, chk_CheckB, chk_CheckC, chk_CheckD;
    private AlertDialog mPDailog;

    String strQuizID = "";

    int current_question = 0;
    int total_question = 0;
    int correct_answers = 0;
    int wrong_answers = 0;
    int skipped = 0;


    LinearLayout layoutGetHint;
    Dialog customDialog;
    int width, height;

    private ImageView mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        customDialog = new Dialog(context);

        /*****setSharedPrefrences*****/
        setSharedPrefrences();

        checkboxIDS();
        /******Get Intent****/
        if (getIntent().getExtras() != null) {
            levelItemModal = (LevelItemModal) getIntent().getSerializableExtra("MODAL");
            strQuizID = levelItemModal.getId();
        }

        questionarr = singletonDataCollection.getTestQustionsModalsArrayList();
        /*******Set Toolbar******/
        setToolbar();


        /*****setWidget Ids****/
        setWidgetIDs();

        /*****Execute Questions API*****/
        if (!IsInternetConnect()) {
            Toast.makeText(context, R.string.network_connection_msg, Toast.LENGTH_SHORT).show();
        } else {
            /******execute Categories Test******/
            executeLevelTestsAPI();
        }

        /******set CLickListner*******/
        setClickListner();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtFinish = (TextView) toolbar.findViewById(R.id.txtFinish);
    }

    private void executeLevelTestsAPI() {
        mPDailog = Utils.CustomProgressbarDialog(context);
        mPDailog.show();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("quiz_id", strQuizID));

        APIThread background_thread = new APIThread(Constants.TESTS_QUESTIONS_URL, Constants.METHOD_POST, params, mResponseListener, mExceptionLitener);
        background_thread.start();
    }

    private void setWidgetIDs() {
        txtTestName = (TextView) findViewById(R.id.txtTestName);
        txtQustionWithNumber = (TextView) findViewById(R.id.txtQustionWithNumber);
        txtQustionLevel = (TextView) findViewById(R.id.txtQustionLevel);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtSaveandNext = (TextView) findViewById(R.id.txtSaveandNext);
        layoutGetHint = (LinearLayout) findViewById(R.id.layoutGetHint);
        mBackBtn = (ImageView) findViewById(R.id.back_ic);

    }

    public void checkboxIDS() {

        chk_CheckA = (CheckBox) findViewById(R.id.chk_CheckA);
        chk_CheckB = (CheckBox) findViewById(R.id.chk_CheckB);
        chk_CheckC = (CheckBox) findViewById(R.id.chk_CheckC);
        chk_CheckD = (CheckBox) findViewById(R.id.chk_CheckD);
    }

    private void setClickListner() {

        layoutGetHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***********Show Get Hint PopUp************/
                showGetHindCustomDialog();
            }

        });

        chk_CheckA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Log.e("current_question", "" + current_question);
                    chk_CheckA.setChecked(true);
                    chk_CheckC.setChecked(false);
                    chk_CheckB.setChecked(false);
                    chk_CheckD.setChecked(false);

                    questionarr.get(current_question).getOptions().get(0).setChecked(true);
                    questionarr.get(current_question).getOptions().get(1).setChecked(false);
                    questionarr.get(current_question).getOptions().get(2).setChecked(false);
                    questionarr.get(current_question).getOptions().get(3).setChecked(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chk_CheckB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Log.e("current_question", "" + current_question);
                    chk_CheckA.setChecked(false);
                    chk_CheckB.setChecked(true);
                    chk_CheckD.setChecked(false);
                    chk_CheckC.setChecked(false);

                    questionarr.get(current_question).getOptions().get(0).setChecked(false);
                    questionarr.get(current_question).getOptions().get(1).setChecked(true);
                    questionarr.get(current_question).getOptions().get(2).setChecked(false);
                    questionarr.get(current_question).getOptions().get(3).setChecked(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chk_CheckC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Log.e("current_question", "" + current_question);
                    chk_CheckA.setChecked(false);
                    chk_CheckB.setChecked(false);
                    chk_CheckC.setChecked(true);
                    chk_CheckD.setChecked(false);

                    questionarr.get(current_question).getOptions().get(0).setChecked(false);
                    questionarr.get(current_question).getOptions().get(1).setChecked(false);
                    questionarr.get(current_question).getOptions().get(2).setChecked(true);
                    questionarr.get(current_question).getOptions().get(3).setChecked(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chk_CheckD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Log.e("current_question", "" + current_question);
                    chk_CheckA.setChecked(false);
                    chk_CheckB.setChecked(false);
                    chk_CheckC.setChecked(false);
                    chk_CheckD.setChecked(true);


                    questionarr.get(current_question).getOptions().get(0).setChecked(false);
                    questionarr.get(current_question).getOptions().get(1).setChecked(false);
                    questionarr.get(current_question).getOptions().get(2).setChecked(false);
                    questionarr.get(current_question).getOptions().get(3).setChecked(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        txtSaveandNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("before current_question", "" + current_question);
                try {

                    current_question++;
                    Log.e("current_question", "" + current_question);
                    txtQustionWithNumber.setText("");
//                    int actualNO = current_question + 1;
                    txtQustionWithNumber.setText(getResources().getString(R.string.questionNo) + "" + (current_question + 1));

                    if (current_question <= questionarr.size() - 1) {
                        txtSaveandNext.setClickable(true);

                        txtQuestion.setText(getResources().getString(R.string.questionNo) + (current_question + 1) + "  " + questionarr.get(current_question).getQuestion());

                        chk_CheckA.setText(questionarr.get(current_question).getOptions().get(0).getAnswer());
                        chk_CheckB.setText(questionarr.get(current_question).getOptions().get(1).getAnswer());
                        chk_CheckC.setText(questionarr.get(current_question).getOptions().get(2).getAnswer());
                        chk_CheckD.setText(questionarr.get(current_question).getOptions().get(3).getAnswer());


                        chk_CheckA.setChecked(questionarr.get(current_question).getOptions().get(0).isChecked());
                        chk_CheckB.setChecked(questionarr.get(current_question).getOptions().get(1).isChecked());
                        chk_CheckC.setChecked(questionarr.get(current_question).getOptions().get(2).isChecked());
                        chk_CheckD.setChecked(questionarr.get(current_question).getOptions().get(3).isChecked());

                    } else {
                        Toast.makeText(StartTestActivity.this, getResources().getString(R.string.nomoreques), Toast.LENGTH_SHORT).show();
                        txtSaveandNext.setClickable(false);
                        current_question = current_question - 1;

                        /********finishQustionThenGotoResultScreen*********/
                        finishQustionThenGotoResultScreen();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("before current_question", "" + current_question);
                try {
                    current_question--;
                    if (current_question < 0) {
                        current_question = 0;
                        return;
                    }
                    Log.e("current_question", "" + current_question);
                    txtQustionWithNumber.setText("");
//                    int actualNO = current_question - 1;
                    txtQustionWithNumber.setText(getResources().getString(R.string.questionNo) + "" + (current_question+1));


                    txtQuestion.setText(getResources().getString(R.string.questionNo) + (current_question + 1) + "  " + questionarr.get(current_question).getQuestion());

                    chk_CheckA.setText(questionarr.get(current_question).getOptions().get(0).getAnswer());
                    chk_CheckB.setText(questionarr.get(current_question).getOptions().get(1).getAnswer());
                    chk_CheckC.setText(questionarr.get(current_question).getOptions().get(2).getAnswer());
                    chk_CheckD.setText(questionarr.get(current_question).getOptions().get(3).getAnswer());


                    chk_CheckA.setChecked(questionarr.get(current_question).getOptions().get(0).isChecked());
                    chk_CheckB.setChecked(questionarr.get(current_question).getOptions().get(1).isChecked());
                    chk_CheckC.setChecked(questionarr.get(current_question).getOptions().get(2).isChecked());
                    chk_CheckD.setChecked(questionarr.get(current_question).getOptions().get(3).isChecked());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


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

    private void setSharedPrefrences() {
        mPref = this.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();
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
                        String data = jsonObject.getString("data");

                        if (success.equals("1")) {
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String question_id = obj.getString("question_id");
                                String ques = obj.getString("question");
                                String ans = obj.getString("answer");
                                questionarr.add(new TestQustionsModal(ques, ans, question_id));
                            }
                            /*************setting Up Data in Views***********/
                            setDataonWidgets();
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

    private void setDataonWidgets() {
        chk_CheckA.setText(questionarr.get(current_question).getOptions().get(0).getAnswer());
        chk_CheckB.setText(questionarr.get(current_question).getOptions().get(1).getAnswer());
        chk_CheckC.setText(questionarr.get(current_question).getOptions().get(2).getAnswer());
        chk_CheckD.setText(questionarr.get(current_question).getOptions().get(3).getAnswer());

        chk_CheckA.setChecked(questionarr.get(current_question).getOptions().get(0).isChecked());
        chk_CheckB.setChecked(questionarr.get(current_question).getOptions().get(1).isChecked());
        chk_CheckC.setChecked(questionarr.get(current_question).getOptions().get(2).isChecked());
        chk_CheckD.setChecked(questionarr.get(current_question).getOptions().get(3).isChecked());

        txtQustionWithNumber.setText(getResources().getString(R.string.questionNo) + "" + (current_question + 1));
    }


    /*********
     * Finish Test Goto Next Screen
     ************/
    public void finishQustionThenGotoResultScreen() {
        try {
            total_question = questionarr.size();

            for (int i = 0; i < questionarr.size(); i++) {
                for (int j = 0; j < questionarr.get(i).getOptions().size(); j++) {
                    if (questionarr.get(i).getOptions().get(j).isChecked() == true && questionarr.get(i).getOptions().get(j).isIsCorrect() == true) {
                        ++correct_answers;
                        questionarr.get(i).setQuesStatus("Correct");
                    }
                }
            }


            for (int i = 0; i < questionarr.size(); i++) {

                if (questionarr.get(i).getOptions().size() == 3) {
                    if (questionarr.get(i).getOptions().get(0).isChecked() == false && questionarr.get(i).getOptions().get(1).isChecked() == false &&
                            questionarr.get(i).getOptions().get(2).isChecked() == false) {
                        ++skipped;
                        questionarr.get(i).setQuesStatus("Not Answered");
                    }
                }
                if (questionarr.get(i).getOptions().size() == 4) {
                    if (questionarr.get(i).getOptions().get(0).isChecked() == false && questionarr.get(i).getOptions().get(1).isChecked() == false &&
                            questionarr.get(i).getOptions().get(2).isChecked() == false && questionarr.get(i).getOptions().get(3).isChecked() == false) {
                        ++skipped;
                        questionarr.get(i).setQuesStatus("Not Answered");
                    }
                }
                if (questionarr.get(i).getOptions().size() == 5) {
                    if (questionarr.get(i).getOptions().get(0).isChecked() == false && questionarr.get(i).getOptions().get(1).isChecked() == false &&
                            questionarr.get(i).getOptions().get(2).isChecked() == false && questionarr.get(i).getOptions().get(3).isChecked() == false &&
                            questionarr.get(i).getOptions().get(4).isChecked() == false) {
                        ++skipped;
                        questionarr.get(i).setQuesStatus("Not Answered");
                    }
                }


            }

            wrong_answers = questionarr.size() - correct_answers - skipped;

            Log.e(TAG, "total_question" + total_question);
            Log.e(TAG, "correct_answers" + correct_answers);
            Log.e(TAG, "wrong_answers" + wrong_answers);
            Log.e(TAG, "skipped" + skipped);

            Intent i = new Intent(StartTestActivity.this, ResultActivity.class);
            i.putExtra("MODAL", levelItemModal);
            i.putExtra("total_question", total_question);
            i.putExtra("correct_answers", correct_answers);
            i.putExtra("wrong_answers", wrong_answers);
            i.putExtra("skipped", skipped);
            startActivity(i);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showGetHindCustomDialog() {

        /*******setting Up Dialog*****/
        customDialog = new Dialog(context, R.style.DialogTheme);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.customgethint_dialog);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /****Getting Height Width***/
        getHeightWidth();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        customDialog.getWindow().setAttributes(lp);

        /**********Define the Dialog Widgets*******/
        ImageView imgLaato = (ImageView) customDialog.findViewById(R.id.imgLaato);
        TextView txtCenterText = (TextView) customDialog.findViewById(R.id.txtCenterText);
        TextView btnQuestionCounting = (TextView) customDialog.findViewById(R.id.btnQuestionCounting);

        /********Set CLick Listner********/
        btnQuestionCounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        customDialog.show();


    }

    public void getHeightWidth() {
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        ViewGroup.LayoutParams params;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            width = manager.getDefaultDisplay().getWidth();
            height = manager.getDefaultDisplay().getHeight();
        } else {
            Point point = new Point();
            manager.getDefaultDisplay().getSize(point);
            width = point.x;
            height = point.y;
        }
    }

}




