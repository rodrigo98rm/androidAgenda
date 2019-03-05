package mayer.rodrigo.agenda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.R;

public class ContactsListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private ArrayList<Contact> contacts;

    public ContactsListAdapter(Context context, ArrayList<Contact> contacts){
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.contacts_list_item, null, false);
        }

        TextView name, email;
        name = convertView.findViewById(R.id.textView_name_ContactsAdapter);
        email = convertView.findViewById(R.id.textView_email_ContactsAdapter);

        Contact contact = contacts.get(position);
        name.setText(contact.getName());
        email.setText(contact.getEmail());

        return convertView;
    }
}
