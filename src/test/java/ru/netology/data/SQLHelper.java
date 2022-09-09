package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;


public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_code ORDER BY created DESC LIMIT 1";
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app",
                "app", "pass")) {
            var result = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(result);
        }
        //или так, когда записываем все строки таблицы в лист и оттуда берем код,
        // или можно записать только последнюю строку указав лимит 1, тогда в result будет BeanHendler
//        public static DataHelper.VerificationCode getVerificationCode() {
//        var codeSQL = "SELECT * FROM auth_code ORDER BY created DESC";
//        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")) {
//                 var result = runner.query(conn, codeSQL, new BeanListHandler<>(DataHelper.AuthCode.class));
//            return new DataHelper.VerificationCode(result.get(0).getCode());
//        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app",
                "app", "pass")) {
            runner.execute(conn, "DELETE FROM auth_codes");
            runner.execute(conn, "DELETE FROM card_transactions");
            runner.execute(conn, "DELETE FROM cards");
            runner.execute(conn, "DELETE FROM users");
        }
    }
}
