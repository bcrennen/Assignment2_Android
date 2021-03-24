package ca.bcit.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // logout instance variable
    Button logoutButton;

    // Group by instance variable
    Button genderGroup;
    Button ageGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to logout
        logoutButton = findViewById(R.id.ButtonLogout);

        // get reference to group by button
        genderGroup = findViewById(R.id.groupGenderButton);
        ageGroup = findViewById(R.id.groupAgeButton);

        // logout method
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), AccountScreen.class));
                finish();
            }
        });
    }

    // onclick method to go to GroupByGenderScreen
    public void onclickGenderScreen(View view) {
        Intent intent = new Intent(this, GroupByGenderScreen.class);
        startActivity(intent);
    }

    // onclick method to go to GroupByAgeScreen
    public void onclickAgeScreen(View view) {
        Intent intent = new Intent(this, GroupByAgeScreen.class);
        startActivity(intent);
    }

    // onclick method to go to CasesByHealthAuthority
    public void onclickHealthAuthority(View view) {
        Intent intent = new Intent(this, CasesByHealthAuthorityActivity.class);
        startActivity(intent);
    }

    // onclick method to go to CasesByMonthAndYear
    public void onclickMonthAndYear(View view) {
        Intent intent = new Intent(this, CasesByMonthAndYearActivity.class);
        startActivity(intent);
    }
}