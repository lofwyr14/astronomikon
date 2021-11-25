//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("focalLength")
public class FocalLengthConverter implements Converter {
  private static final Logger LOG = LoggerFactory.getLogger(FocalLengthConverter.class);

  public FocalLengthConverter() {
  }

  public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
    LOG.error("Not implemented");
    return null;
  }

  public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    Integer focalLength = (Integer)value;
    return focalLength + " mm";
  }
}
