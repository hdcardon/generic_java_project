package com.sap.hana.clould.samples.teamviewer.birthdayfinder.user;

import java.util.List;

public class UsersWrapper {

	private List<User> results;

	public UsersWrapper() {
	}

	public UsersWrapper(List<User> results) {
		this.results = results;
	}

	public List<User> getResults() {
		return results;
	}

}
