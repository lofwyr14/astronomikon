package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("focalLength")
public class FocalLengthConverter implements Converter {

  private static final Logger LOG = LoggerFactory.getLogger(FocalLengthConverter.class);

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
    LOG.error("Not implemented");
    return null;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    Integer focalLength = (Integer) value;
    return focalLength + " mm";
  }
}
