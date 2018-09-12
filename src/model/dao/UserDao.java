package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import model.bean.UserBean;
import model.bean.info.UserInfo;
import model.dao.statement.UserStatementFactory;

public abstract class UserDao {

  
  private UserStatementFactory factory = new UserStatementFactory();
  

  protected int cadastrar(UserBean bean) throws SQLException {

    PreparedStatement ps = factory.insertStatement(bean);

    ps.executeUpdate();

    ResultSet rs = ps.getGeneratedKeys();

    if (rs.next())
      return rs.getInt(1);

    return -1;
  }

  protected UserDao alterar(UserBean bean) throws SQLException {

    factory.updateStatement(bean).execute();
    
    System.out.println("Executada");

    return this;
  }

  protected UserDao deletar(int id) throws SQLException {

    factory.deleteStatement(id).execute();

    return this;
  }

  protected UserBean selecionar(int id) throws SQLException {

    ResultSet rs = factory.selectStatement(id).executeQuery();

    while (rs.next()) {

      UserBean bean = new UserBean();

      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {

        String colName = rsmd.getColumnName(i);

        for (UserInfo info : UserInfo.values()) {

          if (info.getCampo().equals(colName))
            bean.setInfo(info, rs.getObject(i));
          break;
        }
      }

      return bean;
    }

    return null;
  }

  /*public int login(String email, String senha) throws SQLException {

    StatementBuilder sf = new StatementBuilder();

    ResultSet rs =
        sf.setTabela(Tabela.User).setTipo(sf.SELECT).setInfos(UserInfo.ID, UserInfo.Senha)
            .addCondition(UserInfo.Email).addConditionValue(email).build().executeQuery();

    while (rs.next()) {

      Blob senhaServer = rs.getBlob(2);
      int length = (int) senhaServer.length();

      if (PswdStorage.compararHashClient(senha, senhaServer.getBytes(1, length)))
        return rs.getInt(1);
    }

    return -1;
  }*/
}