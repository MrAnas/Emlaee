package com.app.emlaee.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.emlaee.ActivityHome;
import com.app.emlaee.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerformanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerformanceFragment#} factory method to
 * create an instance of this fragment.
 */
public class PerformanceFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View rootView;
    public PerformanceFragment() {
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

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_performance, container, false);

            /*set Toolbar*/
            ActivityHome.setToolbarText(" ");


            /******set Widgets Ids******/
            setWidgetsIds(rootView);

            /********set CLick Listner*********/
            setCLickListner();

            return rootView;
        } else
            return rootView;


        // Inflate the layout for this fragment

    }

    private void setWidgetsIds(View rootView) {
    }

    private void setCLickListner() {

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

    @Override
    public void onDetach() {
        super.onDetach();
        ActivityHome obj = (ActivityHome) getActivity();
        obj.showBottomButton();
        obj.replaceWithLevelFragemnt();
    }
}
