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
        addContact(new Contact("Rodrigo M", "rodrigo@teste.com", "Rua Abolição, 34", "(11) 9 5346-6857", "(11) 9 2564-3344"));
        addContact(new Contact("Paulo M", "paulo@teste.com", "Rua Inglaterra, 58", "(11) 9 5346-4685", "(11) 9 2564-3345"));
        addContact(new Contact("Elver M", "elver@teste.com", "Rua Japão, 265", "(11) 9 5346-3258", "(11) 9 2564-3368"));
        addContact(new Contact("Gabriel F", "gabriel@teste.com", "Rua França, 154", "(11) 9 5346-1259", "(11) 9 2564-3494"));
        addContact(new Contact("Lucas G", "lucas@teste.com", "Rua Speers, 98", "(11) 9 5346-4587", "(11) 9 2564-3369"));
        addContact(new Contact("Andre S", "andre@teste.com", "Rua Oratório, 345", "(11) 9 5346-5698", "(11) 9 2564-3148"));
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
