package net.popecke.astro;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class CelestialController extends AbstractEntityController<Celestial> implements Serializable {

  @Inject
//  @Dependent
  private CelestialRepository repository;

  @PostConstruct
  public void init() {
    init(Celestial.class, repository);
  }

}
