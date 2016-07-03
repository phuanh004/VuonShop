package com.example.anhpham.vuonshop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhpham.vuonshop.Model.RestAPI;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    private String url = "";
    private int id;
    private String name, image, description;
    private float regularPrice, salePrice;

    private ImageView imageView;
    private TextView productName, productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.backdrop);
        productName = (TextView) findViewById(R.id.productNameInfo);
        productDescription = (TextView) findViewById(R.id.productDescriptionInfo);

        if (getIntent().getExtras() != null) {
            Bundle product = getIntent().getExtras();
            id = product.getInt("id");
            name = product.getString("name");
            image = product.getString("image");
            description = product.getString("description").replaceAll("<[^>]*>", "").trim();
            regularPrice = product.getFloat("price");

            getSupportActionBar().setTitle(name);
            Picasso.with(ProductActivity.this).load(image).into(imageView);
            productName.setText(name);
            productDescription.setText(description);
        }

//        Toast.makeText(ProductActivity.this, id+"id ", Toast.LENGTH_SHORT).show();
        RestAPI restAPI = new RestAPI();
        url = restAPI.getHost() + restAPI.getPath() + "products/" + id + "?" + restAPI.getKey();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
