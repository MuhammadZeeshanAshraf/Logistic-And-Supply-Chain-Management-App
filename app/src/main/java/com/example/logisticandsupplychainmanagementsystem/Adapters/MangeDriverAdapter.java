package com.example.logisticandsupplychainmanagementsystem.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;


import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.EditProductActivity;
import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.ProductDetailActivity;
import com.example.logisticandsupplychainmanagementsystem.Models.Driver;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class MangeDriverAdapter extends RecyclerView.Adapter<MangeDriverAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Driver> list;
    private Activity activity;

    DatabaseReference rootRef;

    public MangeDriverAdapter(Context context, ArrayList<Driver> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    @NonNull
    @Override
    public MangeDriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MangeDriverAdapter.ViewHolder holder, int position) {
        final Driver model = list.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("Product", model);
//                context.startActivity(intent);
            }
        });


//        holder.more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showMenu(view, model);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView no, name;
        ImageView more;
        RoundedImageView preview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.product_no);
            name = itemView.findViewById(R.id.product_name);
            more = itemView.findViewById(R.id.product_more);
            preview = itemView.findViewById(R.id.product_preview_image);
        }
    }

    public void showMenu(View view, final Driver model) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.inflate(R.menu.manage_product_menu);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cat_details:
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("Product", model);
                        context.startActivity(intent);
                        break;
                    case R.id.cat_edit:
                        Intent i = new Intent(context, EditProductActivity.class);
                        i.putExtra("Product", model);
                        context.startActivity(i);
                        activity.finish();
                        break;
                    case R.id.cat_del:
//                        DeleteProduct(model);
                        break;

                }

                return true;
            }
        });

    }

//    private void DeleteProduct(Driver model)
//    {
//        rootRef.child("Products").child(model.getCategory()).child(model.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                {
//                    Toasty.info(context , "Product item Deleted Successfully..." , Toasty.LENGTH_SHORT).show();
//                    activity.finish();
//                }else
//                {
//                    Toasty.error(context , "Some Problem occur while Deleting..." , Toasty.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }



}

