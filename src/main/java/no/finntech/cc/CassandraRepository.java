

package no.finntech.cc;

import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;


final class CassandraRepository implements AutoCloseable {

    private final Cluster cluster;
    private final Session session;
    private final PreparedStatement getValue;
    private final PreparedStatement getValues;
    private final PreparedStatement setValue;


    CassandraRepository(final List<String> hosts, final int port, final String keyspace) {

        cluster = Cluster.builder()
                .addContactPoints(hosts.toArray(new String[hosts.size()]))
                .withPort(port)
                .build();

        CassandraSchema.checkKeySpaceAndTable(cluster, keyspace);

        session = cluster.connect(keyspace);

        getValue = session.prepare(QueryBuilder.select("value")
                    .from(CassandraSchema.TABLE_NAME)
                    .where(QueryBuilder.eq("scope", QueryBuilder.bindMarker("scope")))
                    .and(QueryBuilder.eq("key", QueryBuilder.bindMarker("key"))));

        getValues = session.prepare(QueryBuilder.select("key", "value")
                    .from(CassandraSchema.TABLE_NAME)
                    .where(QueryBuilder.eq("scope", QueryBuilder.bindMarker("scope"))));

        setValue = session.prepare(QueryBuilder.insertInto(CassandraSchema.TABLE_NAME)
                    .value("scope", QueryBuilder.bindMarker("scope"))
                    .value("key", QueryBuilder.bindMarker("key"))
                    .value("value", QueryBuilder.bindMarker("value")));
    }

    public Optional<String> getValue(String scope, String key) {
        Row row = session.execute(getValue.bind().setString("scope", scope).setString("key", key)).one();
        return Optional.ofNullable(row).map(r -> r.getString("value"));

    }

    public Map<String,String> getValues(String scope) {
        ResultSet results = session.execute(getValues.bind().setString("scope", scope));

        Map<String,String> values = new HashMap<>();
        for ( Row row : results) {
            values.put(row.getString("key"), row.getString("value"));
        }
        return Collections.unmodifiableMap(values);
    }

    void setValue(String scope, String key, String value) {
        session.execute(setValue.bind().setString("scope", scope).setString("key", key).setString("value", value));
    }

    @Override
    public void close()  {
        session.close();
        cluster.close();
    }

}
