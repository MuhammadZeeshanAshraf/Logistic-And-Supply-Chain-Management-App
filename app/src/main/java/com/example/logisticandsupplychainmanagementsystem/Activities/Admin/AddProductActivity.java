package com.example.logisticandsupplychainmanagementsystem.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.logisticandsupplychainmanagementsystem.Adapters.ManageCategoryAdapter;
import com.example.logisticandsupplychainmanagementsystem.Models.Category;
import com.example.logisticandsupplychainmanagementsystem.R;
import com.example.logisticandsupplychainmanagementsystem.databinding.ActivityAddProductBinding;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;

public class AddProductActivity extends AppCompatActivity {

    ActivityAddProductBinding binding;
    DatabaseReference rootRef;
    Uri productUri;
    StorageReference ProductImageReference;
    ArrayList<String> categoryList;
    String category, productUriString;
    AlertDialog waitingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        showLoadingDialog();
        init();
        getCategories();
        setListener();


        binding.CategorySpinner.setItems(categoryList);
        binding.CategorySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Toast.makeText(AddProductActivity.this, ""+ item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        productUri = null;
        rootRef = FirebaseDatabase.getInstance().getReference();
        ProductImageReference = FirebaseStorage.getInstance().getReference().child("ProductsImages");
        Wave wave = new Wave();
        binding.LoadingBar.setIndeterminateDrawable(wave);
        binding.LoadingBar.setVisibility(View.INVISIBLE);
        categoryList = new ArrayList<>();
        categoryList.add("Select Category");
        category = "";
        productUriString = "";


    }


    private void setListener() {
        binding.AddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckFields();
            }
        });

        binding.itemCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(AddProductActivity.this);
            }
        });




    }

    private void CheckFields() {
        try {


            final String name = binding.itemName.getText().toString();
            final String desc = binding.itemDesc.getText().toString();
            final String cost = binding.itemCost.getText().toString();
            final String time = binding.itemTime.getText().toString();
            final String stock = binding.itemStock.getText().toString();


            if (TextUtils.isEmpty(name)) {
                binding.itemName.setError("Name Required");
                Toasty.error(AddProductActivity.this, "Enter Product Name", Toasty.LENGTH_SHORT).show();

            } else if (category.equals("")) {
                Toasty.error(AddProductActivity.this, "Select Product Category", Toasty.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(cost)) {
                binding.itemCost.setError("Cost Required");
                Toasty.error(AddProductActivity.this, "Enter the cost of product item", Toasty.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(time)) {
                binding.itemTime.setError("Time Required");
                Toasty.error(AddProductActivity.this, "Enter the time to get the product item deliver", Toasty.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(stock)) {
                binding.itemTime.setError("Time Required");
                Toasty.error(AddProductActivity.this, "Enter the stock of product", Toasty.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(desc)) {
                binding.itemDesc.setError("Description Required");
                Toasty.error(AddProductActivity.this, "Enter Product Item Description", Toasty.LENGTH_SHORT).show();

            } else {
                if (!(TextUtils.isEmpty(name))) {
                    if (!(TextUtils.isEmpty(desc))) {
                        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                        ConnectivityManager cm =
                                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();


                        if (mWifi.isConnected() || isConnected) {


                            if (productUri.equals(Uri.EMPTY)) {
                                Toasty.error(AddProductActivity.this, "Select Preview Image of the product item", Toasty.LENGTH_SHORT).show();

                            } else {
                                AddItemToDatabase(name, desc, cost, time, stock);
                            }

                        } else {
                            Toasty.warning(AddProductActivity.this, "Check Your Internet ! Make Sure Your are Connected to Internet ", Toasty.LENGTH_SHORT).show();
                        }


                    }

                }
            }

        } catch (Exception e) {
            binding.LoadingBar.setVisibility(View.GONE);
            if (productUriString.equals("")) {
                Toasty.error(AddProductActivity.this, "Select Preview Image of the product item", Toasty.LENGTH_SHORT).show();

            } else {
                Toasty.error(AddProductActivity.this, "Some Problem happen will adding the item...!", Toasty.LENGTH_SHORT).show();

            }

        }
    }

    private void AddItemToDatabase(String name, String desc, String cost, String time, String stock) {

        binding.LoadingBar.setVisibility(View.VISIBLE);
        File actualImage = new File(productUri.getPath());

        try {

            Bitmap compressedImage = new Compressor(this)
                    .setMaxWidth(300)
                    .setMaxHeight(300)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToBitmap(actualImage);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] final_image = baos.toByteArray();

            final StorageReference filePath = ProductImageReference.child(productUri.getLastPathSegment() + ".jpg");

            UploadTask uploadTask = filePath.putBytes(final_image);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DatabaseReference userKeyRef = rootRef.child("Products").child(category).push();

                            final String PushID = userKeyRef.getKey();

                            Map MessageMap = new HashMap<>();
                            MessageMap.put("ID", PushID);
                            MessageMap.put("Name", name);
                            MessageMap.put("Description", desc);
                            MessageMap.put("Cost", cost);
                            MessageMap.put("Time", time);
                            MessageMap.put("Uri", uri + "");
                            MessageMap.put("Category", category);
                            MessageMap.put("Stock", stock);

                            rootRef.child("Products").child(category).child(PushID).updateChildren(MessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toasty.info(AddProductActivity.this, "Product is added Successfully.....", Toasty.LENGTH_SHORT).show();

                                        finish();
                                    } else {
                                        binding.LoadingBar.setVisibility(View.GONE);
                                        Toasty.error(AddProductActivity.this, "Some Problem happen will adding the Product...!", Toasty.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }
                    });

                }
            });


        } catch (Exception error) {
            binding.LoadingBar.setVisibility(View.GONE);
            Toasty.error(AddProductActivity.this, "Some Problem happen will adding Product...!", Toasty.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                productUri = result.getUri();
                productUriString = productUri.toString();


                File actualImage = new File(productUri.getPath());

                Bitmap compressedImage = null;
                try {
                    compressedImage = new Compressor(this)
                            .setMaxWidth(250)
                            .setMaxHeight(250)
                            .setQuality(50)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .compressToBitmap(actualImage);
                    binding.itemPreviewImage.setImageBitmap(Bitmap.createScaledBitmap(compressedImage, 256, 256, true));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toasty.error(AddProductActivity.this, "" + error, Toasty.LENGTH_SHORT).show();

            }
        }
    }

    private void getCategories() {
        rootRef.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String key = snapshot.getKey();

                    if (key != null) {
                        rootRef.child("Categories").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.child("Name").getValue().toString();

                                categoryList.add(name);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

                waitingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
        //setting up the layout for alert dialog
        View view = LayoutInflater.from(AddProductActivity.this).inflate(R.layout.dialog_please_wait, null, false);
        builder.setView(view);
        waitingDialog = builder.create();
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.setCancelable(false);
        waitingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        waitingDialog.show();
    }
}