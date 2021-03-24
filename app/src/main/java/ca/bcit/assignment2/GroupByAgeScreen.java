package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupByAgeScreen extends AppCompatActivity {
    TextView lessThanTen;
    TextView tenGroup;
    TextView twentyGroup;
    TextView thirtyGroup;
    TextView fortyGroup;
    TextView fiftyGroup;
    TextView sixtyGroup;
    TextView seventyGroup;
    TextView eightyGroup;
    TextView ninetyPlusGroup;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_by_age_screen);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        lessThanTen = findViewById(R.id.lessThan10);
        tenGroup = findViewById(R.id.TenTo19);
        twentyGroup = findViewById(R.id.TwentyTo29);
        thirtyGroup = findViewById(R.id.ThirtyTo39);
        fortyGroup = findViewById(R.id.FortyTo49);
        fiftyGroup = findViewById(R.id.FiftyTo59);
        sixtyGroup = findViewById(R.id.SixtyTo69);
        seventyGroup = findViewById(R.id.SeventyTo79);
        eightyGroup = findViewById(R.id.EightyTo89);
        ninetyPlusGroup = findViewById(R.id.NinetyPlus);

        databaseReference.addValueEventListener(new ValueEventListener() {
            int lessThanTenCount = 0;
            int tenGroupCount = 0;
            int twentyGroupCount = 0;
            int thirtyGroupCount = 0;
            int fortyGroupCount = 0;
            int fiftyGroupCount = 0;
            int sixtyGroupCount = 0;
            int seventyGroupCount = 0;
            int eightyGroupCount = 0;
            int ninetyPlusGroupCount = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot casesSnapShot : snapshot.getChildren()) {
                    String age = casesSnapShot.child("Age_Group").getValue().toString();
                    switch (age) {
                        case "<10":
                            lessThanTenCount++;
                            break;
                        case "10-19":
                            tenGroupCount++;
                            break;
                        case "20-29":
                            twentyGroupCount++;
                            break;
                        case "30-39":
                            thirtyGroupCount++;
                            break;
                        case "40-49":
                            fortyGroupCount++;
                            break;
                        case "50-59":
                            fiftyGroupCount++;
                            break;
                        case "60-69":
                            sixtyGroupCount++;
                            break;
                        case "70-79":
                            seventyGroupCount++;
                            break;
                        case "80-89":
                            eightyGroupCount++;
                            break;
                        case "90+":
                            ninetyPlusGroupCount++;
                            break;
                    }
                }

                String lessThanTenCases = lessThanTen.getText().toString() + lessThanTenCount;
                String tenGroupCases = tenGroup.getText().toString() + tenGroupCount;
                String twentyGroupCases = twentyGroup.getText().toString() + twentyGroupCount;
                String thirtyGroupCases = thirtyGroup.getText().toString() + thirtyGroupCount;
                String fortyGroupCases = fortyGroup.getText().toString() + fortyGroupCount;
                String fiftyGroupCases = fiftyGroup.getText().toString() + fiftyGroupCount;
                String sixtyGroupCases = sixtyGroup.getText().toString() + sixtyGroupCount;
                String seventyGroupCases = seventyGroup.getText().toString() + seventyGroupCount;
                String eightyGroupCases = eightyGroup.getText().toString() + eightyGroupCount;
                String ninetyPlusGroupCases = ninetyPlusGroup.getText().toString() + ninetyPlusGroupCount;

                lessThanTen.setText(lessThanTenCases);
                tenGroup.setText(tenGroupCases);
                twentyGroup.setText(twentyGroupCases);
                thirtyGroup.setText(thirtyGroupCases);
                fortyGroup.setText(fortyGroupCases);
                fiftyGroup.setText(fiftyGroupCases);
                sixtyGroup.setText(sixtyGroupCases);
                seventyGroup.setText(seventyGroupCases);
                eightyGroup.setText(eightyGroupCases);
                ninetyPlusGroup.setText(ninetyPlusGroupCases);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


    }
}