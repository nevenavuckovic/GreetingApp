package rs.ac.ni.pmf.greeting;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailsActivity extends AppCompatActivity {

    public static final String PERSON_DATA = "PERSON_DATA";
    private Person person;
    private EditText editFirstName;
    private EditText editLastName;
    private Button btnSave;


    public static final ActivityResultContract<Person, Person> DETAILS_ACTIVITY_CONTRACT = new ActivityResultContract<Person, Person>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Person input) {
            final Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(PERSON_DATA, input);

            return intent;
        }

        @Override
        public Person parseResult(int resultCode, @Nullable Intent intent) {
            if(resultCode == RESULT_CANCELED){
                return null;
            }
            if(resultCode == RESULT_OK){
                if(intent == null){
                    return null;
                }
                return (Person) intent.getParcelableExtra(PERSON_DATA);
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);

        person = (Person) getIntent().getParcelableExtra(PERSON_DATA);

        showPersonData();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this::save);
        btnSave.setEnabled(false);
        findViewById(R.id.btnCancel).setOnClickListener(this::cancel);

        editFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSave.setEnabled(!editFirstName.getText().toString().equals("") && !editLastName.getText().toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSave.setEnabled(!editLastName.getText().toString().trim().isEmpty() && !editFirstName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void showPersonData(){
        if(person != null){
            editFirstName.setText(person.getFirstName());
            editLastName.setText(person.getLastName());
        }
    }

    public void save(View view) {
        final String firstName = editFirstName.getText().toString();
        final String lastName = editLastName.getText().toString();
        final Intent finalIntent = new Intent();
        finalIntent.putExtra(PERSON_DATA, new Person(firstName, lastName));
        setResult(RESULT_OK, finalIntent);
        finish();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}