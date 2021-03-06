package mayer.rodrigo.agenda.Models;

import java.util.UUID;

public class Contact {

    private int id;
    private String name, email, address, homePhone, workPhone;

    public Contact(String name, String email, String address, String homePhone, String workPhone) {
        this.id = UUID.randomUUID().hashCode();
        this.name = name;
        this.email = email;
        this.address = address;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }
}
