package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.TelefoneBean;
import model.bean.info.TelefoneInfo;
import model.dao.statement.TelefoneStatementFactory;

public class TelefoneDao {


  private TelefoneStatementFactory factory = new TelefoneStatementFactory();


  public int cadastrar(TelefoneBean bean, int idUser) throws SQLException {

    bean.setInfo(TelefoneInfo.IDUser, idUser);

    factory.insertStatement(bean).execute();

    return idUser;
  }

  public TelefoneDao cadastrar(List<TelefoneBean> beans, int idUser) throws SQLException {

    int[] ids = new int[beans.size()];

    for (TelefoneBean bean : beans)
      ids[beans.indexOf(bean)] = cadastrar(bean, idUser);

    return this;
  }

  public TelefoneDao alterar(TelefoneBean bean) throws SQLException {

    factory.updateStatement(bean).execute();
    return this;
  }

  public TelefoneDao deletar(int idUser) throws SQLException {

    factory.deleteStatement(idUser);
    return this;
  }

  public TelefoneDao deletar(int idUser, int tipo) throws SQLException {

    factory.deleteStatement(idUser, tipo);
    return this;
  }

  public List<TelefoneBean> selecionar(int idUser) throws SQLException {

    List<TelefoneBean> beans = new ArrayList<>();

    ResultSet rs = factory.selectStatement(idUser).executeQuery();

    while (rs.next())
      beans.add(createBean(rs));

    return beans;
  }

  public TelefoneBean selecionar(int idUser, int tipo) throws SQLException {

    ResultSet rs = factory.selectStatement(idUser, tipo).executeQuery();

    TelefoneBean tb = new TelefoneBean();

    while (rs.next())
      tb = createBean(rs);

    return tb;
  }

  private TelefoneBean createBean(ResultSet rs) throws SQLException {

    TelefoneBean tb = new TelefoneBean();

    ResultSetMetaData rsmd = rs.getMetaData();

    for (int i = 1; i <= rsmd.getColumnCount(); i++) {

      String colName = rsmd.getColumnName(i);

      for (TelefoneInfo info : TelefoneInfo.values()) {

        if (info.getCampo().equals(colName)) {

          tb.setInfo(info, rs.getObject(i));
          break;
        }
      }
    }

    return tb;
  }
}
