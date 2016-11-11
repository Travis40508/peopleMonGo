package com.example.rodneytressler.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rodneytressler.peoplemon.Models.Auth;
import com.example.rodneytressler.peoplemon.Network.RestClient;
import com.example.rodneytressler.peoplemon.PeopleMonApplication;
import com.example.rodneytressler.peoplemon.R;
import com.example.rodneytressler.peoplemon.Stages.LoginStage;

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

public class RegisterView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.confirm_field)
    EditText confirmPassword;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    @Bind(R.id.email_field)
    EditText emailField;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register() {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0); //makes keyboard drop off of screen...
        imm.hideSoftInputFromWindow(confirmPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);

        String fullName = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        String confirm = confirmPassword.getText().toString();
        String AvatarBase64 = "";
        String ApiKey = "iOSandroid301november2016";

        if (fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(context, "Please Fill out all fields",
                    Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please provide valid email",
                    Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirm)) {
            Toast.makeText(context, "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
        } else {
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            Auth account = new Auth(email, fullName, "", "iOSandroid301november2016", password);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(account).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
//                        Account regAccount = response.body();
//                        UserStore.getInstance().setToken(regAccount.getToken()); //different with peoplemon. not built-in.
//                        UserStore.getInstance().setTokenExpiration(regAccount.getExpires());
                        resetView();
                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Flow flow = PeopleMonApplication.getMainFlow();
                        History newHistory = History.single(new LoginStage());
                        flow.setHistory(newHistory, Flow.Direction.BACKWARD);

                    } else {
                        resetView();
                        Toast.makeText(context, "Registration Failed" + response.code(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    resetView();
                    Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
    private void resetView() {
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}
