package Controller;

import Schemas.SchemaConvector;
import Service.FileConverterService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/converter")
@Tag(name="Конвектор файлов", description="Подсчитывает количество символов в файле")
public class ConverterController {
    @ConfigProperty(name = "path.dirOriginal")
    String pathOriginal;
    @ConfigProperty(name = "path.dirModified")
    String pathModified;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user","admin"})
    public SchemaConvector convertFile(@QueryParam String Parameter) {
        FileConverterService.copyFile(pathOriginal, pathModified,Parameter);
        return SchemaConvector.of(pathOriginal,pathModified,FileConverterService.getQuantity_Processed_Files(),Parameter);
    }
}
