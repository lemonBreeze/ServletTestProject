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
@Path( "persons" )
public class PersonService
{
	private static long counter = 0L;

	private static Long getNextID( )
	{
		return counter++;
	}

	private static final Map<Long, Person> persons = new HashMap<>( );

	@Context
	protected UriInfo uriInfo;

	@Context
	protected ContainerRequestContext requestContext;

	@Context
	protected Request request;

	@Context
	protected HttpServletRequest httpServletRequest;

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public Response searchPersons( @QueryParam( "firstName" ) @DefaultValue( "" ) final String filterFirstName,
		@QueryParam( "lastName" ) @DefaultValue( "" ) final String filterLastName )
	{
		List<Person> personList = new ArrayList<>( );

		for ( final Map.Entry<Long, Person> personEntry : persons.entrySet( ) )
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

	private List<Person> filterByLastName( final List<Person> persons, final String lastName )
	{
		final List<Person> filteredPersons = new ArrayList<>( );

		for ( final Person person : persons )
		{
			if ( person.getLastName( ).equals( lastName ) )
			{
				filteredPersons.add( person );
			}
		}

		return filteredPersons;
	}

	private List<Person> filterByFirstName( final List<Person> persons, final String firstName )
	{
		final List<Person> filteredPersons = new ArrayList<>( );

		for ( final Person person : persons )
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
	public Response getPersonById( @PathParam( "id" ) final long id )
	{
		if ( persons.get( id ) == null )
		{
			throw new WebApplicationException( Response.status( 404 ).build( ) );
		}
		return Response.ok( persons.get( id ) ).build( );
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	public Response createPerson( final Person person )
	{
		final Long id = getNextID( );

		person.setId( id );

		persons.put( id, person );

		final URI locationURI = uriInfo.getAbsolutePathBuilder( ).path( Long.toString( id ) ).build( new Object[ 0 ] );

		return Response.created( locationURI ).build( );
	}

	@PUT
	@Path( "{id}" )
	@Consumes( MediaType.APPLICATION_JSON )
	public Response updatePerson( @PathParam( "id" ) final long id, final Person person )
	{
		final Person oldPerson = persons.get( id );

		person.setId( id );

		persons.put( id, person );

		final Response response;

		if ( oldPerson == null )
		{
			response = Response.created( uriInfo.getAbsolutePath( ) ).build( );
		}
		else
		{
			response = Response.ok( person ).build( );
		}

		return response;
	}

	@DELETE
	@Path( "{id}" )
	public Response deletePerson( @PathParam( "id" ) final long id )
	{
		persons.remove( id );

		return Response.noContent( ).build( );
	}
}
