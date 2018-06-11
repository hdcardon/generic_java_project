package com.sap.hana.clould.samples.teamviewer.birthdayfinder.user;

public class UsersResponse {

	private UsersWrapper d;

	public UsersResponse() {
	}

	public UsersResponse(UsersWrapper d) {
		this.d = d;
	}

	public UsersWrapper getD() {
		return d;
	}

}
