
package no.finntech.cc;

import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public final class CassandraRepositoryTest {

    @Test
    public void test_getValue() {

        String ks = "repo_test_getValue";

        CassandraRepository repo = new CassandraRepository(
                Collections.singletonList("localhost"),
                Integer.parseInt(System.getProperty("cassandra.port")),
                ks + "_repository_getValue");

        repo.setValue("test-scope", "test-key", "test-value");
        Assert.assertTrue(repo.getValue("test-scope", "test-key").isPresent());
        Assert.assertEquals("test-value", repo.getValue("test-scope", "test-key").get());
    }

    @Test
    public void test_getValues() {

        String ks = "repo_test_getValues";

        CassandraRepository repo = new CassandraRepository(
                Collections.singletonList("localhost"),
                Integer.parseInt(System.getProperty("cassandra.port")),
                ks + "test_getValues");

        repo.setValue("test-scope", "test-key", "test-value");
        repo.setValue("test-scope", "test-key-2", "test-value-2");
        Assert.assertEquals(2, repo.getValues("test-scope").size());
        Assert.assertTrue(repo.getValues("test-scope").containsKey("test-key"));
        Assert.assertTrue(repo.getValues("test-scope").containsKey("test-key-2"));
        Assert.assertTrue(repo.getValues("test-scope").containsValue("test-value"));
        Assert.assertTrue(repo.getValues("test-scope").containsValue("test-value-2"));
    }
}
