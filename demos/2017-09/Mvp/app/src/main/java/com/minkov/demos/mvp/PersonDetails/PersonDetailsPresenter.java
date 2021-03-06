package com.minkov.demos.mvp.PersonDetails;

import com.minkov.demos.mvp.models.Person;
import com.minkov.demos.mvp.repositories.base.BaseRepository;
import com.minkov.demos.mvp.utils.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Implementation of {@link com.minkov.demos.mvp.PersonDetails.PersonDetailsContacts.Presenter}
 * Created by minkov on 9/27/17.
 */

public class PersonDetailsPresenter implements PersonDetailsContacts.Presenter {
    private final BaseRepository<Person> mRepository;
    private final BaseSchedulerProvider mScheduleProvider;

    private PersonDetailsContacts.View mView;
    private String mPersonId;

    /**
     * Initializes a new {@link PersonDetailsPresenter}
     *
     * @param repository a {@link BaseRepository} instance for loading data
     * @param schedulerProvider {@link BaseSchedulerProvider} instance for control of the async operations
     */
    @Inject
    public PersonDetailsPresenter(BaseRepository<Person> repository,
                                  BaseSchedulerProvider schedulerProvider) {
        mRepository = repository;
        mScheduleProvider = schedulerProvider;
    }

    @Override
    public void subscribe(PersonDetailsContacts.View view) {
        mView = view;
        reload();
    }

    private void reload() {
        if (mPersonId == null) {
            return;
        }

        mRepository.getById(mPersonId)
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
                .subscribe(new Consumer<Person>() {
                    @Override
                    public void accept(Person person) throws Exception {
                        mView.setPerson(person);

                    }
                });
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void setPersonId(String id) {
        mPersonId = id;
        reload();
    }
}
