package com.app.emlaee.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.emlaee.LoginActivity;
import com.app.emlaee.R;
import com.app.emlaee.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView txtLogout;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_settings, container, false);

        /*****Shared Prefrances****/
        setSharedPrefrences();

        /******set Widgets******/
        setWidgetsIDs(rooView);

        /******Set Click Listner*******/
        setClickListner();

        return rooView;
    }

    private void setClickListner() {
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           // Logout App

           if (mEditor != null) {
                mEditor.clear();
                mEditor.commit();
            }
            Intent loginScreen = new Intent(getActivity(), LoginActivity.class);
            startActivity(loginScreen);
            getActivity().finish();

            }
        });
    }

    private void setWidgetsIDs(View rootView) {
        txtLogout = (TextView)rootView.findViewById(R.id.txtLogout);
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

    public void setSharedPrefrences() {
        mPref = getActivity().getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mEditor = mPref.edit();
    }
}
