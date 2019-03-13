package mayer.rodrigo.agenda.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Adapters.ContactsListAdapter;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
import mayer.rodrigo.agenda.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Views
    private ListView listViewContacts;
    private FloatingActionButton fabAddContact;

    private ContatoDAO contatoDAO;

    private final int SEND_EMAIL_REQUEST_CODE = 1;

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

        contatoDAO = ContatoDAO.getInstance();

        setupList();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillContactsList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEND_EMAIL_REQUEST_CODE){
            //Result eh sempre 0, seja o email enviado ou nao
            //nao eh possivel verficar se o envio do email foi bem sucedido ou nao, como pede o exercicio
            Log.i("HERE RESULT", String.valueOf(resultCode)); // Sempre 0
            if(data != null){
                Log.i("HERE DATA", data.toString()); // nunca eh impresso pois data eh sempre null
            }
        }

    }

    private void setupList(){

        listViewContacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
                intent.putExtra(ContactDetailsActivity.CONTACT_ID, contatoDAO.getContacts().get(position).getId());
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

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.main_cab_menu, menu);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mode.setTitleOptionalHint(false);
                }

                Intent incoming = getIntent();

                if(incoming.getAction().equals(Intent.ACTION_PICK)){
                    menu.findItem(R.id.main_cab_delete).setVisible(false);
                    menu.findItem(R.id.main_cab_email).setVisible(false);
                    menu.findItem(R.id.main_cab_share).setVisible(false);
                }else{
                    menu.findItem(R.id.main_cab_done).setVisible(false);
                }

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

                //Delete multiple contacts
                if(id == R.id.main_cab_delete){
                    ArrayList<Contact> contacts = contatoDAO.getContacts();
                    ArrayList<Integer> idsToRemove = new ArrayList<>();

                    for(int i = 0; i < contacts.size(); i++){
                        if(selectedContacts.get(i)){
                            idsToRemove.add(contacts.get(i).getId());
                        }
                    }

                    for(int i = 0; i < idsToRemove.size(); i++){
                        contatoDAO.removeContactWith(idsToRemove.get(i));
                    }
                }
                //Send Email to multiple contacts
                else if(id == R.id.main_cab_email){

                    ArrayList<Contact> contacts = contatoDAO.getContacts();
                    ArrayList<String> emails = new ArrayList<>();

                    for(int i = 0; i < contacts.size(); i++){
                        if(selectedContacts.get(i)){
                            emails.add(contacts.get(i).getEmail());
                        }
                    }

                    sendEmail(emails);
                }
                //Share contacts
                else if(id == R.id.main_cab_share){
                    ArrayList<Contact> contacts = contatoDAO.getContacts();
                    String shareString = "";

                    for(int i = 0; i < contacts.size(); i++){
                        if(selectedContacts.get(i)){
                            Contact contact = contacts.get(i);
                            shareString += contact.getName() + "\n";
                            shareString += contact.getEmail() + "\n";
                            shareString += contact.getAddress() + "\n";
                            shareString += contact.getHomePhone() + "\n";
                            shareString += contact.getWorkPhone() + "\n";
                            shareString += "\n";
                        }
                    }
                    shareContacts(shareString);
                }
                // Send result
                else if(id == R.id.main_cab_done){
                    ArrayList<Contact> contactsList = contatoDAO.getContacts();

                    HashMap<Integer, HashMap<String, String>> contacts = new HashMap<>();

                    int count = 0;
                    for(int i = 0; i < contactsList.size(); i++){
                        if(selectedContacts.get(i)){
                            Contact contact = contactsList.get(i);

                            HashMap<String, String> contactHash = new HashMap<>();

                            contactHash.put("name", contact.getName());
                            contactHash.put("email", contact.getEmail());
                            contactHash.put("address", contact.getAddress());
                            contactHash.put("homePhone", contact.getHomePhone());
                            contactHash.put("workPhone", contact.getWorkPhone());

                            contacts.put(count , contactHash);
                            count++;
                        }
                    }

                    Intent result = new Intent();
                    result.putExtra("contacts", contacts);
                    setResult(Activity.RESULT_OK, result);
                    finish();
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

    private void sendEmail(ArrayList<String> emails){
        String uriEmails = "";

        for(int i = 0; i < emails.size(); i++){
            uriEmails += emails.get(i) + ",";
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", uriEmails, null));
        startActivityForResult(intent, SEND_EMAIL_REQUEST_CODE);
    }

    private void shareContacts(String contacts){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, contacts);
        intent.setType("text/plain");

        Intent chooser = Intent.createChooser(intent, getString(R.string.share_message));
        startActivity(chooser);
    }

    private void fillContactsList(){
        ContactsListAdapter adapter = new ContactsListAdapter(getApplicationContext());
        listViewContacts.setAdapter(adapter);
    }
}
