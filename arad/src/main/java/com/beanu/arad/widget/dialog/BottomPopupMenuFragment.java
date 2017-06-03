package com.beanu.arad.widget.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.support.recyclerview.OnItemClickListener;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhihua on 2017/1/12.
 */

public class BottomPopupMenuFragment extends BottomSheetDialogFragment {
    private static final String TAG = BottomPopupMenuFragment.class.getSimpleName();

    private String mId;
    private String mTitle;
    private List<String> mMenus;
    private Listener mListener;
    private Map<String, Object> mExtData;

    public static class Builder {
        private String id;
        private String title;
        private List<String> menus;
        private Listener listener;
        private Map<String, Object> extData;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMenus(List<String> menus) {
            this.menus = menus;
            return this;
        }

        public Builder setMenus(String... menus) {
            this.menus = Arrays.asList(menus);
            return this;
        }

        public Builder setListener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setExtData(Map<String, Object> extData) {
            this.extData = extData;
            return this;
        }

        public BottomPopupMenuFragment create() {
            BottomPopupMenuFragment fragment = new BottomPopupMenuFragment();
            fragment.mId = id;
            fragment.mTitle = title;
            fragment.mMenus = menus;
            fragment.mListener = listener;
            fragment.mExtData = extData;
            return fragment;
        }
    }

    public BottomPopupMenuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_popup_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textTitle = (TextView) view.findViewById(R.id.text_title);
        TextView textCancel = (TextView) view.findViewById(R.id.text_cancel);
        RecyclerView listMenus = (RecyclerView) view.findViewById(R.id.list_menus);
        listMenus.setLayoutManager(new LinearLayoutManager(getContext()));
        listMenus.setAdapter(new MenusAdapter(mMenus));

        listMenus.addOnItemTouchListener(new OnItemClickListener(listMenus) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                if (mListener != null && mMenus != null) {
                    mListener.onMenuClick(mId, position, mExtData);
                }
                dismiss();
            }
        });
        listMenus.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#dedfe0"))
                .size(1)
                .build());

        if (!TextUtils.isEmpty(mTitle)) {
            textTitle.setVisibility(View.VISIBLE);
            textTitle.setText(mTitle);
        }
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            if (window != null) {
                //解决状态栏变纯黑的问题
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        return dialog;
    }

    public interface Listener {
        void onMenuClick(String id, int clickIndex, Map<String, Object> extData);
    }

    private class MenusViewHolder extends RecyclerView.ViewHolder {
        TextView textMenu;

        MenusViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            this(inflater.inflate(R.layout.item_popup_menu, parent, false));
        }

        MenusViewHolder(View itemView) {
            super(itemView);
            textMenu = (TextView) itemView;
        }

        void bind(List<String> menus, int position) {
            textMenu.setText(menus.get(position));
        }
    }

    private class MenusAdapter extends RecyclerView.Adapter<MenusViewHolder> {
        List<String> menus;
        LayoutInflater inflater;

        MenusAdapter(List<String> menus) {
            this.menus = menus;
            this.inflater = LayoutInflater.from(getContext());
        }

        @Override
        public MenusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenusViewHolder(inflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(MenusViewHolder holder, int position) {
            holder.bind(menus, position);
        }

        @Override
        public int getItemCount() {
            return menus == null ? 0 : menus.size();
        }
    }
}
