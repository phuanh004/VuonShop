package com.example.anhpham.vuonshop.Tab;


import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.anhpham.vuonshop.Adapter.GridSpacingItemDecoration;
import com.example.anhpham.vuonshop.Adapter.ProductsAdapter;
import com.example.anhpham.vuonshop.Model.Product;
import com.example.anhpham.vuonshop.Model.RestAPI;
import com.example.anhpham.vuonshop.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WomenFragment extends Fragment {

    private String url = "";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private ProductsAdapter productsAdapter;
    private List<Product> productList;

    public WomenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_women, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestAPI restAPI = new RestAPI();

        url = restAPI.getHost() + restAPI.getPath() + "products?filter[product_cat]=nu&" + restAPI.getKey();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewWomen);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        productList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(getContext(), productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new MyTask().execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private class MyTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... params) {
            JSONArray Jobject = null;
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response respone = client.newCall(request).execute();
                String result = respone.body().string();

                Jobject = new JSONArray(result);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return Jobject;
        }


        @Override
        protected void onPostExecute(JSONArray result) {
            if(result != null) {
                Float salePrice = Float.parseFloat(String.valueOf(0));
                for (int i = 0; i < result.length(); i++) {
                    try {
                        if (result.getJSONObject(i).getString("sale_price") != null && !Objects.equals(result.getJSONObject(i).getString("sale_price"), "")) {
                            salePrice = Float.parseFloat(result.getJSONObject(i).getString("sale_price"));
                        }
                        Product product = new Product(result.getJSONObject(i).getInt("id"), result.getJSONObject(i).getString("name"),  result.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("src"), result.getJSONObject(i).getString("description"), Float.parseFloat(result.getJSONObject(i).getString("price")), salePrice);
                        productList.add(product);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(productsAdapter);
            }else{
                Snackbar.make(getView(), "Không có dữ liệu", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void refreshItems() {
        // Load items
        // ...
        productList.clear();
        productsAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);

        new MyTask().execute();

        // Load complete
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }
}
