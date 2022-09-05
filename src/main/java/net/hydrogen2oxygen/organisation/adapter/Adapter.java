package net.hydrogen2oxygen.organisation.adapter;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import net.hydrogen2oxygen.organisation.domain.Organisation;
import net.hydrogen2oxygen.organisation.exceptions.ErrorResponse;
import net.hydrogen2oxygen.organisation.controller.OrganisationController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Adapter {

    private static final String ORGANISATION = "/organisation";
    private static final String ORGANISATION_IMPORT_HEADER = "/organisation-import-header";
    private static final String ORGANISATION_IMPORT_DATA = "/organisation-import-data";
    private static final String ORGANISATION_IMPORT_GEO_LOCATIONS = "/organisation-import-geo-locations";
    private final Log log = LogFactory.getLog(Adapter.class);

    private Javalin app;

    public void init(Location location) {

        log.info("Location " + location.name());

        app = Javalin.create(config -> {

            if (Location.CLASSPATH.equals(location)) {
                config.addStaticFiles("static", Location.CLASSPATH);
            } else {
                config.registerPlugin(getConfiguredOpenApiPlugin());
                config.addStaticFiles("src/main/resources/static", location);
            }

            config.enableCorsForAllOrigins();
        }).start(7000);

        app.exception(Exception.class, (e, ctx) -> {
            log.error(e);
            e.printStackTrace();
            ctx.status(500);
            ctx.json(e);
        });

        initDatabaseAdapter();
    }

    private void initDatabaseAdapter() {
        // CRUD, CREATE (or UPDATE), READ, UPDATE, DELETE (does not exist yet in this case)
        app.post(ORGANISATION, OrganisationController::saveOrUpdateOrganisation);
        app.get(ORGANISATION, OrganisationController::loadOrganisation);
        app.post(ORGANISATION_IMPORT_HEADER, OrganisationController::importOrganisationHeader);
        app.post(ORGANISATION_IMPORT_DATA, OrganisationController::importOrganisationData);
        app.post(ORGANISATION_IMPORT_GEO_LOCATIONS, OrganisationController::importGeoLocations);
    }

    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info = new Info().version("1.0").description("Organisation API");
        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor("net.hydrogen2oxygen.organisation")
                .path("/swagger-docs") // endpoint for OpenAPI json
                .swagger(new SwaggerOptions("/swagger")) // endpoint for swagger-ui
                .reDoc(new ReDocOptions("/redoc")) // endpoint for redoc
                .defaultDocumentation(doc -> {
                    doc.json("200", Organisation.class);
                    doc.json("500", ErrorResponse.class);
                    doc.json("503", ErrorResponse.class);
                });
        return new OpenApiPlugin(options);
    }
}
