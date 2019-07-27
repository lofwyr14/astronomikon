package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.StringTokenizer;

@FacesConverter("rightAscension")
public class RightAscensionConverter implements Converter {

  private static final Logger LOG = LoggerFactory.getLogger(RightAscensionConverter.class);

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
    StringTokenizer tokenizer = new StringTokenizer(value, " hmsHMS", true);
    double sum = 0;
    int current = 0;
    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      switch (token) {
        case " ":
          // ignore
        case "h":
        case "H":
          if (current != 0) {
            sum += current;
            current = 0;
          }
          break;
        case "m":
        case "M":
          if (current != 0) {
            sum += current / 60.0;
            current = 0;
          }
          break;
        case "s":
        case "S":
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
    while (sum < 0) {
      sum += 24;
    }
    while (sum > 24) {
      sum -= 24;
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
      return h + "h " + m + "m " + s + "s";
    } else {
      return null;
    }
  }
}
