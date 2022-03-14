package rs.ac.ni.pmf.greeting;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnGreet;
    private TextView label;
    private Person person;
    private ActivityResultLauncher<Person> detailsActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGreet = findViewById(R.id.btnGreet);
        btnGreet.setOnClickListener(this::greet);
        btnGreet.setEnabled(false);
        findViewById(R.id.btnDetails).setOnClickListener(this::showDetails);
        label = findViewById(R.id.labelGreet);
        detailsActivityLauncher = registerForActivityResult(DetailsActivity.DETAILS_ACTIVITY_CONTRACT, this::onDetailsActivityResult);
    }

    public void greet(View view) {
        final String greeting = getResources().getString(R.string.greeting, person.getFirstName(), person.getLastName());
        label.setText(greeting);
    }

    public void showDetails(View view) {
        detailsActivityLauncher.launch(person);
    }

    private void onDetailsActivityResult(Person person){
        if(person == null){
            return;
        }
        this.person = person;
        btnGreet.setEnabled(true);
    }


}