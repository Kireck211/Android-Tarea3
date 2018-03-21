package mx.iteso.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import mx.iteso.app.beans.Category;
import mx.iteso.app.beans.ItemProduct;
import mx.iteso.app.beans.Store;
import mx.iteso.app.database.CategoryControl;
import mx.iteso.app.database.DataBaseHandler;
import mx.iteso.app.database.ItemProductControl;
import mx.iteso.app.database.StoreControl;
import mx.iteso.app.databinding.ActivityItemBinding;

import static mx.iteso.app.utils.Constants.ITEM_INTENT;

public class ActivityItem extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Debug " + ActivityItem.class.getSimpleName();

    private static final int FIRST = 0;

    private ActivityItemBinding mBinding;
    private ArrayAdapter<String> mDrawablesAdapter;
    private ArrayAdapter<String> mCategoryAdapter;
    private ArrayAdapter<String> mStoreAdapter;
    private String[] mDrawables;
    private ArrayList<Category> mCategories;
    private ArrayList<Store> mStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance(this);

            mBinding = DataBindingUtil.setContentView(this, R.layout.activity_item);

            mDrawables = getResources().getStringArray(R.array.drawables);
            String[] drawables = new String[mDrawables.length];
            for(int i = 0; i < mDrawables.length; i++)
                mDrawables[i] = mDrawables[i].substring(mDrawables[i].lastIndexOf("/") + 1, mDrawables[i].indexOf("."));
            mDrawablesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mDrawables);
            mBinding.spImageActivityItem.setAdapter(mDrawablesAdapter);
            mBinding.spImageActivityItem.setSelection(FIRST);

            mCategories =  CategoryControl.getCategories(dataBaseHandler);
            String[] categoryNames = new String[mCategories.size()];
            for(int i = 0; i < mCategories.size(); i++)
                categoryNames[i] = mCategories.get(i).toString();
            mCategoryAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categoryNames);
            mBinding.spCategoryActivityItem.setAdapter(mCategoryAdapter);
            mBinding.spCategoryActivityItem.setSelection(FIRST);

            mStores = StoreControl.getStores(dataBaseHandler);
            String[] storeNames = new String[mStores.size()];
            for(int i = 0; i < mStores.size(); i++)
                storeNames[i] = mStores.get(i).toString();
            mStoreAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, storeNames);
            mBinding.spStoresActivityItem.setAdapter(mStoreAdapter);
            mBinding.spStoresActivityItem.setSelection(FIRST);

            mBinding.btnSaveActivityItem.setOnClickListener(this);
        }
    }

    private void saveItemProduct() {
        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance(this);

        ItemProduct itemProduct = new ItemProduct();
        itemProduct.setTitle(mBinding.tiTitleActivityItem.getText().toString());
        itemProduct.setDescription("");
        int selectedImage = mBinding.spImageActivityItem.getSelectedItemPosition();
        int resourceImageId = getResources().getIdentifier(mDrawables[selectedImage], "drawable", getPackageName());
        itemProduct.setImage(resourceImageId);
        int selectedStore = mBinding.spStoresActivityItem.getSelectedItemPosition();
        itemProduct.setStore(mStores.get(selectedStore));
        int selectedCategory = mBinding.spCategoryActivityItem.getSelectedItemPosition();
        itemProduct.setCategory(mCategories.get(selectedCategory));

        if (!ItemProductControl.addItemProduct(itemProduct, dataBaseHandler)) {
            Log.e(TAG, "Error while creating product");
        }

        Intent intent = new Intent();
        intent.putExtra(ITEM_INTENT, itemProduct);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_activity_item:
                saveItemProduct();
                finish();
                break;
        }
    }
}
