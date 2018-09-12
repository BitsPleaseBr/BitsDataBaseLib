package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import model.bean.PacienteBean;
import model.bean.UserBean;
import model.bean.info.PacienteInfo;
import model.dao.statement.PacienteStatementFactory;

public class PacienteDao extends UserDao {

  
  private PacienteStatementFactory factory = new PacienteStatementFactory();


  public int cadastrar(PacienteBean bean) throws SQLException {

    int id = super.cadastrar(bean);

    bean.setInfo(PacienteInfo.IDUser, id);

    factory.insertStatement(bean).execute();

    return id;
  }

  public PacienteDao alterar(PacienteBean bean) throws SQLException {

    super.alterar(bean);

    factory.updateStatement(bean).execute();

    return this;
  }
  
  public PacienteDao deletar(int id) throws SQLException {
    
    super.deletar(id);
    return this;
  }

  public PacienteBean selecionar(int id) throws SQLException {

    UserBean ub = super.selecionar(id);

    PacienteBean pb = new PacienteBean();
    pb.getInfosUser().putAll(ub.getInfosUser());


    ResultSet rs = factory.selectStatement(id).executeQuery();

    while (rs.next()) {

      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {

        String colName = rsmd.getColumnName(i);

        for (PacienteInfo info : PacienteInfo.values()) {

          if (info.getCampo().equals(colName)) {
            
            pb.setInfo(info, rs.getObject(i));
            break;
          }
        }
      }
      
      return pb;
    }

    return pb;
  }
}
