package com.app.emlaee.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.emlaee.R;
import com.app.emlaee.interfaces.OnItemClickListener;
import com.app.emlaee.modal.CategoriesModal;

import java.util.ArrayList;

/**
 * Created by sahil on 1/11/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{
    Context context;
    ArrayList<CategoriesModal> modalArrayList;
    Resources resources;
    OnItemClickListener onItemClickListener;
    View rootView;

    public CategoriesAdapter(Context context, ArrayList<CategoriesModal> modalArrayList, Resources resources, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.modalArrayList = modalArrayList;
        this.resources = resources;
        this.onItemClickListener = onItemClickListener;
        /***initialize ***/

    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categoriesitem, parent, false);
        //rootView.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark));
        return new CategoriesAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final CategoriesAdapter.ViewHolder holder, final int position) {
        CategoriesModal modal = (CategoriesModal)modalArrayList.get(position);
        holder.txtItem.setText(modal.getName());

        holder.txtItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(holder.itemView,position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modalArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtItem;
        public ViewHolder(View rootView) {
            super(rootView);
            txtItem = (TextView) rootView.findViewById(R.id.txtItem);
        }

    }


}
