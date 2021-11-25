//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@ManagedBean
public class Album implements Serializable {
  private List<Photo> all = new ArrayList();

  public Album() {
    try {
      Target m27 = new Target();
      m27.setName("M27 - Hantelnebel");
      Photo p = new Photo();
      p.setTarget(m27);
      p.setBegin((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2017-08-22 23:52:29"));
      p.setThumb("http://schnurpfeil.de/astro/image/2017-08-22-Oldenburg-M27/M27-thumb.jpg");
      p.setPreview("http://schnurpfeil.de/astro/image/2017-08-22-Oldenburg-M27/M27-preview.jpg");
      p.setFull("http://schnurpfeil.de/astro/image/2017-08-22-Oldenburg-M27/M27-full.jpg");
      p.setCount(200);
      p.setCountDark(10);
      p.setCountBias(10);
      p.setCountFlat(0);
      p.setOptic(new Optic("Takahashi + Reducer", 60, 264));
      p.setDescription("M27");
      this.all.add(p);
    } catch (ParseException var3) {
      var3.printStackTrace();
    }

  }

  public int getSize() {
    return this.all.size();
  }

  public List<Photo> getAll() {
    return this.all;
  }
}
