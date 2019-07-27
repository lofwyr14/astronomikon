package net.popecke.astro;

import org.ektorp.support.CouchDbDocument;

import java.io.Serializable;
import java.util.Date;

public class AbstractEntity extends CouchDbDocument implements Serializable {

  private String name;
  private Date releaseDate;
  private Date updateDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
