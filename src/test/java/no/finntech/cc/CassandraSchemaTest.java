package no.finntech.cc;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.KeyspaceMetadata;
import org.junit.Assert;
import org.junit.Test;


public final class CassandraSchemaTest {

    @Test
    public void checkKeySpaceAndTable() {

        String test_keyspace = "cassandraschematest_checkkeyspaceandtable";

        Cluster cluster = Cluster.builder()
                .addContactPoints("localhost")
                .withPort(Integer.parseInt(System.getProperty("cassandra.port")))
                .build();

        CassandraSchema.checkKeySpaceAndTable(cluster, test_keyspace);

        KeyspaceMetadata md = cluster.getMetadata().getKeyspace(test_keyspace);
        Assert.assertNotNull(md);
        Assert.assertNotNull(md.getTable(CassandraSchema.TABLE_NAME));


        CassandraSchema.checkKeySpaceAndTable(cluster, test_keyspace);
        CassandraSchema.checkKeySpaceAndTable(cluster, test_keyspace);
        CassandraSchema.checkKeySpaceAndTable(cluster, test_keyspace);
        CassandraSchema.checkKeySpaceAndTable(cluster, test_keyspace);
        CassandraSchema.checkKeySpaceAndTable(cluster, test_keyspace);

    }
}
