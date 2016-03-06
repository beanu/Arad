package com.beanu.arad.indexableListView;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.beanu.arad.indexableListView.widget.ContactItemInterface;
import com.beanu.arad.indexableListView.widget.ContactListAdapter;

import java.util.List;

/**
 * 城市adapter
 * Created by beanu on 14-8-21.
 */
public class CityAdapter extends ContactListAdapter {

    public CityAdapter(Context _context, int _resource,
                       List<ContactItemInterface> _items) {
        super(_context, _resource, _items);
    }

    @Override
    public void populateDataForRow(View parentView, ContactItemInterface item,
                                   int position) {
        View infoView = parentView.findViewById(R.id.infoRowContainer);
        TextView nicknameView = (TextView) infoView
                .findViewById(R.id.cityName);

        nicknameView.setText(item.getDisplayInfo());
    }

}