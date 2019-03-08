package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Adapters.ContactsListAdapter;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
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
                intent.putExtra(ContactDetailsActivity.CONTACT_ID, ContatoDAO.getInstance().getContacts().get(position).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillContactsList();
    }

    private void fillContactsList(){
        ContactsListAdapter adapter = new ContactsListAdapter(getApplicationContext());
        listViewContacts.setAdapter(adapter);
    }
}
