
package no.finntech.cc;

import java.util.Collections;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;


public final class Main {

    public static void main(final String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<String> hostSpec = parser.accepts("host").withRequiredArg().defaultsTo("localhost");
        OptionSpec<Integer> portSpec = parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(9042);
        OptionSpec<String> keyspaceSpec = parser.accepts("keyspace").withRequiredArg().required();
        OptionSpec<String> scopeSpec = parser.accepts("scope").withRequiredArg().required();
        OptionSpec<String> keySpec = parser.accepts("key").withRequiredArg();
        OptionSpec<String> valueSpec = parser.accepts("value").withRequiredArg();

        OptionSet opts = parser.parse(args);

        List<String> hosts = opts.valuesOf(hostSpec);
        int port = opts.valueOf(portSpec);
        String keyspace = opts.valueOf(keyspaceSpec);
        String scope = opts.valueOf(scopeSpec);

        if (opts.has(valueSpec)) {
            String key = opts.valueOf(keySpec);
            String value = opts.valueOf(valueSpec);
            // @todo set some value
        } else if (opts.has(keySpec)) {
            // @todo get some value
            String value = "@todo get some value";
            System.out.println(value);
        } else {
            // @todo set some values
            Collections.singletonMap("test-key", "test-value").entrySet().stream().forEach((entries) -> {
                System.out.println(entries.getKey() + " --> " + entries.getValue());
            });
        }
        System.exit(0);
    }

}
