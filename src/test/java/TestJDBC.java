import com.tangz.jdbcdemo.DBPool;
import com.tangz.jdbcdemo.HelloJDBC;
import com.tangz.jdbcdemo.InsertBatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJDBC {
    private HelloJDBC jdbc = null;
    private InsertBatch insertBatch = null;
    private DBPool dbPool = null;

    @Before
    public void before() throws Exception {
        jdbc = new HelloJDBC();
        insertBatch = new InsertBatch();
        dbPool = new DBPool();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testJDBC() throws ClassNotFoundException {
        // jdbc.helloword();
        // insertBatch.insertUsers();
        dbPool.dbPoolRun();
    }
}
