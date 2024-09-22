import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр для работы с БД
            DbHandler dbHandler = DbHandler.getInstance();
            // Сохраняем запись
            dbHandler.saveData(new Product(3,"Круассан", 100, "Выпечка"));
            // Получаем все записи и выводим их на консоль
            List<Product> products = dbHandler.getData();
            for (Product product : products) {
                System.out.println(product.toString());
            }
            // Удаление записи из БД с id = 2
            dbHandler.delData(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
