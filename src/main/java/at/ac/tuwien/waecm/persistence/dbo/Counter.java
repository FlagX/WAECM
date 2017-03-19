package at.ac.tuwien.waecm.persistence.dbo;

import org.springframework.data.annotation.Id;

/**
 * @author Martin Griesler
 */
public class Counter {

	@Id
	private String id;

	private Long counter;

	protected Counter() {}

	public Counter(Long counter) {
		this.counter = counter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

}
