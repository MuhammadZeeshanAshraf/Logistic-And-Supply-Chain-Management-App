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

import com.example.logisticandsupplychainmanagementsystem.Activities.Admin.EditCategoryActivity;
import com.example.logisticandsupplychainmanagementsystem.Models.Category;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class  ManageCategoryAdapter extends RecyclerView.Adapter<ManageCategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> list;
    private Activity activity;

    DatabaseReference rootRef;

    public ManageCategoryAdapter(Context context, ArrayList<Category> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        rootRef = FirebaseDatabase.getInstance().getReference();

    }

    @NonNull
    @Override
    public ManageCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManageCategoryAdapter.ViewHolder holder, int position) {
        final Category model = list.get(position);

        holder.name.setText(model.getName());
        holder.no.setText((position+ 1)+ ".");



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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.item_cat_no);
            name = itemView.findViewById(R.id.item_cat_name);
            more = itemView.findViewById(R.id.item_cat_more);
        }
    }

    public void showMenu(View view, final Category model) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.inflate(R.menu.manage_category_menu);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.cat_edit:
                        Intent i = new Intent(context, EditCategoryActivity.class);
                        i.putExtra("Category", model);
                        context.startActivity(i);
                        activity.finish();
                        break;
                    case R.id.cat_del:
                        DeleteCategory(model);
                        break;

                }

                return true;
            }
        });

    }

    private void DeleteCategory(Category model)
    {
        rootRef.child("Categories").child(model.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toasty.info(context , "Category Deleted Successfully..." , Toasty.LENGTH_SHORT).show();
                    activity.finish();
                }else
                {
                    Toasty.error(context , "Some Problem occur while Deleting..." , Toasty.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void  SendUserToActivity(Activity activity)
    {
        Intent intent = new Intent(context, activity.getClass());
        context.startActivity(intent);
        activity.finish();
    }

}

