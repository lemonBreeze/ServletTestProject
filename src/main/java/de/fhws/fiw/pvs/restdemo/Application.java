package de.fhws.fiw.pvs.restdemo;

import com.owlike.genson.GensonBuilder;
import com.owlike.genson.ext.jaxrs.GensonJaxRSFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by braunpet on 24.04.17.
 */
@ApplicationPath( "api" )
public class Application extends ResourceConfig
{
	public Application( )
	{
		super( );
		registerClasses( getServiceClasses( ) );
		packages( "org.glassfish.jersey.examples.linking" );
		register( DeclarativeLinkingFeature.class );
		register( MultiPartFeature.class );
		register( CorsFilter.class );
		register( new GensonJaxRSFeature( ).use(
			new GensonBuilder( ).setSkipNull( true )
								.useIndentation( true )
								.useDateAsTimestamp( false )
								.useDateFormat( new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" ) )
								.create( ) ) );
	}

	protected Set<Class<?>> getServiceClasses( )
	{
		final Set<Class<?>> returnValue = new HashSet<>( );

		returnValue.add( PersonService.class );

		return returnValue;
	}
}
