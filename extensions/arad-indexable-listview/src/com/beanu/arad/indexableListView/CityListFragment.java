package com.beanu.arad.indexableListView;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.beanu.arad.indexableListView.widget.ContactItemInterface;
import com.beanu.arad.indexableListView.widget.ContactListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市列表选择器
 * Created by Beanu on 16/3/4.
 */
public abstract class CityListFragment extends Fragment implements TextWatcher {

    EditText mInputSearchQuery;
    RelativeLayout mSearchBarContainer;
    IndexableListView mListview;

    private final Object searchLock = new Object();
    boolean inSearchMode = false;

    private final static String TAG = "MainActivity2";

    List<ContactItemInterface> contactList;
    List<ContactItemInterface> filterList;
    private SearchListTask curSearchTask = null;


    public abstract List<ContactItemInterface> getContactList();

    public abstract ContactListAdapter getAdapter(List<ContactItemInterface> list);

    public abstract void onListItemClick(List<ContactItemInterface> searchList, int position);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filterList = new ArrayList<>();
        contactList = getContactList();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInputSearchQuery = (EditText) view.findViewById(R.id.input_search_query);
        mSearchBarContainer = (RelativeLayout) view.findViewById(R.id.searchBarContainer);
        mListview = (IndexableListView) view.findViewById(R.id.listview);
        initCityList();

        mInputSearchQuery.addTextChangedListener(this);
    }


    private void initCityList() {

        mListview.setFastScrollEnabled(true);
        mListview.setAdapter(getAdapter(contactList));

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position,
                                    long id) {
                List<ContactItemInterface> searchList = inSearchMode ? filterList : contactList;
                onListItemClick(searchList, position);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String searchString = mInputSearchQuery.getText().toString().trim().toUpperCase();

        if (curSearchTask != null
                && curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                curSearchTask.cancel(true);
            } catch (Exception e) {
                Log.i(TAG, "Fail to cancel running search task");
            }

        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);
    }


    private class SearchListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            filterList.clear();

            String keyword = params[0];

            inSearchMode = (keyword.length() > 0);

            if (inSearchMode) {
                // get all the items matching this
                for (int i = 1; i < contactList.size(); i++) {
                    ContactItemInterface item = contactList.get(i);
                    CityItem contact = (CityItem) item;

                    boolean isPinyin = contact.getFullName().toUpperCase().contains(keyword);
                    boolean isChinese = contact.getNickName().contains(keyword);

                    if (isPinyin || isChinese) {
                        filterList.add(item);
                    }
                }

            }
            return null;
        }

        protected void onPostExecute(String result) {

            synchronized (searchLock) {

                if (inSearchMode) {

                    CityAdapter adapter = new CityAdapter(getActivity(),
                            R.layout.listview_item, filterList);
                    adapter.setInSearchMode(true);
                    mListview.setAdapter(adapter);
                } else {
                    CityAdapter adapter = new CityAdapter(getActivity(),
                            R.layout.listview_item, contactList);
                    adapter.setInSearchMode(false);
                    mListview.setAdapter(adapter);
                }
            }

        }
    }

}
