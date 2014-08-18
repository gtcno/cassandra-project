
package no.finntech.cc;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


final class CassandraSchema {

    static void checkKeySpaceAndTable(final Cluster cluster, final String keyspace) {
        try (Session session = cluster.connect()) {

        }
    }

    private CassandraSchema() {}
}
