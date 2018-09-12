package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import model.bean.EnderecoBean;
import model.bean.MedicoBean;
import model.bean.TelefoneBean;
import model.bean.UserBean;
import model.bean.info.MedicoInfo;
import model.dao.statement.MedicoStatementFactory;

public class MedicoDao extends UserDao {

  
  private MedicoStatementFactory factory = new MedicoStatementFactory();
  

  public int cadastrar(MedicoBean bean) throws SQLException {

    int id = super.cadastrar(bean);

    bean.setInfo(MedicoInfo.IDUser, id);

    factory.insertStatement(bean).execute();

    new EnderecoDao().cadastrar(bean.getEnderecos(), id);
    new TelefoneDao().cadastrar(bean.getTelefones(), id);

    return id;
  }

  public MedicoDao alterar(MedicoBean bean) throws SQLException {

    super.alterar(bean);

    factory.updateStatement(bean).execute();

    for (EnderecoBean enderecoBean : bean.getEnderecos())
      new EnderecoDao().alterar(enderecoBean);
    
    for (TelefoneBean telefoneBean : bean.getTelefones())
      new TelefoneDao().alterar(telefoneBean);
    
    return this;
  }
  
  public MedicoDao deletar(int id) throws SQLException {
    
    super.deletar(id);
    return this;
  }

  public MedicoBean selecionar(int id) throws SQLException {

    UserBean ub = super.selecionar(id);

    MedicoBean mb = new MedicoBean();
    mb.getInfosUser().putAll(ub.getInfosUser());

    ResultSet rs = factory.selectStatement(id).executeQuery();

    while (rs.next()) {

      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {

        String colName = rsmd.getColumnName(i);

        for (MedicoInfo info : MedicoInfo.values()) {

          if (info.getCampo().equals(colName)) {
            
            mb.setInfo(info, rs.getObject(i));
            break;
          }
        }
      }
    }

    mb.addEnderecos(new EnderecoDao().selecionar(id));
    mb.addTelefones(new TelefoneDao().selecionar(id));

    return mb;
  }

}
