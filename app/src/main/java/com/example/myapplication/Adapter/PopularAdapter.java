package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.databinding.ViewholderPupListBinding;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.help.DataBase;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {
    private final ArrayList<PopularDomain> items;
    private final Context context;

    public PopularAdapter(Context context, ArrayList<PopularDomain> items) {
        this.items = items;
        this.context = context;
    }

    public void updateItems(List<PopularDomain> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderPupListBinding binding = ViewholderPupListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {
        PopularDomain item = items.get(position);

        Log.d("RecyclerView", "Item: " + item.getTitle());
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.feeTxt.setText("$" + item.getPrice());
        holder.binding.scoreTxt.setText("" + item.getScore());
        holder.binding.reviewTxt.setText("" + item.getReview());

        int drawableResourceId = holder.itemView.getResources().getIdentifier(
                item.getPicUrl(),
                "drawable",
                context.getPackageName()
        );

        Glide.with(context)
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.binding.pic);

        // Устанавливаем обработчик клика
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        final ViewholderPupListBinding binding;

        public Viewholder(ViewholderPupListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}