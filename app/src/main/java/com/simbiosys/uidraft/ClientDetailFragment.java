package com.simbiosys.uidraft;


import android.content.Context;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button editButton;
    private ArrayList<EditText> editables;

    private String[][] fakeList = {{"note1", "note2", "note3"},
            {"Nov 6, 2015", "Nov 1, 2015", "Oct 14, 2015"}};

    private EditText editName;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientDetailFragment newInstance(String param1, String param2) {
        ClientDetailFragment fragment = new ClientDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ClientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        editButton = (Button) activity.findViewById(R.id.buttonEdit);

        ViewGroup group = (ViewGroup) editButton.getParent();
        editables = new ArrayList<>(group.getChildCount()-1);

        for (int i = 0; i < group.getChildCount(); i++) {
            View aView = group.getChildAt(i);
            if (aView instanceof EditText) {
                editables.add((EditText) aView);
            }
        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOK = editButton.getText().equals(getString(R.string.ok));

                for (EditText editText : editables) {
                    editText.setEnabled(!isOK);
                }

                if (isOK) {
                    editButton.setText(getString(R.string.edit));
                } else {
                    editButton.setText(getString(R.string.ok));
                }
            }
        });

        Button gotoUpcoming = (Button) activity.findViewById(R.id.btn_upcoming);
        Button gotoReport = (Button) activity.findViewById(R.id.btn_expense_report);
        gotoUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(MainActivity.UPCOMING_EVENTS);
            }
        });
        gotoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(MainActivity.EXPENSE_REPORTS);
            }
        });

        Button newNote = (Button) activity.findViewById(R.id.btn_newnote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(new NewNoteFragment(), true);
            }
        });

        Button client_sms = (Button) activity.findViewById(R.id.btn_sms);
        Button client_email = (Button) activity.findViewById(R.id.btn_emails);
        client_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Email list", Toast.LENGTH_LONG).show();
                ((MainActivity) getActivity()).replaceFragment(new ItemFragment(), true);
            }
        });

        client_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SMS list", Toast.LENGTH_LONG).show();
                ((MainActivity) getActivity()).replaceFragment(new ItemFragment(), true);
            }
        });

        fillList(activity);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
    }

    private void fillList(FragmentActivity activity) {
        ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(getContext(),
                android.R.layout.simple_list_item_2, android.R.id.text1, fakeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(fakeList[0][position]);
                text2.setText(fakeList[1][position]);
                return view;
            }
        };
        ListView notesList = (ListView) activity.findViewById(R.id.notesList);
        notesList.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int fragmentType) {

        if (mListener != null) {
            mListener.onFragmentInteraction(fragmentType);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int fragmentType);


    }

}
