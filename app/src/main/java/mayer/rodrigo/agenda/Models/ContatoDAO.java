package mayer.rodrigo.agenda.Models;

import java.util.ArrayList;
import java.util.UUID;

public class ContatoDAO {

    private static ContatoDAO instance;
    private ArrayList<Contact> contacts = new ArrayList<>();

    private ContatoDAO(){}

    public static ContatoDAO getInstance(){
        if(instance == null){
            instance = new ContatoDAO();
            instance.loadTestData();
        }

        return instance;
    }

    private void loadTestData(){
        addContact(new Contact("Rodrigo Mayer", "rodrigo@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Paulo Mayer", "paulo@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Elver Mayer", "elver@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Rodrigo Mayer", "rodrigo@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Paulo Mayer", "paulo@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Elver Mayer", "elver@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
    }

    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    public Contact getContactWith(int id){
        for(int i = 0; i < contacts.size(); i++){
            if(contacts.get(i).getId() == id){
                return contacts.get(i);
            }
        }
        return null;
    }

    public Contact getContactAt(int index){
        return contacts.get(index);
    }

    public int getContactIndexWith(int id){
        for(int i = 0; i < contacts.size(); i++){
            if(contacts.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void updateContactWith(int id, String name, String email, String adddress, String homePhone, String workPhone){
        Contact contact = getContactWith(id);
        contact.setName(name);
        contact.setEmail(email);
        contact.setAddress(adddress);
        contact.setHomePhone(homePhone);
        contact.setWorkPhone(workPhone);
    }

    public void removeContactWith(int id){
        contacts.remove(getContactWith(id));
    }

}
