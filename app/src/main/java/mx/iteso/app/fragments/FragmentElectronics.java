package mx.iteso.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mx.iteso.app.ActivityMain;
import mx.iteso.app.AdapterProduct;
import mx.iteso.app.R;
import mx.iteso.app.beans.ItemProduct;
import mx.iteso.app.database.DataBaseHandler;
import mx.iteso.app.database.ItemProductControl;

public class FragmentElectronics extends Fragment {
    private AdapterProduct mAdapter;
    private ArrayList<ItemProduct> products;

    public FragmentElectronics() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_electronics, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        DataBaseHandler dh = DataBaseHandler.getInstance(getContext());

        if (products == null)
            products = ItemProductControl.getItemProductsByCategory(DataBaseHandler.ELECTRONICS_CATEGORY, dh);

        if (mAdapter == null)
            mAdapter = new AdapterProduct(products);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void onChangeItem(ItemProduct itemProduct) {
        final int code = itemProduct.getCode();
        if (code < this.products.size()) {
            this.products.set(code, itemProduct);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addItem(ItemProduct itemProduct) {
        this.products.add(itemProduct);
        mAdapter.notifyDataSetChanged();
    }
}
