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

public class AssistantAdapter extends RecyclerView.Adapter<AssistantAdapter.AssistantsViewHolder> {

    private List<String> list;

    public class AssistantsViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public AssistantsViewHolder(View view)
        {
            super(view);
            textView = (TextView)view.findViewById(R.id.txtname_assistants);
        }
    }

    public AssistantAdapter(List<String> horizontalList)
    {
        this.list = horizontalList;
    }

    @Override
    public AssistantsViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_assistant_adapter,
                        parent,
                        false);

        return new AssistantsViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final AssistantsViewHolder holder,
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


