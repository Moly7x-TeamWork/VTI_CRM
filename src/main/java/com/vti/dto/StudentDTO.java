//
package com.vti.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

	private String phoneNumber;

	private String source;

	private String firstName;

	private String lastName;

	private String gender;

	private String email;

	private String birthDate;

	private String school;

	private String address;

	private String social;

	private String target;

	private String status;

	private String transHistory;

	public void setBirthDate(Date birthDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.birthDate = "'" + dateFormat.format(birthDate) + "'";
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return ",(" + phoneNumber + ", " + source + ", " + firstName + ", " + lastName + ", " + gender + ", " + email
				+ ", " + birthDate + ", " + school + ", " + address + ", " + social + ", " + target + ", " + status
				+ ", " + transHistory + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		StudentDTO other = (StudentDTO) obj;
		if (!(phoneNumber.equals(other.phoneNumber))) {
			return false;
		}
		return true;
	}

}
