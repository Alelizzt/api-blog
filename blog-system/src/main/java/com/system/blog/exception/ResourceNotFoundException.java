package com.system.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String resourceName;
	private String nameField;
	private long valueField;

	public ResourceNotFoundException(String resourceName, String nameField, long valueField) {
		super(String.format("%s not found with : %s : '%s'", resourceName, nameField, valueField));
		this.resourceName = resourceName;
		this.nameField = nameField;
		this.valueField = valueField;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public long getValueField() {
		return valueField;
	}

	public void setValueField(long valueField) {
		this.valueField = valueField;
	}

}
