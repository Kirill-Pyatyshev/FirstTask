package controller;

import service.FileConverterService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/converter")
@Tag(name="Конвертер файлов", description="Подсчитывает количество символов в файле")
public class ConverterController {

    @Inject
    FileConverterService fileConverterService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user","admin"})
    public Response startOfConversion(@QueryParam String Parameter) {
        fileConverterService.startOfConversion(Parameter);
        return Response.ok(fileConverterService).build();
    }
}
