package net.itaem.model;

public class Province {
	private int id;
	private String provinceName;
	private String privinceCode;

	public Province() {
	}

	public Province(String provinceName, String privinceCode) {
		this.provinceName = provinceName;
		this.privinceCode = privinceCode;
	}

	public Province(int id, String provinceName, String privinceCode) {
		this(provinceName, privinceCode);
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @return the privinceCode
	 */
	public String getPrivinceCode() {
		return privinceCode;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param provinceName
	 *            the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @param privinceCode
	 *            the privinceCode to set
	 */
	public void setPrivinceCode(String privinceCode) {
		this.privinceCode = privinceCode;
	}

}
