package com.minkov.demos.mvp.PersonDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minkov.demos.mvp.R;
import com.minkov.demos.mvp.models.Person;

/**
 * The implementation of the {@link com.minkov.demos.mvp.PersonDetails.PersonDetailsContacts.View} class
 */
public class PersonDetailsFragment extends Fragment implements PersonDetailsContacts.View {
    private PersonDetailsContacts.Presenter mPresenter;
    private TextView mTvName;

    /**
     * Empty constructor is mandatory for fragments
     */
    public PersonDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_person_details, container, false);

        mTvName = root.findViewById(R.id.tv_name);

        return root;
    }

    /**
     * Factory method for creating a new instance of {@link PersonDetailsFragment}
     * @return the instance
     */
    public static PersonDetailsFragment newInstance() {
        return new PersonDetailsFragment();
    }

    @Override
    public void setPresenter(PersonDetailsContacts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mPresenter = null;
    }

    @Override
    public void setPerson(Person person) {
        mTvName.setText(person.getName());
    }
}
