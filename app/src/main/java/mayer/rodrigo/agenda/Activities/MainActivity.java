package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Adapters.ContactsListAdapter;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
import mayer.rodrigo.agenda.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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

        setupList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillContactsList();
    }

    private void setupList(){

        listViewContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
                intent.putExtra(ContactDetailsActivity.CONTACT_ID, ContatoDAO.getInstance().getContacts().get(position).getId());
                startActivity(intent);
            }
        });

        listViewContacts.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                mode.setTitle(listViewContacts.getCheckedItemCount() + " selecionados");

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //Inflate the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.main_cab_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                SparseBooleanArray selectedContacts = listViewContacts.getCheckedItemPositions();
                int id = item.getItemId();

                //Delete contacts
                if(id == R.id.main_cab_delete){
                    ArrayList<Contact> contacts = ContatoDAO.getInstance().getContacts();
                    ArrayList<Integer> idsToRemove = new ArrayList<>();

                    for(int i = 0; i < contacts.size(); i++){
                        if(selectedContacts.get(i)){
                            idsToRemove.add(contacts.get(i).getId());
                        }
                    }

                    for(int i = 0; i < idsToRemove.size(); i++){
                        ContatoDAO.getInstance().removeContactWith(idsToRemove.get(i));
                    }
                }

                mode.finish();

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                fillContactsList();
            }
        });
    }

    private void fillContactsList(){
        ContactsListAdapter adapter = new ContactsListAdapter(getApplicationContext());
        listViewContacts.setAdapter(adapter);
    }
}
