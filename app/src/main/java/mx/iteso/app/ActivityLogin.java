package mx.iteso.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mx.iteso.app.databinding.ActivityLoginBinding;

import static mx.iteso.app.utils.Constants.LGD_PREFERENCE;
import static mx.iteso.app.utils.Constants.NAME_PREFERENCE;
import static mx.iteso.app.utils.Constants.PWD_PREFERENCE;
import static mx.iteso.app.utils.Constants.USER_PREFERENCES;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.btnLogInActivityLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_in_activity_login:
                saveUser();
                Intent intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void saveUser() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_PREFERENCE, getString(mBinding.etUsernameActivityLogin));
        editor.putString(PWD_PREFERENCE, getString(mBinding.etPasswordActivityLogin));
        editor.putBoolean(LGD_PREFERENCE, true);
        editor.apply();
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }
}
