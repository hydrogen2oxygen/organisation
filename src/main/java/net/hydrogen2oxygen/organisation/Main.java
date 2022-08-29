package net.hydrogen2oxygen.organisation;

import io.javalin.http.staticfiles.Location;
import net.hydrogen2oxygen.organisation.adapter.Adapter;

public class Main {

    public static void main(String [] args) {

        Adapter adapter = new Adapter();

        if (args.length > 0 && "LOCAL".equals(args[0])) {
            adapter.init(Location.EXTERNAL);
        } else {
            adapter.init(Location.CLASSPATH);
        }
    }
}
