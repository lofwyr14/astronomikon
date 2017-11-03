package net.popecke.astro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.io.Serializable;

@JsonIgnoreProperties(value = {"id", "revision"})
public class Camera extends CouchDbDocument implements Serializable {

  private String name;
  private String manufacturer;
  private String sensorFormat;
  private double width;

  private double height;
  private int pixelCountX;
  private int pixelCountY;

  @Deprecated
  public Camera(String name, String sensorFormat, double width, double height, int pixelCountX, int pixelCountY) {
    this.name = name;
    this.sensorFormat = sensorFormat;
    this.width = width;
    this.height = height;
    this.pixelCountX = pixelCountX;
    this.pixelCountY = pixelCountY;
  }

  public Camera() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getSensorFormat() {
    return sensorFormat;
  }

  public void setSensorFormat(String sensorFormat) {
    this.sensorFormat = sensorFormat;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public int getPixelCountX() {
    return pixelCountX;
  }

  public void setPixelCountX(int pixelCountX) {
    this.pixelCountX = pixelCountX;
  }

  public int getPixelCountY() {
    return pixelCountY;
  }

  public void setPixelCountY(int pixelCountY) {
    this.pixelCountY = pixelCountY;
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
    return "Camera{" +
        "id='" + getId() + '\'' +
        ", revision='" + getRevision() + '\'' +
        ", name='" + name + '\'' +
        ", sensorFormat='" + sensorFormat + '\'' +
        ", width=" + width +
        ", height=" + height +
        ", pixelCountX=" + pixelCountX +
        ", pixelCountY=" + pixelCountY +
        '}';
  }
}
