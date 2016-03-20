package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.etUsername)
    EditText username;
    @Bind(R.id.etPassword)
    EditText password;
    @Bind(R.id.tvLoginError)
    TextView error;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbActorToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        user = User.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void login_Click(View view) {
        login();
    }

    public void signUp_Click(View view) {
        signup();
    }

    public void resetPassword_Click(View view) {
        resetpassword();
    }

    private void login() {
        View focusView = this.getCurrentFocus();
        if (focusView != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }

        if (AppHelper.isOnline()) {
            user.login(username.getText().toString(), password.getText().toString(), this, error);
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.clLoginCoordinator), "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction("Refresh", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
            snackbar.show();
        }
    }

    private void signup () {
        if (AppHelper.isOnline()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/account/signup?language=en"));
            startActivity(browserIntent);
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.clLoginCoordinator), "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction("Refresh", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signup();
                }
            });
            snackbar.show();
        }
    }

    private void resetpassword() {
        if (AppHelper.isOnline()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/account/reset-password"));
            startActivity(browserIntent);
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.clLoginCoordinator), "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction("Refresh", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetpassword();
                }
            });
            snackbar.show();
        }
    }
}
