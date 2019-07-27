package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("declination")
public class DeclinationConverter extends AngleConverter {

  private static final Logger LOG = LoggerFactory.getLogger(DeclinationConverter.class);

  protected double getMax() {
    return 90;
  }

  protected double getMin() {
    return -90;
  }

  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent component, String value) throws ConverterException {
    return super.getAsObject(facesContext, component, value);
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent component, Object value) throws ConverterException {
    return super.getAsString(facesContext, component, value);
  }
}
