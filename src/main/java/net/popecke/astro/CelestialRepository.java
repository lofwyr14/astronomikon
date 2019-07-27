package net.popecke.astro;

import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
@View( name = "all", map = "function(doc) { if (doc.type == 'Celestial' ) emit( null, doc._id )}")
public class CelestialRepository extends CouchDbRepositorySupport<Celestial> {

  public CelestialRepository() {
    super(Celestial.class, CouchDb.getConnector());
    initStandardDesignDocument(); // todo: later in production do it otherwise
  }

/*
  @Override
  @View( name="all", map = "function(doc) { if (doc.title) { emit(doc.dateCreated, doc._id) } }")
  public List<Celestial> getAll() {
    ViewQuery q = createQuery("all").descending(true);
    return db.queryView(q, Celestial.class);
  }
*/

}
