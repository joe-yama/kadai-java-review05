import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {
  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String selectSql = "SELECT * from person where id = ?";
      System.out.print("idを入力してください > ");
      int targetId = keyInNum();
      try (Connection con = DriverManager.getConnection(
          "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true", "root", "");
          PreparedStatement pstmt = con.prepareStatement(selectSql);) {
        pstmt.setInt(1, targetId);

        /*
         * ResultSetの暗黙的closeについて：ResultSetオブジェクトは、このオブジェクトを生成したStatementオブジェクトが閉じられるとき、再実行されるとき、
         * あるいは一連の複数の結果から次の結果を取り出すために使われるときに、自動的に閉じられます。 引用元：
         * https://docs.oracle.com/javase/jp/8/docs/api/java/sql/ResultSet.html
         */
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          String name = rs.getString("name");
          int age = rs.getInt("age");
          System.out.println(name);
          System.out.println(age);
        } else {
          System.err.println("指定されたidに該当するレコードが見つかりませんでした。");
        }
      }
    } catch (ClassNotFoundException e) {
      System.err.println("JDBCドライバのロードに失敗しました。");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("データベースに異常が発生しました。");
      e.printStackTrace();
    }
  }

  private static String keyIn() {
    String line = null;
    try {
      BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
      line = key.readLine();
    } catch (IOException e) {

    }
    return line;
  }

  private static int keyInNum() {
    int result = 0;
    try {
      result = Integer.parseInt(keyIn());
    } catch (NumberFormatException e) {
    }
    return result;
  }
}
