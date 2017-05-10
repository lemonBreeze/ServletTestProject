package de.fhws.fiw.pvs.restdemo;

import com.owlike.genson.Genson;
import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * (c) Tobias Fertig, FHWS 2017
 */
public class UserServiceTests
{
	private final static MediaType JSON = MediaType.parse( "application/json; charset=utf-8" );

	private final static String BASE_URL = "http://localhost:8080/demo/api/users";

	private Genson genson;

	private OkHttpClient client;

	private List<String> createdLocations;

	private List<Person> createdPersons;

	@Before
	public void setUp()
	{
		genson = new Genson( );

		client = new OkHttpClient( );

		createdLocations = new ArrayList<>( );

		createdPersons = new ArrayList<>( );

		postUser( new Person( "Hans", "Wurst", "test", new Date( ) ) );
		postUser( new Person( "Hans1", "Wurst1", "test", new Date( ) ) );
		postUser( new Person( "Hans2", "Wurst2", "test", new Date( ) ) );
	}

	public void postUser(Person person)
	{
		RequestBody body = RequestBody.create( JSON, genson.serialize( person ) );

		Request request = new Request.Builder( ).url( BASE_URL )
												.post( body )
												.build( );

		Response response = executeRequest( request );

		if ( response.code( ) == 201 )
		{
			createdLocations.add( response.header( "Location" ) );
			createdPersons.add( person );
		}
	}

	@After
	public void tearDown()
	{
		for ( String createdLocation : createdLocations )
		{
			Request request = new Request.Builder( ).url( createdLocation ).delete( ).build( );

			Response response = executeRequest( request );
		}
	}

	@Test
	public void testCreateUser( ) throws IOException
	{
		Person person = new Person( "Hans3", "Wurst3", "test", new Date( ) );

		RequestBody body = RequestBody.create( JSON, genson.serialize( person ) );

		Request request = new Request.Builder( ).url( BASE_URL )
												.post( body )
												.build( );

		Response response = executeRequest( request );

		assertTrue( "Object was not created!", response.code( ) == 201 );

		String location = response.header( "Location" );

		createdLocations.add( location );
		createdPersons.add( person );

		request = new Request.Builder( ).url( location ).get( ).build( );

		response = executeRequest( request );

		assertTrue( "Object was not created!", response.code( ) == 200 );

	}

	@Test
	public void testDeleteUser( ) throws IOException
	{
		Request request = new Request.Builder( ).url( createdLocations.get( 0 ) ).delete( ).build( );

		Response response = executeRequest( request );

		assertTrue( "Object was not deleted!", response.code( ) == 204 );

		request = new Request.Builder( ).url( createdLocations.get( 0 ) ).get( ).build( );

		response = executeRequest( request );

		assertTrue( "Object was not deleted!", response.code( ) == 404 );

		if ( response.code( ) == 404 )
		{
			createdLocations.remove( 0 );
		}
	}

	@Test
	public void testUpdateUser() throws IOException
	{
		Person person = createdPersons.get( 1 );
		person.setLastName( "Test" );

		RequestBody body = RequestBody.create( JSON, genson.serialize( person ) );

		Request request = new Request.Builder( ).url( createdLocations.get( 1 ) ).put( body ).build( );

		Response response = executeRequest( request );

		assertTrue( "Person was not updated!", response.code( ) == 200 );

		assertTrue( "LastName not correct updated!", response.body( ).string( ).contains( "\"lastName\":\"Test\"" ) );
	}

	@Test
	public void testQueryUsers() throws IOException
	{
		Request request = new Request.Builder( ).url( BASE_URL + "?firstName=Hans" ).get( ).build( );

		Response response = executeRequest( request );

		assertTrue( "firstName not correct!", response.body( ).string( ).contains( "\"firstName\":\"Hans\"" ) );
	}

	private Response executeRequest( Request request )
	{
		Response response;

		try
		{
			response = client.newCall( request ).execute( );
		}
		catch ( IOException e )
		{
			response = null;
		}

		return response;
	}
}
