package com.example.anhpham.vuonshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhpham.vuonshop.Model.Product;
import com.example.anhpham.vuonshop.Model.ProductInCart;
import com.example.anhpham.vuonshop.MyCheck.PriceFormat;
import com.example.anhpham.vuonshop.ProductActivity;
import com.example.anhpham.vuonshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Anh Pham on 18/06/2016.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> productList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        Button overflow;

        MyViewHolder(View view) {
            super(view);
            productName = (TextView)view.findViewById(R.id.productName);
            productPrice = (TextView)view.findViewById(R.id.productPrice);
            productImage = (ImageView) view.findViewById(R.id.productImage);
            overflow = (Button) view.findViewById(R.id.overflow);
        }
    }

    public ProductsAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final int pst = position;

        final Product product = productList.get(pst);

        final int id = product.getId();
        final String name = product.getName();
        final String image = product.getImage();
        final String description = product.getDescription();
        final float price = product.getRegularPrice();

        holder.productName.setText(name);
        holder.productPrice.setText(PriceFormat.formatDecimal(price) + " ₫");
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Đã thêm " + '"' + name + '"' + " vào giỏ hàng !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
                Realm.setDefaultConfiguration(realmConfig);
                Realm realm = Realm.getDefaultInstance();
                RealmResults<ProductInCart> result = realm.where(ProductInCart.class).findAll();

                ProductInCart productInCart = new ProductInCart();
                if(result.max("position") == null) {
                    productInCart.setPosition(0);
                }else {
                    productInCart.setPosition(result.max("position").intValue() + 1);
                }
                productInCart.setId(id);
                productInCart.setName(name);
                productInCart.setImage(image);
                productInCart.setDescription(description);
                productInCart.setRegularPrice(price);

                realm.beginTransaction();
                realm.copyToRealm(productInCart);
                realm.commitTransaction();
            }
        });
        Picasso.with(mContext).load(image).into(holder.productImage);
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("image", image);
                intent.putExtra("price", price);
                intent.putExtra("description", description);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
