package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.TelefoneBean;
import model.bean.info.Info;
import model.bean.info.Tabela;
import model.bean.info.TelefoneInfo;

public class TelefoneStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(TelefoneBean bean) {

    return createInsertStatement(Tabela.Telefone, bean.getInfosTel());
  }

  public PreparedStatement updateStatement(TelefoneBean bean) {

    return createUpdateStatement(Tabela.Telefone, bean.getInfosTel(),
        new Info[] {TelefoneInfo.IDUser, TelefoneInfo.Tipo},
        new Object[] {bean.getInfo(TelefoneInfo.IDUser), bean.getInfo(TelefoneInfo.Tipo)});
  }

  public PreparedStatement deleteStatement(int idUser) {

    return createDeleteStatement(Tabela.Telefone, new Info[] {TelefoneInfo.IDUser},
        new Object[] {idUser});
  }

  public PreparedStatement deleteStatement(int idUser, int tipo) {

    return createDeleteStatement(Tabela.Telefone,
        new Info[] {TelefoneInfo.IDUser, TelefoneInfo.Tipo}, new Object[] {idUser, tipo});
  }

  public PreparedStatement selectStatement(int idUser) {

    return createSelectStatement(Tabela.Telefone, new Info[] {TelefoneInfo.IDUser},
        new Object[] {idUser}, TelefoneInfo.values());
  }

  public PreparedStatement selectStatement(int idUser, int tipo) {

    return createSelectStatement(Tabela.Telefone,
        new Info[] {TelefoneInfo.IDUser, TelefoneInfo.Tipo}, new Object[] {idUser, tipo},
        TelefoneInfo.values());
  }
}
