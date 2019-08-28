package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.StringTokenizer;

@FacesConverter("angle")
public class AngleConverter implements Converter {

  private static final Logger LOG = LoggerFactory.getLogger(AngleConverter.class);

  protected double getMax() {
    return 360;
  }

  protected double getMin() {
    return 0;
  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
    StringTokenizer tokenizer = new StringTokenizer(value, " °'\"", true);
    double sum = 0;
    int current = 0;
    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      switch (token) {
        case " ":
          // ignore
        case "°":
          if (current != 0) {
            sum += current;
            current = 0;
          }
          break;
        case "'":
          if (current != 0) {
            sum += current / 60.0;
            current = 0;
          }
          break;
        case "\"":
          if (current != 0) {
            sum += current / 3600.0;
            current = 0;
          }
          break;
        default:
          try {
            current = Integer.parseInt(token);
          } catch (NumberFormatException e) {
            throw new ConverterException(e);
          }
          break;
      }
    }
    while (sum < getMin()) {
      throw new ConverterException("Wert zu klein!");
    }
    while (sum > getMax()) {
      throw new ConverterException("Wert zu groß!");
    }
    return sum;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    Double d = (Double) value;
    if (d != null) {
      int h = (int) (double) d;
      d -= h;
      d *= 60;
      int m = (int) (double) d;
      d -= m;
      d *= 60;
      int s = (int) (double) (d + 0.5);
      if (h > 0 && s > 0) {
        return h + "° " + m + "' " + s + "\"";
      }
      if (h > 0 && m > 0) {
        return h + "° " + m + "'";
      }
      if (h > 0) {
        return h + "°";
      }
      if (m > 0 && s > 0) {
        return m + "' " + s + "\"";
      }
      if (m > 0) {
        return m + "'";
      }
      if (s > 0) {
        return s + "\"";
      }
      return "0°";
    } else {
      return null;
    }
  }
}
