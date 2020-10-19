/*
 * Created on Mon Sep 28 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class Shops_adapter extends RecyclerView.Adapter<Shops_adapter.ShopsViewHolder> {

    private List<String> list;

    public class ShopsViewHolder extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;

        public ShopsViewHolder(View view)
        {
            super(view);

            textView = (TextView)view.findViewById(R.id.txtname_shop);
        }
    }

    public Shops_adapter(List<String> horizontalList)
    {
        this.list = horizontalList;
    }

    @Override
    public ShopsViewHolder onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shops_adapter,
                        parent,
                        false);

        return new ShopsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShopsViewHolder holder,
                                 final int position)
    {

        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}

