package com.test.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.domain.Employee;
import com.test.domain.Response;

@RestController
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmployeeController.class);
	private static String DATABASE_IP;
	private static final String INSERT_RECORD = "INSERT INTO employee (name, age) VALUES (?,?)";
	private static final String SELECT_ALL_RECORDS = "SELECT * FROM test.employee";
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DOCKER_CONTAINER_NAME = "db";
	private static final String DATABASE_NAME = "test";
	private static final String DATABASE_PORT = "3306";
	private static final String DATABASE_USER = "test";
	private static final String DATABASE_PASSWORD = "test";

	static {
		try {
			DATABASE_IP = InetAddress.getByName(DOCKER_CONTAINER_NAME)
					.getHostAddress();
		} catch (UnknownHostException e) {
			LOGGER.error("Error Occurred while getting Database IP Address", e);
		}
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public Response addEmployee(@RequestBody Employee employee) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT_RECORD);
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getAge());
			preparedStatement.executeUpdate();
		} catch (Exception ex) {
			LOGGER.error("Exception inserting record", ex);
			return new Response("Exception inserting record " + ex.getMessage());
		}
		return new Response("Record Inserted!");
	}

	@RequestMapping(value = "/getEmployees", method = RequestMethod.GET)
	public List<Employee> getEmployees() throws ClassNotFoundException {
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_ALL_RECORDS);
			while (rs.next()) {
				employeeList.add(new Employee(rs.getString("name"), rs
						.getString("age")));
			}
			rs.close();
		} catch (SQLException e) {
			LOGGER.error("Exception fetching record", e);
		}
		return employeeList;
	}

	private Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(MYSQL_DRIVER);
		return DriverManager.getConnection("jdbc:mysql://" + DATABASE_IP + ":"
				+ DATABASE_PORT + "/" + DATABASE_NAME, DATABASE_USER,
				DATABASE_PASSWORD);
	}
}