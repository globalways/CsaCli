package com.globalways.csacli.entity;

import com.google.gson.annotations.Expose;

public class IndustryEntity {
	@Expose
	private int id;
	@Expose
	private String industry_name;
	@Expose
	private String industry_icon;
	@Expose
	private String created;
	@Expose
	private String updated;

	public IndustryEntity() {
		super();
	}

	public IndustryEntity(int id, String industry_name, String industry_icon, String created, String updated) {
		super();
		this.id = id;
		this.industry_name = industry_name;
		this.industry_icon = industry_icon;
		this.created = created;
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "IndustryEntity [id=" + id + ", industry_name=" + industry_name + ", industry_icon=" + industry_icon
				+ ", created=" + created + ", updated=" + updated + "]";
	}

	
	
	/**
	 * compare Industry name
	 * @param another
	 * @author yangping.wang
	 * @return boolean
	 */
	public boolean compareName(String anotherName)
	{
		boolean result = false;
		result = anotherName.equals(this.getIndustry_name()) ? true : false;
		return result;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIndustry_name() {
		return industry_name;
	}

	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
	}

	public String getIndustry_icon() {
		return industry_icon;
	}

	public void setIndustry_icon(String industry_icon) {
		this.industry_icon = industry_icon;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
}
