package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "contactId";

    //Views
    private TextView txtName, txtEmail, txtAddress, txtHomePhone, txtWorkPhone;

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

        fillData();
    }

    private void fillData(){
        Intent intent = getIntent();
        int contactId = intent.getIntExtra(CONTACT_ID, -1);

        //TODO: Obter o contato do banco de dados no futuro

        if(contactId != -1){
            Contact contact = new Contact(1,"Rodrigo Mayer", "mayerrodrigo98@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", "(11) 9 5346-6857");
            txtName.setText(contact.getName());
            txtEmail.setText(contact.getEmail());
            txtAddress.setText(contact.getAddress());
            txtHomePhone.setText(contact.getHomePhone());
            txtWorkPhone.setText(contact.getWorkPhone());
        }

    }
}
