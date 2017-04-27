package at.ac.tuwien.waecm.app.persistence.dbo;

import at.ac.tuwien.waecm.common.persistence.dbo.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.time.ZonedDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @ManyToOne
    private Account owner;
    @ManyToOne
    private Account target;

    private Double value = new Double(0);

    private ZonedDateTime created=null;
    private ZonedDateTime commited=null;

    private String tan;

    public Transaction() {

    }

    public Transaction(String description, Account owner, Account target, Double value, ZonedDateTime created) {

        this.description=description;
        this.owner = owner;
        this.target = target;
        this.value = value;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getCommited() {
        return commited;
    }

    public void setCommited(ZonedDateTime commited) {
        this.commited = commited;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    @Override
	public String toString() {
		return "Transaction [id=" + id + ", description=" + description + ", owner=" + owner + ", target=" + target
				+ ", value=" + value + ", created=" + created + ", commited=" + commited + "]";
	}

}
