package org.acme

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.jboss.resteasy.annotations.jaxrs.PathParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Named
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.quarkus.oidc.client.filter.OidcClientFilter
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.ws.rs.GET
import javax.ws.rs.Path

// TODO comment this to fix
@Named("ResourceHandler")
class LambdaHandler(
    @RestClient val exampleService: Service,
    val objectMapper: ObjectMapper,
) : RequestStreamHandler {
    override fun handleRequest(p0: InputStream, p1: OutputStream, p2: Context?) {
        val response = exampleService.getByName()
        p1.writer().use { it.write(objectMapper.writeValueAsString(response)) }
    }
}

// TODO Uncomment this to break
//@Path("/test")
//class Resource {
//    @RestClient
//    lateinit var service: Service
//
//    @GET
//    @Path("/")
//    fun name() = service.getByName()
//}





@Path("/")
@OidcClientFilter
@RegisterRestClient(configKey = "thingy")
interface Service {
    @GET
    @Path("/todos/1")
    fun getByName(): Todo
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Todo {
    var userId: String? = null
    var id: String? = null
    var title: String? = null
    var completed: Boolean? = null
}
