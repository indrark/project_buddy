package com.njit.buddy.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.*;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.njit.buddy.app.entity.Profile;
import com.njit.buddy.app.network.Connector;
import com.njit.buddy.app.network.task.LoginTask;
import com.njit.buddy.app.network.task.ProfileViewTask;
import com.njit.buddy.app.util.EmailValidator;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    // UI references.
    private EditText m_email;
    private EditText m_password;
    private View progress_view;
    private View login_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        m_email = (EditText) findViewById(R.id.email);

        m_password = (EditText) findViewById(R.id.password);
        m_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        login_form = findViewById(R.id.login_form);
        progress_view = findViewById(R.id.login_progress);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegisterPage();
            }
        });
    }

    public void gotoRegisterPage() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void gotoBuddyPage() {
        Intent intent = new Intent(this, BuddyActivity.class);
        startActivity(intent);
        showProgress(false);
        finish();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        String email = m_email.getText().toString();
        String password = m_password.getText().toString();

        // Reset errors.
        m_email.setError(null);
        m_password.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            m_email.setError(getString(R.string.error_field_required));
            focusView = m_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            m_email.setError(getString(R.string.error_invalid_email));
            focusView = m_email;
            cancel = true;
        }

        // Check for a non-empty password.
        if (TextUtils.isEmpty(password)) {
            m_password.setError(getString(R.string.error_field_required));
            focusView = m_password;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            attemptLogin(email, password);
        }
    }

    private void attemptLogin(String email, String password) {
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        new LoginTask() {
            @Override
            public void onSuccess(String token) {
                LoginActivity.this.onLoginSuccess(token);
            }

            @Override
            public void onFail(int error_code) {
                LoginActivity.this.onLoginFail(error_code);
            }
        }.execute(email, password);
    }

    public void onLoginSuccess(String token) {
        Connector.setAuthenticationToken(token);
        SharedPreferences preferences = getSharedPreferences("buddy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getResources().getString(R.string.key_token), token);
        editor.apply();
        ProfileViewTask task = new ProfileViewTask() {
            @Override
            public void onSuccess(Profile result) {
                onProfileSuccess(result);
            }

            @Override
            public void onFail(int error_code) {
                onLoginFail(error_code);
            }
        };
        task.execute(0);
    }

    public void onProfileSuccess(Profile profile) {
        SharedPreferences preferences = getSharedPreferences("buddy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getResources().getString(R.string.key_username), profile.getUsername());
        editor.putString(getResources().getString(R.string.key_username), profile.getUsername());
        editor.apply();
        gotoBuddyPage();
    }

    public void onLoginFail(int error_code) {
        showProgress(false);
        m_password.setError(getString(R.string.error_incorrect_password));
        m_password.requestFocus();
    }

    private boolean isEmailValid(String email) {
        EmailValidator validator = new EmailValidator();
        return validator.validate(email);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            login_form.setVisibility(show ? View.GONE : View.VISIBLE);
            login_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress_view.setVisibility(show ? View.VISIBLE : View.GONE);
            progress_view.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress_view.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress_view.setVisibility(show ? View.VISIBLE : View.GONE);
            login_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

}



