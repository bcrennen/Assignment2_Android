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

public class CasesByHealthAuthorityActivity extends AppCompatActivity {
    TextView fraser;
    TextView interior;
    TextView northern;
    TextView outOfCanada;
    TextView coastal;
    TextView island;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_by_health_authority);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        fraser = findViewById(R.id.fraser);
        interior = findViewById(R.id.interior);
        northern = findViewById(R.id.northern);
        outOfCanada = findViewById(R.id.outOfCanada);
        coastal = findViewById(R.id.coastal);
        island = findViewById(R.id.island);

        databaseReference.addValueEventListener(new ValueEventListener() {
            int fraserCasesCounter = 0;
            int interiorCasesCounter = 0;
            int northernCasesCounter = 0;
            int outOfCanadaCasesCounter = 0;
            int coastalCasesCounter = 0;
            int islandCasesCounter = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot caseSnapShot : dataSnapshot.getChildren()) {
                    String healthAuthority = caseSnapShot.child("Health_Authority").getValue().toString();
                    switch (healthAuthority) {
                        case "Fraser":
                            fraserCasesCounter++;
                            break;
                        case "Interior":
                            interiorCasesCounter++;
                            break;
                        case "Northern":
                            northernCasesCounter++;
                            break;
                        case "Out of Canada":
                            outOfCanadaCasesCounter++;
                            break;
                        case "Vancouver Coastal":
                            coastalCasesCounter++;
                            break;
                        case "Vancouver Island":
                            islandCasesCounter++;
                            break;
                    }
                }

                String fraserCases = fraser.getText().toString() + fraserCasesCounter;
                String interiorCases = interior.getText().toString() + interiorCasesCounter;
                String northernCases = northern.getText().toString() + northernCasesCounter;
                String outOfCanadaCases = outOfCanada.getText().toString() + outOfCanadaCasesCounter;
                String coastalCases = coastal.getText().toString() + coastalCasesCounter;
                String islandCases = island.getText().toString() + islandCasesCounter;

                fraser.setText(fraserCases);
                interior.setText(interiorCases);
                northern.setText(northernCases);
                outOfCanada.setText(outOfCanadaCases);
                coastal.setText(coastalCases);
                island.setText(islandCases);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}