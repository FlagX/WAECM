package at.ac.tuwien.waecm.app.persistence.dbo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Martin Griesler
 */
@Entity
public class Counter {

	@Id
	@GeneratedValue
	private Long id;

	private Long counter;

	protected Counter() {}

	public Counter(Long counter) {
		this.counter = counter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

}
