package com.app.emlaee.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.emlaee.R;
import com.app.emlaee.StartTestActivity;
import com.app.emlaee.modal.LevelItemModal;
import com.app.emlaee.utils.Utils;

import java.util.ArrayList;

/**
 * Created by sahil on 1/10/2017.
 */

public class LavelItemAdapter extends RecyclerView.Adapter<LavelItemAdapter.ViewHolder>{
    Context context;
    ArrayList<LevelItemModal> modalArrayList;
    Resources resources;
    View rootView;
    int arr3[] = {R.drawable.level_item1, R.drawable.level_item2, R.drawable.level_item3,R.drawable.level_item1, R.drawable.level_item2, R.drawable.level_item3};

    public LavelItemAdapter(Context context, ArrayList<LevelItemModal> modalArrayList, Resources resources) {
        this.context = context;
        this.modalArrayList = modalArrayList;
        this.resources = resources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lavelitem, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LevelItemModal modal = (LevelItemModal)modalArrayList.get(position);
        holder.imageView.setImageResource(modal.getImage());
        holder.txtTopTilte.setText(modal.getName());
        holder.txtDescription.setText(modal.getDescription());
        holder.layoutRelative.setBackgroundResource(arr3[Utils.randInt(0,5)]);

        holder.txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Button LClick",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StartTestActivity.class);
                intent.putExtra("MODAL",modal);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return modalArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtTopTilte;
        TextView txtDescription;
        TextView txtOption;
        LinearLayout layoutRelative;
        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView)view.findViewById(R.id.imageView);
            txtTopTilte = (TextView) view.findViewById(R.id.txtTopTilte);
            txtDescription = (TextView)view.findViewById(R.id.txtDescription);
            txtOption = (TextView) view.findViewById(R.id.txtOption);
            /*****Set the Different background for every position or recycler view*****/
            layoutRelative = (LinearLayout) view.findViewById(R.id.layoutRelative);

        }
    }
}
