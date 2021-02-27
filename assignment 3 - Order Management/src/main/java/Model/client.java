package Model;

/**
 * The class defines the object of type client, having an ID,name and address
 */
public class client {
    private Integer idclient;
    private String name;
    private String address;

    public client() {
        this(null,null,null);
    }

    public client(Integer id, String name, String address) {
        this.idclient = id;
        this.name = name;
        this.address = address;
    }

    public client(String name, String address) {
        this(null,name,address);
    }

    public Integer getIdclient() {
        return idclient;
    }

    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        return this.getIdclient() + ", " + this.getName() + ", " + this.getAddress();
    }
}
