package com.sap.hana.clould.samples.teamviewer.birthdayfinder.employee;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest.ServiceDateSerializer;

public class Employee {

	@JsonSerialize(using = ServiceDateSerializer.class)
	private Date dateOfBirth;
	private String userId;
	private String firstName;
	private String lastName;

	public Employee(String userId, String firstName, String lastName, Date dateOfBirth) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

}
