package ir.fearefull.wheretoapp.ui;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.local.AppDatabase;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;

import static ir.fearefull.wheretoapp.utils.Constants.splashScreenDuration;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        scheduleSplashScreen();
    }

    private void scheduleSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseActivity();
            }
        }, splashScreenDuration);
    }

    private void chooseActivity() {
        if(DatabaseInitializer.countUsers(AppDatabase.getAppDatabase(getApplicationContext())) >= 1) {
            Intent dashboardIntent = new Intent(this, MainActivity.class);
            startActivity(dashboardIntent);
            finish();
        }
        else {
            Intent dashboardIntent = new Intent(this, VerifyActivity.class);
            startActivity(dashboardIntent);
            finish();
        }
    }
}
