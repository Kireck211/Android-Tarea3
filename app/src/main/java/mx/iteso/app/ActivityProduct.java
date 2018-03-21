package mx.iteso.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mx.iteso.app.beans.ItemProduct;
import mx.iteso.app.databinding.ActivityProductBinding;

import static mx.iteso.app.utils.Constants.FRAGMENT_INTENT;
import static mx.iteso.app.utils.Constants.ITEM_INTENT;

public class ActivityProduct extends AppCompatActivity implements View.OnClickListener {

    private ActivityProductBinding mBinding;
    private ItemProduct mItemProduct;
    private int mSelectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        Intent intent = getIntent();
        if (intent != null) {
            mItemProduct = getIntent().getParcelableExtra(ITEM_INTENT);
            if (mItemProduct != null) {
                if (intent.getExtras() != null)
                    mSelectedFragment = intent.getExtras().getInt(FRAGMENT_INTENT);
                /*mBinding.tiNameActivityProduct.setText(mItemProduct.getTitle());
                mBinding.tiStoreActivityProduct.setText(mItemProduct.getStore());
                mBinding.tiPhoneActivityProduct.setText(mItemProduct.getPhone());
                mBinding.tiLocationActivityProduct.setText(mItemProduct.getLocation());
                mBinding.ivProductActivityProduct.setImageResource(mItemProduct.getImage());
                mBinding.btnSaveActivityProduct.setOnClickListener(this);
                mBinding.btnCancelActivityProduct.setOnClickListener(this);*/
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_activity_product:
                Intent intent = new Intent();
                /*ItemProduct itemProduct = new ItemProduct(
                        getString(mBinding.tiNameActivityProduct),
                        getString(mBinding.tiStoreActivityProduct),
                        getString(mBinding.tiPhoneActivityProduct),
                        getString(mBinding.tiLocationActivityProduct),
                        mItemProduct.getImage(),
                        mItemProduct.getCode()
                );
                intent.putExtra(ITEM_INTENT, itemProduct);
                intent.putExtra(FRAGMENT_INTENT, mSelectedFragment);
                setResult(RESULT_OK, intent);*/
                finish();
                break;
            case R.id.btn_cancel_activity_product:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    private String getString(TextInputEditText textInputEditText) {
        return textInputEditText.getText().toString();
    }
}