package org.anc.web;

import org.anc.resource.ResourceLoader;

import javax.print.attribute.standard.Media;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @author Keith Suderman
 */


/**
 * @author Keith Suderman
 */
public class WebUtil {

	public static Response ok() {
		return Response.ok().build();
	}

	public static Response ok(Object entity) {
		return Response.ok(entity).build();
	}

	public static Response ok(Object entity, String type) {
		return Response.ok(entity, type).build();
	}

	public static Response ok(Object entity, MediaType type) {
		return Response.ok(entity, type).build();
	}

	public static Response text(Object entity)
	{
		return Response.ok(entity, MediaType.TEXT_PLAIN).build();
	}

	public static Response text(Object entity, Map<String,String> headers) {
		Response.ResponseBuilder builder = Response.ok(entity, MediaType.TEXT_PLAIN);
		for (Map.Entry<String,String> entry : headers.entrySet())
		{
			builder.header(entry.getKey(), entry.getValue());
		}
		return builder.build();
	}

	public static Response json(Object entity) {
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
	}
	public static Response json(Object entity, Map<String,String> headers) {
		Response.ResponseBuilder builder = Response.ok(entity, MediaType.APPLICATION_JSON);
		for (Map.Entry<String,String> entry : headers.entrySet())
		{
			builder.header(entry.getKey(), entry.getValue());
		}
		return builder.build();
	}


	public static Response html(Object entity) {
		return Response.ok(entity, MediaType.TEXT_HTML).build();
	}
	public static Response html(Object entity, Map<String,String> headers) {
		Response.ResponseBuilder builder = Response.ok(entity, MediaType.TEXT_HTML);
		for (Map.Entry<String,String> entry : headers.entrySet())
		{
			builder.header(entry.getKey(), entry.getValue());
		}
		return builder.build();
	}


	public static Response status(int code, Object entity) {
		return Response.status(code).entity(entity).build();
	}

	public static Response status(int code, Object entity, String type) {
		return Response.status(code).entity(entity).type(type).build();
	}

	public static Response status(int code, Object entity, MediaType type) {
		return Response.status(code).entity(entity).type(type).build();
	}

	public static Response error(Object entity) {
		return Response.serverError().entity(entity).build();
	}
}
