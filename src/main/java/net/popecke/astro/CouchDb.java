package net.popecke.astro;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Named;
import javax.naming.InitialContext;

@ApplicationScoped
@Named
public class CouchDb {

  private static final Logger LOG = LoggerFactory.getLogger(CouchDb.class);

  private CouchDbConnector db;

  public CouchDb() {
    String couchDbUrl = System.getenv().get("COUCH_DB_URL");
    if (couchDbUrl == null) {
      couchDbUrl ="http://localhost:5984";
    }
    LOG.info("Couch DB URL: '{}'", couchDbUrl);
    try {
      final HttpClient httpClient = new StdHttpClient.Builder()
          .url(couchDbUrl)
          .build();

      final CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
      db = new StdCouchDbConnector("astro", dbInstance);

      db.createDatabaseIfNotExists();
    } catch (Exception e) {
      final String text = "Problems while connecting database url='" + couchDbUrl + "'!";
      LOG.error(text, e);
      throw new RuntimeException(text, e);
    }
  }

  public CouchDbConnector getDb() {
    return db;
  }

  public static CouchDbConnector getConnector() {
    final BeanManager beanManager = getBeanManager();
    CouchDb couchDb = null;
    if (beanManager != null) {
      couchDb = (CouchDb) beanManager.getBeans("couchDb");
    }
    if (couchDb == null) {
      LOG.error("CDI not giving me the couchDb"); // XXX How do use CDI correctly in this case?
      couchDb = new CouchDb();
    }
    return couchDb.getDb();
  }

  public static BeanManager getBeanManager() {
    try {
      return CDI.current().getBeanManager();
    } catch (Exception ignore) {
      LOG.error("CDI here!"); // XXX How do use CDI correctly in this case?
    }
    try {
      return (BeanManager) InitialContext.doLookup("java:comp/BeanManager");
    } catch (Exception ignore) {
    }
    try {
      return (BeanManager) InitialContext.doLookup("java:comp/env/BeanManager");
    } catch (Exception ignore) {
    }
    return null;
  }
}
