package de.fhws.fiw.pvs.restdemo;

import com.owlike.genson.annotation.JsonDateFormat;
import com.owlike.genson.annotation.JsonIgnore;

import java.util.Date;

/**
 * (c) Tobias Fertig, FHWS 2017
 */
public class Person
{
	private long id;

	private String firstName;

	private String lastName;

	private String password;

	@JsonDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	private Date birthday;

	public Person( )
	{
	}

	public Person( String firstName, String lastName, String password, Date birthday )
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.birthday = birthday;
	}

	public long getId( )
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public String getFirstName( )
	{
		return firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	public String getLastName( )
	{
		return lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	@JsonIgnore
	public String getPassword( )
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public Date getBirthday( )
	{
		return birthday;
	}

	public void setBirthday( Date birthday )
	{
		this.birthday = birthday;
	}
}
