package trainning.osms.business;

public class CategorySearchOptions {
	private String name;
	private String description;
	private Integer firstResult;
	private Integer MaxResults;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	public Integer getMaxResults() {
		return MaxResults;
	}
	public void setMaxResults(Integer maxResults) {
		MaxResults = maxResults;
	}

}
