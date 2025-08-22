package tech.ada. resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import tech.ada.model.Accessory;

@Path("/api/v1/accessories") 
public class AccessoryResource {
    @GET
    public Response all() {
        return Response.ok(Accessory.ListAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Accessory accessory = Accessory.findById(id);
        System.out.println(accessory);
        return Response.ok(accessory).build();
    }

}



