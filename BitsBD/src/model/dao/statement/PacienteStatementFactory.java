package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.PacienteBean;
import model.bean.info.Info;
import model.bean.info.PacienteInfo;
import model.bean.info.Tabela;

public class PacienteStatementFactory extends StatementFactory {

  
  public PreparedStatement insertStatement(PacienteBean bean) {
    
    return createInsertStatement(Tabela.Paciente, bean.getInfosPac());
  }
  
  public PreparedStatement updateStatement(PacienteBean bean) {
    
    return createUpdateStatement(Tabela.Paciente, bean.getInfosPac(), new Info[] {PacienteInfo.IDUser}, new Object[] {bean.getInfo(PacienteInfo.IDUser)});
  }
  
  public PreparedStatement deleteStatement(int id) {
    
    return createDeleteStatement(Tabela.Paciente, new Info[] {PacienteInfo.IDUser}, new Object[] {id});
  }
  
  public PreparedStatement selectStatement(int id) {
    
    return createSelectStatement(Tabela.Paciente, new Info[] {PacienteInfo.IDUser}, new Object[] {id}, PacienteInfo.values());
  }
}