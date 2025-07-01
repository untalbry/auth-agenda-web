package com.binarybrains.external.rest.controller;

import com.binarybrains.core.buisness.input.AuthenticationService;
import com.binarybrains.external.rest.dto.RegisterDto;
import com.binarybrains.external.rest.dto.SigningDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationCtrl {

    AuthenticationService authenticationService;
    @Inject
    public AuthenticationCtrl(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @POST
    @APIResponse(responseCode = "200", name = "Success", description = "User register successful")
    @APIResponse(responseCode = "400", name = "Bad request", description = "Error in the request")
    public Response register(@Valid RegisterDto registerDto){
        return authenticationService.register(registerDto.toEntity())
                .map(Response::ok)
                .getOrElseGet(errorCode -> Response.status(400).entity(errorCode)).build();
    }
    @POST
    @Path("/login")
    @APIResponse(responseCode = "200", name = "Success", description = "Authentication successful")
    @APIResponse(responseCode = "400", name = "Bad request", description = "Error in the request")
    @APIResponse(responseCode = "404", name = "Not found", description = "User not found")
    public Response login(@Valid SigningDto signingDto){
        return authenticationService.login(signingDto.toEntity())
                .map(userAuth -> Response.status(200).entity(userAuth).build())
                .getOrElseGet(errorCode -> Response.status(400).entity(errorCode).build());
    }
}
