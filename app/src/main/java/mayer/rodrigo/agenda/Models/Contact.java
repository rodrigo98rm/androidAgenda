package mayer.rodrigo.agenda.Models;

public class Contact {

    private int id;
    private String name, email, address, homePhone, workPhone;

    public Contact(int id, String name, String email, String address, String homePhone, String workPhone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
    }

    public int getId() {
        return id;
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
