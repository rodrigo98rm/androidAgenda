package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
import mayer.rodrigo.agenda.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    //Views
    private EditText txtName, txtEmail, txtAddress, txtHomePhone, txtWorkPhone;
    private Button buttonSave;

    //Accepted Intent EXTRAS
    public static final String EDIT_MODE = "edit", CONTACT_ID = "contact_id";

    private boolean editMode = false;
    private Intent incomingIntent;
    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //Check if activity should be on EDIT mode
        incomingIntent = getIntent();
        editMode = incomingIntent.getBooleanExtra(EDIT_MODE, false);

        //Views
        txtName = findViewById(R.id.editText_name_Add);
        txtEmail = findViewById(R.id.editText_email_Add);
        txtAddress = findViewById(R.id.editText_address_Add);
        txtHomePhone = findViewById(R.id.editText_homePhone_Add);
        txtWorkPhone = findViewById(R.id.editText_workPhone_Add);
        buttonSave = findViewById(R.id.button_save_Add);

        //Listeners
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContact();
            }
        });

        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);

        if(!editMode){
            MenuItem delete = menu.findItem(R.id.menu_delete_add);
            delete.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_delete_add:
                ContatoDAO.getInstance().removeContactWith(contactId);

                //Fecha as activities anteriores e volta para a Main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillData(){
        if(!editMode){
            return;
        }

        //Change Action Bar title
        getSupportActionBar().setTitle("Editar Contato");

        contactId = incomingIntent.getIntExtra(CONTACT_ID, -1);

        //TODO: Obter o contato do banco de dados no futuro

        if(contactId != -1){
            Contact contact = ContatoDAO.getInstance().getContactWith(contactId);
            if(contact == null){
                //Error, return to main screen
                finish();
                return;
            }
            txtName.setText(contact.getName());
            txtEmail.setText(contact.getEmail());
            txtAddress.setText(contact.getAddress());
            txtHomePhone.setText(contact.getHomePhone());
            txtWorkPhone.setText(contact.getWorkPhone());
        }

    }

    private void saveContact(){

        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String homePhone = txtHomePhone.getText().toString().trim();
        String workPhone = txtWorkPhone.getText().toString().trim();

        //Validate fields
        if(!isNameValid(name)){
            txtName.setError("Nome muito curto");
            return;
        }

        if(!isEmailValid(email)){
            txtEmail.setError("Email inválido");
            return;
        }

        if(!isAddressValid(address)){
            txtAddress.setError("O endereço não pode ser vazio");
            return;
        }

        //Update or save contact
        if(editMode){
            ContatoDAO.getInstance().updateContactWith(contactId, name, email, address, homePhone, workPhone);
            finish();
        }else{
            //Saves a new contact
            Contact contact = new Contact(name, email, address, homePhone, workPhone);
            ContatoDAO.getInstance().addContact(contact);
        }

        finish();
    }

    private boolean isNameValid(String name){
        return name.length() >= 3;
    }

    private boolean isEmailValid(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isAddressValid(String address){
        return !address.isEmpty();
    }

}
