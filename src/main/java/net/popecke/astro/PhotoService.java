package net.popecke.astro;

import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@ApplicationScoped
@Named
@View( name = "all", map = "function(doc) { if (doc.type == 'Photo') emit( null, doc._id )}")
public class PhotoService extends CouchDbRepositorySupport<Photo> {

  private static final Logger LOG = LoggerFactory.getLogger(PhotoService.class);

  @Inject
  public PhotoService() {
    super(Photo.class, CouchDb.getConnector());
    initStandardDesignDocument(); // todo: later in production do it otherwise
  }

  @Override
  public List<Photo> getAll() {
    return super.getAll();
  }
}
