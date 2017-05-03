package de.fhws.fiw.pvs.restdemo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by braunpet on 24.04.17.
 */
@Path( "users" )
public class UserService
{
	private static long counter = 0L;

	private static Long getNextID( )
	{
		return counter++;
	}

	private static Map<Long, Person> persons = new HashMap<>( );

	@Context
	protected UriInfo uriInfo;

	@Context
	protected ContainerRequestContext requestContext;

	@Context
	protected Request request;

	@Context
	protected HttpServletRequest httpServletRequest;

	@GET
	@Path("ping")
	@Produces( MediaType.TEXT_PLAIN )
	public Response ping( )
	{
		return Response.ok( "Hello All Persons!" ).build( );
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public Response searchPersons( @QueryParam( "firstName" ) @DefaultValue( "" ) String filterFirstName,
		@QueryParam( "lastName" ) @DefaultValue( "" ) String filterLastName )
	{
		List<Person> personList = new ArrayList<>( );

		for ( Map.Entry<Long, Person> personEntry : persons.entrySet( ) )
		{
			personList.add( personEntry.getValue( ) );
		}

		if ( !filterFirstName.equals( "" ) )
		{
			personList = filterByFirstName( personList, filterFirstName );
		}

		if ( !filterLastName.equals( "" ) )
		{
			personList = filterByLastName( personList, filterLastName );
		}

		return Response.ok( personList ).build( );
	}

	private List<Person> filterByLastName( List<Person> persons, String lastName )
	{
		List<Person> filteredPersons = new ArrayList<>( );

		for ( Person person : persons )
		{
			if ( person.getLastName( ).equals( lastName ) )
			{
				filteredPersons.add( person );
			}
		}

		return filteredPersons;
	}

	private List<Person> filterByFirstName( List<Person> persons, String firstName )
	{
		List<Person> filteredPersons = new ArrayList<>( );

		for ( Person person : persons )
		{
			if ( person.getFirstName( ).equals( firstName ) )
			{
				filteredPersons.add( person );
			}
		}

		return filteredPersons;
	}

	@GET
	@Path( "{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public Response getPersonById( @PathParam( "id" ) long id )
	{
		if ( persons.get( id ) == null )
		{
			throw new WebApplicationException( Response.status( 404 ).build( ) );
		}
		return Response.ok( persons.get( id ) ).build( );
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	public Response createPerson( Person person )
	{
		Long id = getNextID( );

		person.setId( id );

		persons.put( id, person );

		URI locationURI = uriInfo.getAbsolutePathBuilder( ).path( Long.toString( id ) ).build( new Object[ 0 ] );

		return Response.created( locationURI ).build( );
	}

	@PUT
	@Path( "{id}" )
	@Consumes( MediaType.APPLICATION_JSON )
	public Response updatePerson( @PathParam( "id" ) long id, Person person )
	{
		Person oldPerson = persons.get( id );

		person.setId( id );

		persons.put( id, person );

		Response response;

		if ( oldPerson == null )
		{

			response = Response.created( uriInfo.getAbsolutePath( ) ).build( );
		} else
		{
			response = Response.ok( person ).build( );
		}

		return response;
	}

	@DELETE
	@Path( "{id}" )
	public Response deletePerson( @PathParam( "id" ) long id )
	{
		persons.remove( id );

		return Response.noContent( ).build( );
	}
}
