package com.example.rodneytressler.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.Network.RestClient;
import com.example.rodneytressler.peoplemon.Network.UserStore;
import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Stages.MapViewStage;
import com.example.rodneytressler.peoplemon.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodneytressler on 11/7/16.
 */

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.login_button_field)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void showRegisterView() {
        Flow flow = PeopleMonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build(); //pushes on the stack
        flow.setHistory(newHistory, Flow.Direction.FORWARD); //sets new direction and history forward.
    }

    @OnClick(R.id.login_button_field)
    public void login() {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0); //makes keyboard drop off of screen...

        String email = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String grantType = "password";

        Auth auth = new Auth(grantType, email, password);
        RestClient restClient = new RestClient();
        restClient.getApiService().login(email, password, grantType).enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                resetView();
                Auth authUser = response.body();
                UserStore.getInstance().setToken(authUser.getAccessToken());
                UserStore.getInstance().setTokenExpiration(authUser.getExpires());

                Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show();

                Flow flow = PeopleMonApplication.getMainFlow();
                History newHistory = History.single(new MapViewStage());
                flow.setHistory(newHistory, Flow.Direction.FORWARD);
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                resetView();
            }
        });
    }

    private void resetView() {
        loginButton.setEnabled(true); //allows the login button to be clickable again if password is incorrect.
        registerButton.setEnabled(true); //allows the register button to be clickable again if password is incorrect.
        spinner.setVisibility(GONE);
    }

}
