package com.fatiappsstudio.rulesofjanazarnamaj;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Nishat on 5/19/2017.
 */

public class PersonFragment extends Fragment {
    private Person mPerson;
    private TextView mNameTextView, mDetailsTextView;
    private ImageView mPersonImageView;
    private static final String ARG_PERSON_ID = "person_id";

    //Attaching arguments to a fragment
    public static PersonFragment newInstance(UUID personId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_ID, personId);
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID personID = (UUID) getArguments().getSerializable(ARG_PERSON_ID); //
        mPerson = PersonLab.get(getActivity()).getPerson(personID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_details, container, false);



        mDetailsTextView = (TextView) v.findViewById(R.id.details_text);
        mDetailsTextView.setText(Html.fromHtml(mPerson.getDetails()));



        return v;
    }

}