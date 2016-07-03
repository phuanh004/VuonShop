package com.example.anhpham.vuonshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by Anh Pham on 23/06/2016.
 */

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.MyViewHolder>{
    private Context mContext;
    private List<Product> productList;
    private Realm realm;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        Button overflowDelete;

        MyViewHolder(View view) {
            super(view);
            productName = (TextView)view.findViewById(R.id.productName);
            productPrice = (TextView)view.findViewById(R.id.productPrice);
            productImage = (ImageView) view.findViewById(R.id.productImage);
            overflowDelete = (Button) view.findViewById(R.id.overflowDelete);

            RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
            Realm.setDefaultConfiguration(realmConfig);
            realm = Realm.getDefaultInstance();
        }
    }

    public CheckOutAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public CheckOutAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card_checkout, parent, false);
        return new CheckOutAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckOutAdapter.MyViewHolder holder, int position) {
        final int pst = position;

        final Product product = productList.get(position);

        final int id = product.getId();
        final String name = product.getName();
        final String image = product.getImage();
        final String description = product.getDescription();
        final float price = product.getRegularPrice();

        holder.productName.setText(name);
        holder.productPrice.setText(PriceFormat.formatDecimal(price) + " ₫");
        holder.overflowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("s", pst +"");
                RealmResults<ProductInCart> result = realm.where(ProductInCart.class).equalTo("position", pst).findAll();
                Log.d("a", result.toString());
                realm.beginTransaction();
                result.deleteFirstFromRealm();
                realm.commitTransaction();
                productList.remove(pst);
                notifyItemRemoved(pst);
//                notifyItemRangeChanged(pst, productList.size());
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
