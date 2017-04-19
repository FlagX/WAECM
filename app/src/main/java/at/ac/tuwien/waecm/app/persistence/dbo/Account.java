package at.ac.tuwien.waecm.app.persistence.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Created by Stefan on 15.03.2017.
 */
@Entity
@RepositoryRestResource(exported = false)
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private @JsonIgnore String password;

    protected Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
