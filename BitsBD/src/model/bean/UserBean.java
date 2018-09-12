package model.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.bean.info.UserInfo;

public class UserBean {


  private HashMap<UserInfo, Object> infosUser = new HashMap<>();
  private ArrayList<EnderecoBean> enderecos = new ArrayList<>();
  private ArrayList<TelefoneBean> telefones = new ArrayList<>();
  
  
  public UserBean() {
    
    this.infosUser.put(UserInfo.Situacao, 1);
  }
  

  public UserBean setInfo(UserInfo key, Object value) {

    this.infosUser.put(key, key.parse(value));

    return this;
  }

  public Object getInfo(UserInfo key) {

    return this.infosUser.get(key);
  }

  public HashMap<UserInfo, Object> getInfosUser() {

    return this.infosUser;
  }


  public UserBean addTelefone(TelefoneBean bean) {
    
    this.telefones.add(bean);
    return this;
  }
  
  public UserBean addTelefones(List<TelefoneBean> beans) {
    
    for (TelefoneBean bean : beans)
      this.telefones.add(bean);    
    return this;
  }
  
  public UserBean addEndereco(EnderecoBean bean) {
    
    this.enderecos.add(bean);
    return this;
  }
  
  public UserBean addEnderecos(List<EnderecoBean> beans) {
    
    for (EnderecoBean bean : beans)
      this.enderecos.add(bean);
    return this;
  }
  
  public ArrayList<EnderecoBean> getEnderecos() {
    
    return this.enderecos;
  }
  
  public ArrayList<TelefoneBean> getTelefones() {
    
    return this.telefones;
  }
}