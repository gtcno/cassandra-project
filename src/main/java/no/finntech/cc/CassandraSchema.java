
package no.finntech.cc;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;


final class CassandraSchema {

    static final String TABLE_NAME = "keystore";

    static void checkKeySpaceAndTable(final Cluster cluster, final String keyspace) {
        try (Session session = cluster.connect()) {

            session.execute("CREATE KEYSPACE IF NOT EXISTS " + keyspace
                    + " WITH replication = { 'class': 'NetworkTopologyStrategy', 'datacenter1':1};");

            session.execute("CREATE TABLE IF NOT EXISTS " + keyspace + "." + TABLE_NAME + " ("
                    + " scope text,"
                    + " key text,"
                    + " value text,"
                    + " PRIMARY KEY (scope, key));");
        }
    }

    private CassandraSchema() {}
}
