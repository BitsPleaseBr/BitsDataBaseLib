package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.MedicoBean;
import model.bean.info.Info;
import model.bean.info.MedicoInfo;
import model.bean.info.Tabela;

public class MedicoStatementFactory extends StatementFactory {

  
  public PreparedStatement insertStatement(MedicoBean bean) {
    
    return createInsertStatement(Tabela.Medico, bean.getInfosMed());
  }
  
  public PreparedStatement updateStatement(MedicoBean bean) {
    
    return createUpdateStatement(Tabela.Medico, bean.getInfosMed(), new Info[] {MedicoInfo.IDUser}, new Object[] {bean.getInfo(MedicoInfo.IDUser)});
  }
  
  public PreparedStatement deleteStatement(int id) {
    
    return createDeleteStatement(Tabela.Medico, new Info[] {MedicoInfo.IDUser}, new Object[] {id});
  }
  
  public PreparedStatement selectStatement(int id) {
    
    return createSelectStatement(Tabela.Medico, new Info[] {MedicoInfo.IDUser}, new Object[] {id}, MedicoInfo.values());
  }
}