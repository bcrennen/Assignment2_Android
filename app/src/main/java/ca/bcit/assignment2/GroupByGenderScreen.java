package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupByGenderScreen extends AppCompatActivity {
    TextView male;
    TextView female;
    TextView unknown;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_by_gender_screen);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        unknown = findViewById(R.id.unknown);

        databaseReference.addValueEventListener(new ValueEventListener() {
            int maleCasesCount = 0;
            int femaleCasesCount = 0;
            int unknownCasesCount = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot casesSnapShot : snapshot.getChildren()) {
                    String gender = casesSnapShot.child("Sex").getValue().toString();
                    switch (gender) {
                        case "M":
                            maleCasesCount++;
                            break;
                        case "F":
                            femaleCasesCount++;
                            break;
                        default:
                            unknownCasesCount++;
                    }
                }

                String maleCases = male.getText().toString() + maleCasesCount;
                String femaleCases = female.getText().toString() + femaleCasesCount;
                String unknownCases = unknown.getText().toString() + unknownCasesCount;

                male.setText(maleCases);
                female.setText(femaleCases);
                unknown.setText(unknownCases);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}