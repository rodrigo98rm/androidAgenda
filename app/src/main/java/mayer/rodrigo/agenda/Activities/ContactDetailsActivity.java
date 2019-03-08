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
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "contact_id";

    //Views
    private TextView txtName, txtEmail, txtAddress, txtHomePhone, txtWorkPhone;

    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //Views
        txtName = findViewById(R.id.textView_name_Details);
        txtEmail = findViewById(R.id.textView_email_Details);
        txtAddress = findViewById(R.id.textView_address_Details);
        txtHomePhone = findViewById(R.id.textView_homePhone_Details);
        txtWorkPhone = findViewById(R.id.textView_workPhone_Details);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_editar_details:
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                intent.putExtra(AddContactActivity.EDIT_MODE, true);
                intent.putExtra(AddContactActivity.CONTACT_ID, contactId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void fillData(){
        Intent intent = getIntent();
        contactId = intent.getIntExtra(CONTACT_ID, -1);

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
}
