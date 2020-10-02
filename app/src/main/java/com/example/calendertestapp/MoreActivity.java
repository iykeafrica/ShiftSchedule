package com.example.calendertestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendertestapp.api.SubmitFeedbackForm;
import com.example.calendertestapp.dialog.ErrorDialog;
import com.example.calendertestapp.dialog.SuccessfulDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.calendertestapp.api.SubmitFeedbackForm.BASE_URL;

public class MoreActivity extends AppCompatActivity {
    private static final String TAG = "MoreActivity";

    private EditText mFeedback;
    private TextView mAboutAppTitle, mAboutAppText, mFeedBackTitle;
    private Button mSubmitButton;
    public static String mStringFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        mAboutAppTitle = findViewById(R.id.text_about_app_title);
        mAboutAppText = findViewById(R.id.text_about_app);
        mFeedBackTitle = findViewById(R.id.text_feedback_title);
        mFeedback = findViewById(R.id.text_feedback);
        mSubmitButton = findViewById(R.id.submit_button);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStringFeedback = mFeedback.getText().toString().trim();

                if (!TextUtils.isEmpty(mStringFeedback)) {

                    if (isInternetAvailable(getApplicationContext())) {
                        Log.i(TAG, "Checking status..." + mStringFeedback);
                        submitFeedback(mStringFeedback);

                    } else {
                        errorDialog();
                    }


                } else {
                    Toast.makeText(MoreActivity.this, "You cannot submit an empty feedback.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void submitFeedback(String feedback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        SubmitFeedbackForm submitFeedbackForm = retrofit.create(SubmitFeedbackForm.class);

        Call<Void> call = submitFeedbackForm.sendFeedback(feedback);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: Checking status..." + response);
                    successDialog();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "onFailure: Checking status..." + t.getMessage());
                errorDialog();
            }
        });
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return mConMgr.getActiveNetworkInfo() != null
                && mConMgr.getActiveNetworkInfo().isAvailable()
                && mConMgr.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;

//        Add this line to Manifest for the activity
//        <meta-data
//        android:name="android.support.PARENT_ACTIVITY"
//        android:value="MainActivity" />
    }

    private void successDialog() {
        SuccessfulDialog dialog = new SuccessfulDialog();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, dialog, "dialog");
        transaction.commit();

        mAboutAppTitle.setVisibility(View.INVISIBLE);
        mAboutAppText.setVisibility(View.INVISIBLE);
        mFeedBackTitle.setVisibility(View.INVISIBLE);
        mFeedback.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);

    }

    private void errorDialog() {
        ErrorDialog dialog = new ErrorDialog();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, dialog, "dialog");
        transaction.commit();

        mAboutAppTitle.setVisibility(View.INVISIBLE);
        mAboutAppText.setVisibility(View.INVISIBLE);
        mFeedBackTitle.setVisibility(View.INVISIBLE);
        mFeedback.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);
    }


}