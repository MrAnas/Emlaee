package com.app.emlaee.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.emlaee.ActivityHome;
import com.app.emlaee.Adapters.LavelItemAdapter;
import com.app.emlaee.R;
import com.app.emlaee.interfaces.ExceptionListener;
import com.app.emlaee.interfaces.ResponseListener;
import com.app.emlaee.modal.LevelItemModal;
import com.app.emlaee.utils.Constants;
import com.app.emlaee.utils.SingletonDataCollection;
import com.app.emlaee.utils.Utils;
import com.app.emlaee.webServices.APIThread;
import com.app.emlaee.webServices.APIThreadUnicode;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelsFragment extends Fragment {

    private View view;

    RecyclerView widgetRecyclerView;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;


    String arr1[] = {"ابدأ المستوىابدأ المستوى", "ابدأ المستوى", "ابدأ المستوىابدأ المستوى", "ابدأ المستوى"};
    String arr2[] = {"ابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوى",
            "ابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوى",
            "ابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوى", "ابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوىابدأ المستوى"};
    int arr3[] = {R.drawable.level_item1, R.drawable.level_item2, R.drawable.level_item3};

    public SingletonDataCollection singletonDataCollection = SingletonDataCollection.getInstance();
    ArrayList<LevelItemModal> modalArrayList;

    LavelItemAdapter mlavelItemAdapter;
    private AlertDialog mPDailog;

    String strGetIntent = "";
    String categoryID = "";


    public LevelsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            categoryID = getArguments().getString("CATEGORY_ID");
//            Log.e("","CATEGORY_ID++++++++++++++"+categoryID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_levels, container, false);

            /******initialize Arraylist*******/
            modalArrayList = singletonDataCollection.getLevelArrayList();

            /*Set Shared Prefrances*/
            setSharedPrefrences();

             /*set Toolbar*/
            ActivityHome.setToolbarText("الصفحة الرئيسية ");

            /********Set Recycler View **********/
            setRecyclerView(view);

            if (!IsInternetConnect()) {
                Toast.makeText(getActivity(), R.string.network_connection_msg, Toast.LENGTH_SHORT).show();
            } else {
                    /******execute Categories Test******/
                    executeLevelTestsAPI();
            }

            return view;
        } else
            return view;
    }


    private void executeLevelTestsAPI() {
        mPDailog = Utils.CustomProgressbarDialog(getActivity());
        mPDailog.show();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("category_id",categoryID));

        APIThread background_thread = new APIThread(Constants.CATEGORIES_TEST_URL, Constants.METHOD_GET, params, mResponseListener, mExceptionLitener);
        background_thread.start();
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
                    try{
                        modalArrayList.clear();
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        String data = jsonObject.getString("data");

                        if (success.equals("1")){
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                LevelItemModal modal = new LevelItemModal();
                                modal.setId(jsonObject1.getString("id"));
                                modal.setName(jsonObject1.getString("name"));
                                modal.setDescription(jsonObject1.getString("description"));
                                modal.setImage(R.drawable.logo_img);

                                modalArrayList.add(modal);
                            }

                        }else{
                            Toast.makeText(getActivity(), getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                        }

                        if (mPDailog != null && mPDailog.isShowing()) {
                            mPDailog.dismiss();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    /********Adapter******/
                    setAdapter();

                    if (mPDailog != null && mPDailog.isShowing()) {
                        mPDailog.dismiss();
                    }

                    break;
                case 2:
                    Bundle bb1 = msg.getData();
                    String response1 = bb1.getString("RESPONSE");

                    Toast.makeText(getActivity(), getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();

                    if (mPDailog != null && mPDailog.isShowing()) {
                        mPDailog.dismiss();
                    }
                    break;

            }
        }
    };


    private void setRecyclerView(View view) {
        widgetRecyclerView = (RecyclerView) view.findViewById(R.id.widgetRecyclerView);
    }


    private void setSharedPrefrences() {
        mPref = getActivity().getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public Boolean IsInternetConnect() {
        ConnectivityManager connMgr1 = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo1 = connMgr1.getActiveNetworkInfo();
        if (networkInfo1 != null && networkInfo1.isConnected()) {
            return true;
        } else
            return false;
    }

    /*********Set Adapter*******/
    public void setAdapter(){
        /******Set RecyclerView Adapter*******/
        widgetRecyclerView.setHasFixedSize(true);
        widgetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*********Set Recyclerview Adapter********/
        widgetRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mlavelItemAdapter = new LavelItemAdapter(getActivity(), modalArrayList, getResources());
        widgetRecyclerView.setAdapter(mlavelItemAdapter);
    }
}
