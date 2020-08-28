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
import com.example.logisticandsupplychainmanagementsystem.Models.Product;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MangeProductAdapter extends RecyclerView.Adapter<MangeProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private Activity activity;

    DatabaseReference rootRef;

    public MangeProductAdapter(Context context, ArrayList<Product> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    @NonNull
    @Override
    public MangeProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MangeProductAdapter.ViewHolder holder, int position) {
        final Product model = list.get(position);

        holder.name.setText(model.getName());
        holder.no.setText((position+ 1)+ ".");

        Picasso.get().load(model.getUri()).placeholder(R.drawable.add_product).error(R.drawable.add_product).into(holder.preview);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("Product", model);
                context.startActivity(intent);
            }
        });


        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view, model);
            }
        });


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

    public void showMenu(View view, final Product model) {
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
                        DeleteProduct(model);
                        break;

                }

                return true;
            }
        });

    }

    private void DeleteProduct(Product model)
    {
        rootRef.child("Products").child(model.getCategory()).child(model.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toasty.info(context , "Product item Deleted Successfully..." , Toasty.LENGTH_SHORT).show();
                    activity.finish();
                }else
                {
                    Toasty.error(context , "Some Problem occur while Deleting..." , Toasty.LENGTH_SHORT).show();
                }
            }
        });

    }



}

