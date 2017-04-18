package at.ac.tuwien.waecm.dto;

/**
 * @author Martin Griesler
 */
public class CounterDto {
	private Long count;


	public CounterDto(Long count) {
		super();
		this.count = count;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Counter [count=" + count + "]";
	}


}
