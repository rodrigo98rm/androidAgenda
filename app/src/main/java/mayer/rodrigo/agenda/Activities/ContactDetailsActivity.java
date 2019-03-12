package mayer.rodrigo.agenda.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
import mayer.rodrigo.agenda.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailsActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "contact_id";
    public static final int PERMISSION_REQUEST_CALL_PHONE = 0;

    //Views
    private TextView txtName, txtEmail, txtAddress, txtHomePhone, txtWorkPhone;
    private ImageButton buttonSendEmail, buttonCallHome, buttonCallWork, buttonMsgHome, buttonMsgWork;

    private int contactId;
    private Contact contact;

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
        buttonSendEmail = findViewById(R.id.imageButton_sendEmail_Details);
        buttonCallHome = findViewById(R.id.imageButton_callHome_Details);
        buttonCallWork = findViewById(R.id.imageButton_callWork_Details);
        buttonMsgHome = findViewById(R.id.imageButton_messageHome_Details);
        buttonMsgWork = findViewById(R.id.imageButton_messageWork_Details);

        //Listeners
        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(contact.getEmail());
            }
        });

        buttonCallHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(contact.getHomePhone());
            }
        });

        buttonCallWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(contact.getWorkPhone());
            }
        });

        buttonMsgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms(contact.getHomePhone());
            }
        });

        buttonMsgWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms(contact.getWorkPhone());
            }
        });

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

    private void callNumber(String number){

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "Permissão para realizar chamadas negada", Toast.LENGTH_LONG).show();
        }

        try{
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            startActivity(intent);
        }catch (SecurityException e){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_CALL_PHONE);
        }

    }

    private void sendSms(String number){

        Uri uri = Uri.fromParts("sms", number, null);
        Log.i("HERE URI", uri.toString());

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void sendEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        startActivity(intent);
    }

    private void fillData(){
        Intent intent = getIntent();
        contactId = intent.getIntExtra(CONTACT_ID, -1);

        //TODO: Obter o contato do banco de dados no futuro

        if(contactId != -1){
            contact = ContatoDAO.getInstance().getContactWith(contactId);
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
