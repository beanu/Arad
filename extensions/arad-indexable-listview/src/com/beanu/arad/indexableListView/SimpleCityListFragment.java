package com.beanu.arad.indexableListView;

import android.app.Activity;
import android.content.Intent;

import com.beanu.arad.indexableListView.widget.ContactItemInterface;
import com.beanu.arad.indexableListView.widget.ContactListAdapter;

import java.util.List;

/**
 * Created by Beanu on 16/3/6.
 */
public class SimpleCityListFragment extends CityListFragment {

    @Override
    public List<ContactItemInterface> getContactList() {
        List<ContactItemInterface> list = CityData.getSampleContactList();
        list.add(0, new CityItem("杭州", ""));
        return list;
    }

    @Override
    public ContactListAdapter getAdapter(List<ContactItemInterface> contactList) {
        return new CityAdapter(getActivity(), R.layout.listview_item, contactList);
    }

    @Override
    public void onListItemClick(List<ContactItemInterface> searchList, int position) {
//        AppHolder.getInstance().location.setCity(searchList.get(position).getDisplayInfo());
//        Arad.preferences.putString(Constants.P_city, searchList.get(position).getDisplayInfo());
//        Arad.preferences.flush();

        Intent intent = getActivity().getIntent();
        intent.putExtra("cityName", searchList.get(position).getDisplayInfo());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    }
}
