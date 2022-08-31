package net.hydrogen2oxygen.organisation.services;

import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.OpenApi;
import net.hydrogen2oxygen.organisation.domain.Organisation;

import java.io.IOException;
import io.javalin.plugin.openapi.annotations.*;
import net.hydrogen2oxygen.organisation.exceptions.ErrorResponse;

import static net.hydrogen2oxygen.organisation.services.Database.instance;

public class OrganisationController {

    @OpenApi(
            summary = "Create or update Organisation",
            operationId = "saveOrUpdateOrganisation",
            path = "/organisation",
            method = HttpMethod.POST,
            tags = {"Organisation"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = Organisation.class)}),
            responses = {
                    @OpenApiResponse(status = "200"),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void saveOrUpdateOrganisation(Context ctx) throws IOException {
        ctx.status(200);
        Organisation org = ctx.bodyStreamAsClass(Organisation.class);
        org = Database.instance().saveOrganisation(org);
        ctx.json(org);
    }

    @OpenApi(
            summary = "Load Organisation",
            operationId = "loadOrganisation",
            path = "/organisation",
            method = HttpMethod.GET,
            tags = {"Organisation"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Organisation.class)}),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void loadOrganisation(Context ctx) throws IOException {
        try {
            Organisation org = instance().loadOrganisation();
            ctx.json(org);
        } catch (IOException e) {
            ctx.json(new Organisation());
        }
    }
}
