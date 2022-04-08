package lab04.eim.systems.cs.pub.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText numberEditText;
    EditText emailEditText;
    EditText addressEditText;
    EditText companyEditText;
    EditText jobEditText;
    EditText imEditText;
    EditText websiteEditText;
    LinearLayout additional_fields_container;

    Button save;
    Button cancel;
    Button show_hide_additional;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.show_hide_button:
                    if (additional_fields_container.getVisibility() == View.VISIBLE) {
                        show_hide_additional.setText(getResources().getString(R.string.show_additional_fields));
                        additional_fields_container.setVisibility(View.INVISIBLE);
                    } else {
                        show_hide_additional.setText(getResources().getString(R.string.hide_additional_fields));
                        additional_fields_container.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.save:
                    String name = nameEditText.getText().toString();
                    String number = numberEditText.getText().toString();
                    String address =  addressEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String job = jobEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String im =  imEditText.getText().toString();
                    String website = websiteEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (number != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (job != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivity(intent);
                    break;
                case R.id.cancel:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;

            }
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        nameEditText = findViewById(R.id.name);
        numberEditText = findViewById(R.id.number);
        emailEditText = findViewById(R.id.email);
        addressEditText = findViewById(R.id.address);
        companyEditText = findViewById(R.id.company);
        jobEditText = findViewById(R.id.job);
        imEditText =  findViewById(R.id.IM);
        websiteEditText = findViewById(R.id.website);
        additional_fields_container = findViewById(R.id.additional_fields_container);

        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        show_hide_additional = findViewById(R.id.show_hide_button);

        save.setOnClickListener(buttonClickListener);
        cancel.setOnClickListener(buttonClickListener);
        show_hide_additional.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                numberEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}