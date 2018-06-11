package com.sap.hana.clould.samples.teamviewer.birthdayfinder.employee;

import java.util.List;

public class EmployeesWrapper {
	private List<Employee> employees;

	public EmployeesWrapper() {
	}

	public EmployeesWrapper(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
}
