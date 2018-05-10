package com.example.hp.verifyimagination.popmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.verifyimagination.R;

import java.util.ArrayList;

/**
 * @author SoBan
 * @create 2017/4/12 10:29.
 */

public class PopMenuMoreAdapter extends BaseAdapter {

    private ArrayList<PopMenuMoreItem> items;
    private Context context;

    public PopMenuMoreAdapter(Context context, ArrayList<PopMenuMoreItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PopMenuMoreItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_popmenu_more, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.menu_icon);
            holder.text = (TextView) view.findViewById(R.id.menu_text);
            view.setTag(holder);
        } else if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        PopMenuMoreItem item = items.get(position);
        if (item.getResId() == 0) {
            holder.icon.setVisibility(View.GONE);
        }
        holder.text.setText(item.getText());
        return view;
    }

    private class ViewHolder {
        ImageView icon;
        TextView text;
    }
}