package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Adapters.ContactsListAdapter;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Views
    private ListView listViewContacts;
    private FloatingActionButton fabAddContact;

    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views
        listViewContacts = findViewById(R.id.listView_contatos_Main);
        fabAddContact = findViewById(R.id.fab_addContact_Main);

        //Listeners
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
                intent.putExtra(ContactDetailsActivity.CONTACT_ID, contacts.get(position).getId());
                startActivity(intent);
            }
        });

        fillContactsList();
    }

    private void fillContactsList(){
        contacts.add(new Contact(1,"Rodrigo Mayer", "mayerrodrigo98@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        contacts.add(new Contact(2, "Rodrigo Mayer", "mayerrodrigo98@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        contacts.add(new Contact(3, "Rodrigo Mayer", "mayerrodrigo98@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));

        ContactsListAdapter adapter = new ContactsListAdapter(getApplicationContext(), contacts);
        listViewContacts.setAdapter(adapter);
    }
}
