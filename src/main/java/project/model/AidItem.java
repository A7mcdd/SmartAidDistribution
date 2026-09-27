package project.model;

import java.io.Serializable;


public abstract class AidItem implements Serializable {
	private static final long serialVersionUID = 1L; //  بنحط هاد الشي عشان لما نعدل على الكلاس الملفات القديمة تنفتح بدون مشاكل

	protected String code;
	protected String description;

	public AidItem(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public abstract String getCategory();

	@Override
	public String toString() {
		return "Code: " + code + ", Description: " + description + ", Category: " + getCategory();
	}




}





