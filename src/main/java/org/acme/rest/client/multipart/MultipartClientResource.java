package org.acme.rest.client.multipart;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/multipart")
public class MultipartClientResource {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response multipart(@MultipartForm MultipartFormDataInput input) {
        try {
            if (!input.getFormDataMap().containsKey("file")) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("The file part is unavailable.").build();
            }
            InputStream file = input.getFormDataPart("file", InputStream.class, null);
            String fileContents = IOUtils.toString(file, StandardCharsets.UTF_8);
            if ("Test file contents.".equals(fileContents)) {
                return Response.status(Response.Status.OK).entity("Success").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected content").build();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed").build();
        }
    }
}