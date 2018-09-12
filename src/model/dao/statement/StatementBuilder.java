package model.dao.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import model.conexao.ConnectionFactory;
import model.bean.info.Info;
import model.bean.info.Tabela;

public class StatementBuilder {


  public final int INSERT = 1, SELECT = 2, UPDATE = 3, DELETE = 4;

  private String tabela;
  private int tipo;
  private HashMap<? extends Info, Object> mapa = new HashMap<>();
  private ArrayList<Info> infos = new ArrayList<>();
  private ArrayList<Info> conditions = new ArrayList<>();
  private ArrayList<Object> conditionsValues = new ArrayList<>();
  private PreparedStatement ps;
  private int fimValues;


  public PreparedStatement build() {

    setInfos(infos.toArray(new Info[] {})).instantiateStatement().prepareStatement();

    System.out.println("Statement pronto!");
    System.out.println(ps);
    
    return ps;
  }


  private StatementBuilder instantiateStatement() {

    System.out.println("Instanciando Statement...");
    
    try {

      ps = ConnectionFactory.getConnection().prepareStatement(estruturarComando(),
          Statement.RETURN_GENERATED_KEYS);
      
      System.out.println("Statement instanciado!");
    } catch (SQLException e) {

      System.out.println("Erro ao instanciar Statement \n" + estruturarComando());
      e.printStackTrace();
    }
    
    return this;
  }

  private StatementBuilder prepareStatement() {

    System.out.println("Preparando Statement...");
    
    switch (tipo) {

      case INSERT: {

        setInfosValues();
        break;
      }

      case SELECT: {

        setConditionsValues();
        break;
      }

      case UPDATE: {

        setInfosValues();
        setConditionsValues();
        break;
      }

      case DELETE: {

        setConditionsValues();
        break;
      }
    }
    
    System.out.println("Statement preparado!");

    return this;
  }

  private String estruturarComando() {

    String comando = "";

    switch (tipo) {

      case INSERT: {

        comando = "insert into " + tabela + "(" + getCampos() + ") values (" + getValues() + ")";
        break;
      }

      case SELECT: {

        comando = "select " + getCampos() + " from " + tabela + " where " + getConditions();
        break;
      }

      case UPDATE: {

        comando = "update " + tabela + " set " + getCamposAndValues() + " where " + getConditions();
        break;
      }

      case DELETE: {

        comando = "delete from " + tabela + " where " + getConditions();
        break;
      }
    }

    System.out.println("Comando gerado: " + comando);

    return comando;
  }


  private String getCampos() {

    String campos = "";

    int i = 0;

    switch (tipo) {

      case INSERT: {

        for (Entry<? extends Info, Object> entrada : mapa.entrySet()) {

          campos += ((Info) entrada.getKey()).getCampo();
          campos += i < mapa.entrySet().size() - 1 ? ", " : "";

          i++;
        }
        break;
      }

      case SELECT: {

        for (Info info : infos) {

          campos += info.getCampo();
          campos += i < infos.size() - 1 ? ", " : "";

          i++;
        }
        break;
      }
    }

    return campos;
  }

  private String getCamposAndValues() {

    String camposAndValues = "";

    int i = 0;

    for (Entry<? extends Info, Object> entrada : mapa.entrySet()) {

      camposAndValues += ((Info) entrada.getKey()).getCampo() + " = ";
      camposAndValues += "?";

      camposAndValues += i < mapa.entrySet().size() - 1 ? ", " : "";

      i++;
    }

    return camposAndValues;
  }

  private String getValues() {

    String values = "";

    switch (tipo) {

      case INSERT: {

        int length = mapa.entrySet().size();

        for (int i = 0; i < length; i++) {

          values += "?";
          values += i < length - 1 ? ", " : "";
        }

        break;
      }
    }

    return values;
  }

  private String getConditions() {

    String conditions = "";

    for (int i = 0; i < this.conditions.size(); i++) {

      conditions += this.conditions.get(i).getCampo() + " = ?"
          + (i + 1 < this.conditions.size() ? " and " : "");
    }

    return conditions;
  }

  private void setConditionsValues() {

    for (int i = 0; i < this.conditionsValues.size(); i++) {

      System.out.println("Definindo condição " + this.conditions.get(i).getCampo() + " como "
          + this.conditionsValues.get(i) + "...");

      try {
        ps.setObject(i + 1 + fimValues, this.conditionsValues.get(i));
      } catch (SQLException e) {

        System.out.println("Não foi possível definir a condição em: " + this.conditions.get(i).getCampo()
            + " = " + this.conditionsValues.get(i));
        e.printStackTrace();
      }
    }

    System.out.println("Condições definidas com sucesso!");
  }

  private void setInfosValues() {

    int i = 1;

    for (Entry<? extends Info, Object> entrada : mapa.entrySet()) {

      System.out
          .println("Definindo valor " + entrada.getKey().getCampo() + " como " + entrada.getValue() + "...");

      try {

        ps.setObject(i, entrada.getValue());
      } catch (SQLException e) {

        System.out.println("Não foi possível definir o valor em: " + entrada.getKey().getCampo() + " = "
            + entrada.getValue());
        e.printStackTrace();
      }

      fimValues = i;
      i++;
    }

    System.out.println("Valores definidos com sucesso!");
  }


  public StatementBuilder withTabela(Tabela tabela) {

    this.tabela = tabela.getNome();

    return this;
  }

  public StatementBuilder withTipo(int tipo) {

    this.tipo = tipo;

    return this;
  }

  public StatementBuilder addCondition(Info condition) {

    this.conditions.add(condition);

    return this;
  }

  public StatementBuilder addConditionValue(Object value) {

    this.conditionsValues.add(value);

    return this;
  }

  public StatementBuilder withMap(HashMap<? extends Info, Object> mapa) {

    this.mapa = mapa;

    return this;
  }

  public StatementBuilder withInfos(Info... infos) {

    for (Info info : infos)
      this.infos.add(info);

    return this;
  }

  private StatementBuilder setInfos(Info... infos) {

    this.infos.clear();

    for (Info info : infos) {

      if (!this.conditions.contains(info))
        this.infos.add(info);
    }

    return this;
  }
}
