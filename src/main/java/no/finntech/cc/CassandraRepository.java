

package no.finntech.cc;

import java.util.List;
import java.util.Map;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


final class CassandraRepository {

    private final Session session;

    CassandraRepository(final List<String> hosts, final int port, final String keyspace) {

        Cluster cluster = Cluster.builder()
                .addContactPoints(hosts.toArray(new String[hosts.size()]))
                .withPort(port)
                .build();

        CassandraSchema.checkKeySpaceAndTable(cluster, keyspace);
        session = cluster.connect(keyspace);
    }

    public String getValue(final String scope, final String key) {

    }

    public Map<String,String> getValues(final String scope)  {

    }

    public void setValue(final String scope, final String key, final String value) {

    }

}
