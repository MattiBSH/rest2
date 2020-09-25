
package dtos;

import entities.Person;
import java.util.Objects;


public class PersonDTO {
    private int id;
    private String fName;
    private String lName;
    private String phone;
    private String city;
    private String zip;
    private String street;

    public PersonDTO(Person p) {
        this.fName = p.getfName();
        this.lName = p.getlName();
        this.phone = p.getPhone();
        this.id=p.getId();
        this.city=p.getAddress().getCity();
        this.zip=p.getAddress().getZip();
        this.street=p.getAddress().getStreet(); 
    }
    
    public PersonDTO(String fn,String ln, String phone) {
        this.fName = fn;
        this.lName = ln;
        this.phone = phone;
        
    }

    public PersonDTO(String fName, String lName, String phone, String city, String zip, String street) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.city = city;
        this.zip = zip;
        this.street = street;
    }

    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    public PersonDTO() {}  

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

 

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonDTO other = (PersonDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        return true;
    }
    
}

