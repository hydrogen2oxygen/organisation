package net.hydrogen2oxygen.organisation.adapter;

import io.javalin.Javalin;
import net.hydrogen2oxygen.organisation.domain.Organisation;
import net.hydrogen2oxygen.organisation.services.Database;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.javalin.http.staticfiles.Location;

public class Adapter {

    private static final String ORGANISATION = "/organisation";
    private Log log = LogFactory.getLog(Adapter.class);

    private Javalin app;

    public void init(Location location) {

        log.info("Location " + location.name());

        app = Javalin.create(config -> {
            if (Location.CLASSPATH.equals(location)) {
                config.addStaticFiles("static", Location.CLASSPATH);
            } else {
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
        // ConnectionInfo
        app.post(ORGANISATION, ctx -> {
            ctx.status(200);
            Organisation organisation = ctx.bodyStreamAsClass(Organisation.class);
            Database.instance().saveOrganisation(organisation);
        });
        app.put(ORGANISATION, ctx -> {
            ctx.status(200);
            Organisation organisation = ctx.bodyStreamAsClass(Organisation.class);
            Database.instance().saveOrganisation(organisation);
        });
        app.get(ORGANISATION, ctx -> ctx.json(Database.instance().loadOrganisation()));
    }
}
