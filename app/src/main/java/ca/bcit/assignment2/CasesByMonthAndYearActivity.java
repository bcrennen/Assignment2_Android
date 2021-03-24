package ca.bcit.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CasesByMonthAndYearActivity extends AppCompatActivity {

    EditText year;
    EditText month;
    Button find;
    ListView results;
    ArrayList<String> casesFound;
    ArrayAdapter<String> casesFoundListAdapter;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_by_month_and_year);

        year = findViewById(R.id.year);
        month = findViewById(R.id.month);
        find = findViewById(R.id.findBtn);
        results = findViewById(R.id.results);
    }

    public void onFind(View view) {

        String inputYear = year.getText().toString();
        String inputMonth = month.getText().toString();

        Date startDate = new GregorianCalendar(Integer.parseInt(inputYear),
                Integer.parseInt(inputMonth) - 1, 1).getTime();
        Date endDate = new GregorianCalendar(Integer.parseInt(inputYear),
                Integer.parseInt(inputMonth), 1).getTime();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                casesFound = new ArrayList<>();
                int casesCounter = 1;

                for (DataSnapshot caseSnapShot : dataSnapshot.getChildren()) {
                    String ageGroup = caseSnapShot.child("Age_Group").getValue().toString();
                    String healthAuthority = caseSnapShot.child("Health_Authority").getValue().toString();
                    String date = caseSnapShot.child("Reported_Date").getValue().toString();
                    String sex = caseSnapShot.child("Sex").getValue().toString();
                    Date caseDate = new GregorianCalendar(1900, 0, 0).getTime();

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    try {
                        caseDate = formatter.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (caseDate.after(startDate) && caseDate.before(endDate)) {
                        casesFound.add("Case: " + casesCounter + "\n" +
                                "Reported Date: " + date + "\n" +
                                "Health Authority: " + healthAuthority + "\n" +
                                "Sex: " + sex + "\n" +
                                "Age Group: " + ageGroup);
                        casesCounter++;
                    }
                }

                casesFoundListAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, casesFound);
                results.setAdapter(casesFoundListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}