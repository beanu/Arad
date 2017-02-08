package com.beanu.arad.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by lizhihua on 2017/1/12.
 */

public class BottomPopupMenuFragment extends BottomSheetDialogFragment {
    private static final String TAG = BottomPopupMenuFragment.class.getSimpleName();
    private static final String ARG_ID = "id";
    private static final String ARG_TITLE = "title";
    private static final String ARG_MENUS = "menus";
    private Listener mListener;

    public static void show(String id, String title, Collection<String> menus, @NonNull FragmentManager fragmentManager) {
        newInstance(id, title, menus).show(fragmentManager, TAG + id);
    }

    public static void show(String id, String title, String[] menus, @NonNull FragmentManager fragmentManager) {
        newInstance(id, title, Arrays.asList(menus)).show(fragmentManager, TAG + id);
    }

    public static void show(String id, Collection<String> menus, @NonNull FragmentManager fragmentManager) {
        newInstance(id, menus).show(fragmentManager, TAG + id);
    }

    public static void show(String id, String[] menus, @NonNull FragmentManager fragmentManager) {
        newInstance(id, Arrays.asList(menus)).show(fragmentManager, TAG + id);
    }

    public static BottomPopupMenuFragment newInstance(String id, String title, @NonNull Collection<String> menus) {
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_TITLE, title);
        args.putStringArrayList(ARG_MENUS, new ArrayList<>(menus));
        BottomPopupMenuFragment fragment = new BottomPopupMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BottomPopupMenuFragment newInstance(String id, @NonNull Collection<String> menus) {
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putStringArrayList(ARG_MENUS, new ArrayList<>(menus));
        BottomPopupMenuFragment fragment = new BottomPopupMenuFragment();
        fragment.setArguments(args);
        return fragment;
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
        listMenus.setAdapter(new MenusAdapter(getArguments().getStringArrayList(ARG_MENUS)));

        final ArrayList<String> menus = getArguments().getStringArrayList(ARG_MENUS);

        listMenus.addOnItemTouchListener(new OnItemClickListener(listMenus) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                if (mListener != null && menus != null) {
                    mListener.onMenuClick(getArguments().getString(ARG_ID), menus.get(position), position);
                }
                dismiss();
            }
        });
        listMenus.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#dedfe0"))
                .size(1)
                .build());

        String title = getArguments().getString(ARG_TITLE);
        if (!TextUtils.isEmpty(title)) {
            textTitle.setVisibility(View.VISIBLE);
            textTitle.setText(title);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        final Fragment parent = getParentFragment();
        if (parent != null && parent instanceof Listener) {
            mListener = (Listener) parent;
        } else if (context instanceof Listener) {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onMenuClick(String id, String clickMenuString, int clickIndex);
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
        ArrayList<String> menus;
        LayoutInflater inflater;

        MenusAdapter(ArrayList<String> menus) {
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
