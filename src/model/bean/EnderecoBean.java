package model.bean;

import java.util.HashMap;
import model.bean.info.EnderecoInfo;

public class EnderecoBean {

  
  public transient final int RESIDENCIAL = 1, COMERCIAL = 2;
  
  private HashMap<EnderecoInfo, Object> infosEnd = new HashMap<>();
  
  
  public EnderecoBean setInfo(EnderecoInfo key, Object value) {
    
    infosEnd.put(key, key.parse(value));
    
    return this;
  }
  
  public Object getInfo(EnderecoInfo key) {
    
    return infosEnd.get(key);
  }
  
  public HashMap<EnderecoInfo, Object> getInfosEnd() {
    
    return this.infosEnd;
  }
}