package com.iwk.yang.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.iwk.yang.impl.UserDaoImpl;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.tool.UrlAppendParameter;
import com.iwk.yang.volley.MyJsonObjectRequest;
import com.user.entity.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via nickname/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private AutoCompleteTextView login_nickname;
    private EditText login_pwd;
    private View mProgressView;
    private View mLoginFormView;

    private RequestQueue mRequestQueue;//请求栈
    private ToastShow toastShow;

    private UserDaoImpl userDao;
    private User oldUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("this=========="+this);
        //实例化数据库操作
        userDao = new UserDaoImpl(this);
        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);
        toastShow = new ToastShow(this);
        // Set up the login form.
        login_nickname = (AutoCompleteTextView) findViewById(R.id.login_nickname);
        populateAutoComplete();

        login_pwd = (EditText) findViewById(R.id.login_password);
        login_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btn_login = (Button) findViewById(R.id.email_sign_in_button);
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        initData();
    }
    //取本地数据填用户信息
    private void initData() {
        User user=userDao.listOneUser(1);
        if (user!=null){
            login_nickname.setText(user.getName());
            login_pwd.setText(user.getPwd());
            oldUser=user;
        }

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(login_nickname, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid nickname, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        login_nickname.setError(null);
        login_pwd.setError(null);

        // Store values at the time of the login attempt.
        String nickname = login_nickname.getText().toString();
        String password = login_pwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            login_pwd.setError(getString(R.string.error_field_required));
            focusView = login_pwd;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            login_pwd.setError(getString(R.string.error_invalid_password));
            focusView = login_pwd;
            cancel = true;
        }

        // Check for a valid nickname address.
        if (TextUtils.isEmpty(nickname)) {
            login_nickname.setError(getString(R.string.error_field_required));
            focusView = login_nickname;
            cancel = true;
        } else if (!isEmailValid(nickname)) {
            login_nickname.setError(getString(R.string.error_invalid_email));
            focusView = login_nickname;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // 登陆
            showProgress(true);
            login(nickname, password);
        }
    }

    private boolean isEmailValid(String nickname) {
        //TODO: Replace this with your own logic
        return nickname.length() > 0;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only nickname addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary nickname addresses first. Note that there won't be
                // a primary nickname address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        login_nickname.setAdapter(adapter);
    }


    /**
     * 加载视频列表
     */
    public void login(String nickname, String password) {
        //获取视频列表action
        String url = getResources().getText(R.string.serverURL) + "wUser_queryUserByName";
        Map<String, String> map = new HashMap<>();
        map.put("name", nickname.trim());
        map.put("pwd", password.trim());
        String paramstr = UrlAppendParameter.appendParameter(url, map);
        MyJsonObjectRequest loginRequest = new MyJsonObjectRequest(url,
                paramstr,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        try {
                            JSONObject data = null;
                            if (!response.get("jsonStr").equals(null))
                                data = new JSONObject(response.get("jsonStr").toString());
                            System.out.println(data);
                            if (data != null) {
                                //更新数据库数据
                                userDao.deleteUser(oldUser);
                                //插入本地数据库
                                User user = new User(Long.parseLong(data.get("id").toString()),
                                        data.get("icons").toString(),
                                        data.get("name").toString(),
                                        data.get("pwd").toString(),

                                        data.get("question").toString(),
                                        data.get("answer").toString(),
                                        data.get("school").toString(),

                                        data.get("college").toString(),
                                        data.get("professional").toString(),

                                        data.get("clazz").toString(),
                                        data.get("sno").toString(),
                                        data.get("sname").toString(),
                                        data.get("ssex").toString(),
                                        data.get("email").toString(),
                                        data.get("introduce").toString()
                                );

                                System.out.println(user.getName());
                                userDao.insertUser(user);
                                System.out.println("datanickname==" + data.get("icons").toString());
                                //登陆成功
                                Intent loginIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("nickname", data.get("name").toString());
                                bundle.putString("introduce", data.get("introduce").toString());
                                loginIntent.putExtra("data", bundle);
                                setResult(1, loginIntent);
                                finish();
                            } else {
                                login_pwd.setError(getString(R.string.error_incorrect_password));
                                login_pwd.requestFocus();
                            }
                        } catch (Exception e) {
                            showProgress(false);
                            Log.e("queryPageNumjson解析异常：", e.getMessage(), e);
                            toastShow.makeText("json解析异常", Toast.LENGTH_LONG);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);
                        Log.e("请求异常：", error.getMessage(), error);
                        toastShow.makeText("网络或者服务器异常", Toast.LENGTH_LONG);

                    }
                }
        );
        //将StringRequest添加到RequestQueue
        mRequestQueue.add(loginRequest);

    }

    @Override
    protected void onStop() {
        super.onStop();

        mRequestQueue.cancelAll(null);
    }
}

