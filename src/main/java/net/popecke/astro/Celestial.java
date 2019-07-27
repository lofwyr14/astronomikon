package net.popecke.astro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.ektorp.support.TypeDiscriminator;

import java.beans.Transient;

@JsonIgnoreProperties(value = {"id", "revision"})
public class Celestial extends AbstractEntity {

  private String description;
  private String url;

  private double rightAscension;
  private double declination;
  private double apparentSize;

  public Celestial() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public double getRightAscension() {
    return rightAscension;
  }

  public void setRightAscension(double rightAscension) {
    this.rightAscension = rightAscension;
  }

  public double getDeclination() {
    return declination;
  }

  public void setDeclination(double declination) {
    this.declination = declination;
  }

  public double getApparentSize() {
    return apparentSize;
  }

  /** @deprecated use a converter */
  @Deprecated
  @Transient
  public String getApparentSizeString() {
    return AngleCalculator.format(apparentSize);
  }

  public void setApparentSize(double apparentSize) {
    this.apparentSize = apparentSize;
  }

  @TypeDiscriminator
  public String getType() {
    return getClass().getSimpleName();
  }

  // XXX a bit ugly
  public void setType(String type) {
    assert type.equals(getClass().getSimpleName());
  }

  @Override
  public String toString() {
    return "Celestial{" +
        "id='" + getId() + '\'' +
        ", revision='" + getRevision() + '\'' +
        ", name='" + getName() + '\'' +
        ", description='" + description + '\'' +
        ", url='" + url + '\'' +
        ", rightAscension=" + rightAscension +
        ", declination=" + declination +
        ", apparentSize=" + apparentSize +
        '}';
  }
}
