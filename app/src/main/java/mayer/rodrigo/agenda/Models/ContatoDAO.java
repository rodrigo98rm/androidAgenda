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
        addContact(new Contact("Rodrigo Mayer", "rodrigo@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Paulo Mayer", "paulo@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
        addContact(new Contact("Elver Mayer", "elver@gmail.com", "Rua Abolição, 34", "(11) 9 5346-6857", ""));
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

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(int index){
        contacts.remove(index);
    }

}