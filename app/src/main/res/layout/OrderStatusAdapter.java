package com.example.kingofsheba.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kingofsheba.Models.Order;
import com.example.kingofsheba.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> list;
    private Activity activity;

    DatabaseReference rootRef;

    public OrderStatusAdapter(Context context, ArrayList<Order> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    @NonNull
    @Override
    public OrderStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderStatusAdapter.ViewHolder holder, int position) {
        final Order model = list.get(position);

//        holder.name.setText(model.getFoodName());
//        holder.status.setText("Order Status : " + model.getStatus());
//        holder.orderBy.setText(model.getOrderBy());
//        holder.date.setText(model.getDate());
//        holder.time.setText(model.getTime());
//
//        Picasso.get().load(model.getUri()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name , status, orderBy , date , time;
        RoundedImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_order_name);
            status = itemView.findViewById(R.id.item_order_status);
            orderBy = itemView.findViewById(R.id.item_order_by);
            date = itemView.findViewById(R.id.item_order_day);
            time = itemView.findViewById(R.id.item_order_time);
            imageView = itemView.findViewById(R.id.item_order_image);

        }
    }


}

