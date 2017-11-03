package net.popecke.astro;

import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
@View( name = "all", map = "function(doc) { if (doc.type == 'Camera' ) emit( null, doc._id )}")
public class CameraRepository extends CouchDbRepositorySupport<Camera> {

  public CameraRepository() {
    super(Camera.class, CouchDb.getConnector());
    initStandardDesignDocument(); // todo: later in production do it otherwise
  }

/*
  @Override
  @View( name="all", map = "function(doc) { if (doc.title) { emit(doc.dateCreated, doc._id) } }")
  public List<Camera> getAll() {
    ViewQuery q = createQuery("all").descending(true);
    return db.queryView(q, Camera.class);
  }
*/

}
