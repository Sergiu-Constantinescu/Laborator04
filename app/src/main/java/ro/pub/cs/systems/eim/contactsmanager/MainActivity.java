package ro.pub.cs.systems.eim.contactsmanager;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private boolean showing_extra_fields = false;

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            Integer viewId = view.getId();

            switch (viewId) {
                case R.id.show_hide_button:
                    if(showing_extra_fields) {
                        showing_extra_fields = false;
                        button.setText(R.string.but_show_det);
                        findViewById(R.id.layout_additional_fields).setVisibility(View.GONE);
                    } else {
                        showing_extra_fields = true;
                        button.setText(R.string.but_hide_det);
                        findViewById(R.id.layout_additional_fields).setVisibility((View.VISIBLE));
                    }
                    break;

                case R.id.save_button:
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    String name = ((EditText) findViewById(R.id.edit_text_name)).getText().toString();
                    if (name.length() > 0) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }

                    String number = ((EditText) findViewById(R.id.edit_text_number)).getText().toString();
                    if (number.length() > 0) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
                    }

                    String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
                    if (email.length() > 0) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }

                    String address = ((EditText) findViewById(R.id.edit_text_address)).getText().toString();
                    if (address.length() > 0) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }

                    String job = ((EditText) findViewById(R.id.edit_text_job)).getText().toString();
                    if (job.length() > 0) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                    }

                    String company = ((EditText) findViewById(R.id.edit_text_company)).getText().toString();
                    if (company.length() > 0) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }

                    ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                    String website = ((EditText) findViewById(R.id.edit_text_website)).getText().toString();
                    String im = ((EditText) findViewById(R.id.edit_text_im)).getText().toString();

                    if(website.length() > 0) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }

                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivity(intent);
                    break;

                case R.id.cancel_button:
                    finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button show_hide_button = (Button) findViewById(R.id.show_hide_button);
        Button save_button = (Button) findViewById(R.id.save_button);
        Button cancel_button = (Button) findViewById(R.id.cancel_button);

        show_hide_button.setOnClickListener(buttonClickListener);
        save_button.setOnClickListener(buttonClickListener);
        cancel_button.setOnClickListener(buttonClickListener);

        ((LinearLayout) findViewById(R.id.layout_additional_fields)).setVisibility(View.GONE);

    }
}