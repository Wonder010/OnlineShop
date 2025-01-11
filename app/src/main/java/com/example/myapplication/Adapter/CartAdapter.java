package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.databinding.ViewholderCartBinding;
import com.example.myapplication.databinding.ViewholderPupListBinding;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.help.ChangeNumberItemsListener;
import com.example.myapplication.help.ManagmentCart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    private ArrayList<PopularDomain> items;
    private Context context;
    private ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(Context context, ArrayList<PopularDomain> items, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items;
        this.context = context;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding = ViewholderCartBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        PopularDomain item = items.get(position);
        holder.binding.titleTxt.setText(item.getTitle());
        holder.binding.feeEachItem.setText("$" + item.getPrice());
        holder.binding.totalEachItem.setText("$" + Math.round(item.getNumberInCart() * item.getPrice()));
        holder.binding.numberItemTxt.setText(String.valueOf(item.getNumberInCart()));

        int drawableResource = context.getResources().getIdentifier(item.getPicUrl(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(drawableResource)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.binding.pic);

        holder.binding.plusCartBtn.setOnClickListener(v -> {
            managmentCart.plusNumberItem(items, position, () -> {
                items.clear();
                items.addAll(managmentCart.getListCart());
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });

        holder.binding.minusCartBtn.setOnClickListener(v -> {
            managmentCart.minusNumberItem(items, position, () -> {
                items.clear();
                items.addAll(managmentCart.getListCart());
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;

        public Viewholder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

