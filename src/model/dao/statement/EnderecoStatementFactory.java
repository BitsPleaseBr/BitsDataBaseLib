package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.EnderecoBean;
import model.bean.info.EnderecoInfo;
import model.bean.info.Info;
import model.bean.info.Tabela;

public class EnderecoStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(EnderecoBean bean) {

    return createInsertStatement(Tabela.Endereco, bean.getInfosEnd());
  }

  public PreparedStatement updateStatement(EnderecoBean bean) {

    return createUpdateStatement(Tabela.Endereco, bean.getInfosEnd(),
        new Info[] {EnderecoInfo.IDUser, EnderecoInfo.Tipo},
        new Object[] {bean.getInfo(EnderecoInfo.IDUser), bean.getInfo(EnderecoInfo.Tipo)});
  }

  public PreparedStatement deleteStatement(int idUser) {

    return createDeleteStatement(Tabela.Endereco, new Info[] {EnderecoInfo.IDUser},
        new Object[] {idUser});
  }

  public PreparedStatement deleteStatement(int idUser, int tipo) {

    return createDeleteStatement(Tabela.Endereco,
        new Info[] {EnderecoInfo.IDUser, EnderecoInfo.Tipo}, new Object[] {idUser, tipo});
  }

  public PreparedStatement selectStatement(int idUser) {

    return createSelectStatement(Tabela.Endereco, new Info[] {EnderecoInfo.IDUser},
        new Object[] {idUser}, EnderecoInfo.values());
  }

  public PreparedStatement selectStatement(int idUser, int tipo) {

    return createSelectStatement(Tabela.Endereco,
        new Info[] {EnderecoInfo.IDUser, EnderecoInfo.Tipo}, new Object[] {idUser, tipo},
        EnderecoInfo.values());
  }
}
