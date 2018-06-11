package com.sap.hana.clould.samples.teamviewer.birthdayfinder.user;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.sap.hana.clould.samples.teamviewer.birthdayfinder.rest.ServiceDateDeserializer;

public class User {

	@JsonDeserialize(using = ServiceDateDeserializer.class)
	private Date dateOfBirth;
	private String userId;
	private String firstName;
	private String lastName;

	public User() {
	}

	public User(String userId, String firstName, String lastName, Date dateOfBirth) {
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
