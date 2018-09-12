package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.UserBean;
import model.bean.info.Info;
import model.bean.info.Tabela;
import model.bean.info.UserInfo;

public class UserStatementFactory extends StatementFactory {

  
  public PreparedStatement insertStatement(UserBean bean) {
    
    return createInsertStatement(Tabela.User, bean.getInfosUser());
  }
  
  public PreparedStatement updateStatement(UserBean bean) {
    
    return createUpdateStatement(Tabela.User, bean.getInfosUser(), new Info[] {UserInfo.ID}, new Object[] {bean.getInfo(UserInfo.ID)});
  }
  
  public PreparedStatement deleteStatement(int id) {
    
    return createDeleteStatement(Tabela.User, new Info[] {UserInfo.ID}, new Object[] {id});
  }
  
  public PreparedStatement selectStatement(int id) {
    
    return createSelectStatement(Tabela.User, new Info[] {UserInfo.ID}, new Object[] {id}, UserInfo.values());
  }
}