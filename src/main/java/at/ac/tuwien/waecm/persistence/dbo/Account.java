package at.ac.tuwien.waecm.persistence.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



/**
 * Created by Stefan on 15.03.2017.
 */
@Document
@RepositoryRestResource(exported = false)
public class Account {

    @Id
    private String id;
    private String username;
    private @JsonIgnore String password;

    protected Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
