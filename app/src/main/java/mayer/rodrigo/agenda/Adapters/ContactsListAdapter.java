package mayer.rodrigo.agenda.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import mayer.rodrigo.agenda.Models.Contact;
import mayer.rodrigo.agenda.Models.ContatoDAO;
import mayer.rodrigo.agenda.R;

public class ContactsListAdapter extends BaseAdapter {

    private Context context;
    private ContatoDAO contatoDAO;

    public ContactsListAdapter(Context context){
        super();
        this.context = context;
        contatoDAO = ContatoDAO.getInstance();
    }

    @Override
    public int getCount() {
        return contatoDAO.getContacts().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
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

        Contact contact = contatoDAO.getContactAt(position);
        name.setText(contact.getName());
        email.setText(contact.getEmail());

        return convertView;
    }
}
