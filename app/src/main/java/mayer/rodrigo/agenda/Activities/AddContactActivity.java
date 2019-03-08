package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
import mayer.rodrigo.agenda.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    //Views
    private EditText txtName, txtEmail, txtAddress, txtHomePhone, txtWorkPhone;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

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

    }

    private void saveContact(){
        String name = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String homePhone = txtHomePhone.getText().toString().trim();
        String workPhone = txtWorkPhone.getText().toString().trim();

        Contact contact = new Contact(name, email, address, homePhone, workPhone);
        ContatoDAO.getInstance().addContact(contact);
        finish();
    }

}
