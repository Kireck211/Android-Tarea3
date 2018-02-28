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

import mx.iteso.app.AdapterProduct;
import mx.iteso.app.R;
import mx.iteso.app.beans.ItemProduct;

public class FragmentTechnology extends Fragment {
    public FragmentTechnology () {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.fragment_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemProduct> products = new ArrayList<>();
        String[] laptops = getResources().getStringArray(R.array.laptops);
        String location = getString(R.string.location);
        String phone = getString(R.string.phone);
        String store = getString(R.string.store);
        int[] images = new int[]{R.drawable.mac, R.drawable.alienware, R.drawable.lanix};
        for(int i = 0; i < laptops.length; i++)
            products.add(new ItemProduct(laptops[i], store, phone, location, images[i]));

        AdapterProduct adapterProduct = new AdapterProduct(products);
        recyclerView.setAdapter(adapterProduct);

        return rootView;
    }
}
