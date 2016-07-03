package com.example.anhpham.vuonshop;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anhpham.vuonshop.Adapter.CheckOutAdapter;
import com.example.anhpham.vuonshop.Adapter.GridSpacingItemDecoration;
import com.example.anhpham.vuonshop.Model.Product;
import com.example.anhpham.vuonshop.Model.ProductInCart;
import com.example.anhpham.vuonshop.MyCheck.PriceFormat;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CheckOutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckOutAdapter productsAdapter;
    private List<Product> productList;
    private TextView textViewCheckOutPrice;
    private Button btnFinish;
    private float total;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewCheckOut);
        textViewCheckOutPrice = (TextView) findViewById(R.id.textViewCheckOutPrice);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        productList = new ArrayList<>();
        productsAdapter = new CheckOutAdapter(CheckOutActivity.this, productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(CheckOutActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(CheckOutActivity.this).build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        RealmQuery<ProductInCart> query = realm.where(ProductInCart.class);
        RealmResults<ProductInCart> results = query.findAll();

        total = results.sum("regularPrice").floatValue();

        for (int i=0; i<results.size(); i++){
            Product product = new Product(results.get(i).getId(), results.get(i).getName(), results.get(i).getImage(),
                    results.get(i).getDescription(), results.get(i).getRegularPrice(), results.get(i).getSalePrice());
            productList.add(product);
        }

        total = results.sum("regularPrice").floatValue();
        textViewCheckOutPrice.setText("Tổng cộng: "+ PriceFormat.formatDecimal(total) + " ₫");
        recyclerView.setAdapter(productsAdapter);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mua thành công ! Cảm ơn quý khách !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                RealmResults<ProductInCart> results = realm.where(ProductInCart.class).findAll();
                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();
                productList.clear();
                productsAdapter.notifyDataSetChanged();
                textViewCheckOutPrice.setText("Tổng cộng: 0 ₫");
            }
        });
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
