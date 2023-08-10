package com.fatiappsstudio.rulesofjanazarnamaj;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nishat on 12/30/2016.
 */



public class PersonListFragment extends Fragment {
    private RecyclerView mPersonRecyclerView;
    private PersonAdapter mAdapter;

    public static PersonListFragment newInstance() {
        Bundle args = new Bundle();
        PersonListFragment fragment = new PersonListFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        //create RecyclerView that requires a layoutManager
        mPersonRecyclerView = (RecyclerView) view.findViewById(R.id.person_recycler_view);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Now that you have an Adapter, your final step is to connect it to your RecyclerView.
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        PersonLab personLab = PersonLab.get(getActivity());
        List<Person> persons = personLab.getPersons();


        if (mAdapter == null) {
            mAdapter = new PersonAdapter(persons);
            mPersonRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(persons);
            mAdapter.notifyDataSetChanged();
        }
    }


    //inner class-a simple View Holder Class
    private class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mPersonTextView;
        private ImageView mPersonImageView;

        private Person mPerson;

        public PersonHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPersonTextView = (TextView) itemView.findViewById(R.id.list_item_person_text);
            mPersonImageView = (ImageView) itemView.findViewById(R.id.list_item_person_image);
        }

        public void bindPerson(Person person) {
            mPerson = person;
            mPersonTextView.setText(Html.fromHtml(mPerson.getName()));
            mPersonImageView.setImageBitmap(ScaleImage.decodeSampledBitmapFromResource(getResources(),
                    getResources().getIdentifier(mPerson.getPhoto(), "drawable", getActivity().getPackageName()),
                    500, 500));
        }


        @Override
        public void onClick(View v) {
            //Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            Intent intent = PersonPagerActivity.newIntent(getActivity(), mPerson.getID());
            startActivity(intent);
        }
    }

    //the beginning of an adapter
    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {
        private List<Person> mPersons;

        public PersonAdapter(List<Person> persons ) {
            mPersons = persons;
        }

        //create the view and wrap it in a View Holder (CrimeHolder)
        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            View view = layoutInflater.inflate(R.layout.list_item_person, parent, false);
            return new PersonHolder(view);
        }

        //This method will bind a ViewHolder's view to the model object
        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            Person person = mPersons.get(position);
            holder.bindPerson(person);
        }

        @Override
        public int getItemCount() {
            return mPersons.size();
        }

        public void setCrimes(List<Person> persons) {
            mPersons = persons;
        }
    }

}

