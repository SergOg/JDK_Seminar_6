import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbHandler implements WorkBD {

    private static final String CON_STR = "jdbc:sqlite:C:/myData.db";   // Константа c адресом подключения
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null) instance = new DbHandler();
        return instance;
    }

    private Connection connection;   // Объект, в котором будет храниться соединение с БД

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());   // Регистриуем драйвер, с которым будем работать Sqlite
        this.connection = DriverManager.getConnection(CON_STR);   // Выполняем подключение к базе данных
    }

    public List<Product> getData() {
        try (Statement statement = this.connection.createStatement()) {   // Statement для выполнения sql-запроса
            List<Product> products = new ArrayList<Product>();   // В данный список будем загружать наши продукты, полученные из БД
            ResultSet resultSet = statement.executeQuery("SELECT id, good, price, category_name FROM products"); // В resultSet результат
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("good"),
                        resultSet.getDouble("price"),
                        resultSet.getString("category_name")));
            }
            return products;   // Возращаем список
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();   // Если произошла ошибка - возвращаем пустую коллекцию
        }
    }

    public void saveData(Product product) {
        try (PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Products(`good`, `price`, `category_name`) " + "VALUES(?, ?, ?)")) {
            statement.setObject(1, product.good);
            statement.setObject(2, product.price);
            statement.setObject(3, product.category_name);
            statement.execute();   // Выполняем запрос
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delData(int n) {
        try (PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Products WHERE id = ?")) {
            statement.setObject(0, 1);
            statement.execute();   // Выполняем запрос
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
