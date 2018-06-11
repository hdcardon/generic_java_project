package com.sap.hana.clould.samples.teamviewer.birthdayfinder.birthday;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import com.sap.hana.clould.samples.teamviewer.birthdayfinder.connectivity.ConnectivityProvider;
import com.sap.hana.clould.samples.teamviewer.birthdayfinder.date.DateUtil;
import com.sap.hana.clould.samples.teamviewer.birthdayfinder.employee.Employee;
import com.sap.hana.clould.samples.teamviewer.birthdayfinder.employee.EmployeesWrapper;
import com.sap.hana.clould.samples.teamviewer.birthdayfinder.user.User;
import com.sap.hana.clould.samples.teamviewer.birthdayfinder.user.UsersResponse;

@Path("/birthdays")
@Produces(MediaType.APPLICATION_JSON)
public class BirthdayService {

	private static final String ODATA_DESTINATION_NAME = "sap_hcmcloud_core_odata";

	private static final int CONNECTION_TIMEOUT = 5 * 1000;

	private static final int READ_TIMEOUT = 10 * 1000;

	private static final int DEFAULT_UPCOMMING_DAYS = 365;

	private ConnectivityConfiguration connectivityConfig;

	public BirthdayService() {
		this(ConnectivityProvider.getConnectivityConfiguration());
	}

	public BirthdayService(ConnectivityConfiguration connectivityConfig) {
		this.connectivityConfig = connectivityConfig;
	}

	@GET
	@Path("/managedEmployees")
	public EmployeesWrapper getManagedEmployees(@QueryParam("upcomingDays") Integer upcomingDays, @Context HttpServletRequest request) {
		DestinationConfiguration destinationConfig = connectivityConfig.getConfiguration(ODATA_DESTINATION_NAME);
		if (destinationConfig == null) {
			throw new InternalServerErrorException();
		}

		String currentUser = request.getRemoteUser();

		// Get the destination URL
		String baseUrl = destinationConfig.getProperty("URL");
		String username = destinationConfig.getProperty("User");
		String password = destinationConfig.getProperty("Password");
		String requestUrl = baseUrl.replaceAll("/$", "") + "/User?$select=userId,firstName,lastName,dateOfBirth&$filter=manager/userId%20eq%20'"
				+ currentUser + "'";

		String credentials = username + ":" + password;
		BasicHeader authorizationHeader;
		try {
			authorizationHeader = new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			throw new InternalServerErrorException("Could not encode in base64");
		}

		BasicHeader acceptHeader = new BasicHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

		String proxyHost = System.getProperty("https.proxyHost");
		Integer proxyPort = Integer.valueOf(System.getProperty("https.proxyPort"));
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);

		String managedUsersResponse = null;
		try {
			// @formatter:off
			managedUsersResponse = Request.Get(requestUrl)
										  .setHeaders(authorizationHeader, acceptHeader)
										  .connectTimeout(CONNECTION_TIMEOUT)
										  .socketTimeout(READ_TIMEOUT)
										  .viaProxy(proxy)
										  .execute().returnContent().asString();
			// @formatter:on
		} catch (IOException e) {
			throw new InternalServerErrorException(String.format("Could not execute GET request to [%s]", requestUrl), e);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		UsersResponse usersResposne = null;
		try {
			usersResposne = mapper.readValue(managedUsersResponse, UsersResponse.class);
		} catch (IOException e) {
			throw new InternalServerErrorException(String.format("Cloud not parse response to [%s]", UsersResponse.class), e);
		}

		List<User> users = usersResposne.getD().getResults();
		List<Employee> employees = new ArrayList<>();
		for (User user : users) {
			employees.add(new Employee(user.getUserId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth()));
		}

		LocalDate endCheckDate = LocalDate.now().plusDays(upcomingDays != null ? upcomingDays : DEFAULT_UPCOMMING_DAYS);
		List<Employee> employeesWithBirthday = new ArrayList<>();
		for (Employee employee : employees) {
			LocalDate firstBirthday = employee.getDateOfBirth().toInstant().atZone(ZoneId.of(DateUtil.GMT_TIME_ZONE.getID())).toLocalDate();
			LocalDate thisYearBirthday = firstBirthday.withYear(LocalDate.now().getYear());
			// @formatter:off
			LocalDate upcomingBirthday = thisYearBirthday.isEqual(LocalDate.now()) || thisYearBirthday.isAfter(LocalDate.now()) 
					? thisYearBirthday
					: thisYearBirthday.plusYears(1);
			// @formatter:on

			if (upcomingBirthday.isEqual(endCheckDate) || upcomingBirthday.isBefore(endCheckDate)) {
				employeesWithBirthday.add(employee);
			}
		}
		return new EmployeesWrapper(employeesWithBirthday);
	}

}
