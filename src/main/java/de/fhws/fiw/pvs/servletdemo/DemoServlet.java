package de.fhws.fiw.pvs.servletdemo;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet( name = "ServletExample", urlPatterns = { "/test" } )
public class DemoServlet extends javax.servlet.http.HttpServlet
{
	@Override
	protected void doPost( javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response ) throws javax.servlet.ServletException, IOException
	{}

	@Override
	protected void doGet( javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response )
		throws javax.servlet.ServletException, IOException
	{
		response.getWriter( ).print( "Hello World" );
	}
}
