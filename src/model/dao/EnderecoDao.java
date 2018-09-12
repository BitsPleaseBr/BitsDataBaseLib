package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.EnderecoBean;
import model.bean.info.EnderecoInfo;
import model.dao.statement.EnderecoStatementFactory;

public class EnderecoDao {


  private EnderecoStatementFactory factory = new EnderecoStatementFactory();


  public int cadastrar(EnderecoBean bean, int idUser) throws SQLException {

    bean.setInfo(EnderecoInfo.IDUser, idUser);

    factory.insertStatement(bean).execute();

    return idUser;
  }

  public int[] cadastrar(List<EnderecoBean> beans, int idUser) throws SQLException {

    int[] ids = new int[beans.size()];

    for (EnderecoBean bean : beans) {

      ids[beans.indexOf(bean)] = cadastrar(bean, idUser);
    }

    return ids;
  }

  public EnderecoDao alterar(EnderecoBean bean) throws SQLException {

    factory.updateStatement(bean).execute();
    return this;
  }

  public EnderecoDao deletar(int idUser) throws SQLException {

    factory.deleteStatement(idUser);
    return this;
  }

  public EnderecoDao deletar(int idUser, int tipo) throws SQLException {

    factory.deleteStatement(idUser, tipo).execute();
    return this;
  }

  public List<EnderecoBean> selecionar(int idUser) throws SQLException {

    List<EnderecoBean> beans = new ArrayList<>();

    ResultSet rs = factory.selectStatement(idUser).executeQuery();
    
    while (rs.next())
      beans.add(createBean(rs));

    return beans;
  }
  
  public EnderecoBean selecionar(int idUser, int tipo) throws SQLException {

    ResultSet rs = factory.selectStatement(idUser, tipo).executeQuery();
    
    EnderecoBean eb = new EnderecoBean();
    
    while (rs.next())
      eb = createBean(rs);
      
    return eb;
  }

  private EnderecoBean createBean(ResultSet rs) throws SQLException {

    EnderecoBean eb = new EnderecoBean();

    ResultSetMetaData rsmd = rs.getMetaData();

    for (int i = 1; i <= rsmd.getColumnCount(); i++) {

      String colName = rsmd.getColumnName(i);

      for (EnderecoInfo info : EnderecoInfo.values()) {

        if (info.getCampo().equals(colName)) {

          eb.setInfo(info, rs.getObject(i));
          break;
        }
      }
    }

    return eb;
  }
}
