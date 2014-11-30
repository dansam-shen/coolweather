package net.itaem.db;

public class County {
	private int id;
	private String countyName;
	private String countyCode;
	private int cityId;
	
	public County(String countyName, String countyCode) {
		super();
		this.countyName = countyName;
		this.countyCode = countyCode;
	}

	public County(int id, String countyName, String countyCode, int cityId) {
		super();
		this.id = id;
		this.countyName = countyName;
		this.countyCode = countyCode;
		this.cityId = cityId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * @param countyName the countyName to set
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the countyCode
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * @param countyCode the countyCode to set
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public County() {
	}

}
