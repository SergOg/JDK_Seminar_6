import java.util.List;
//Создайте интерфейс, который определяет набор методов для работы с
//базой данных (например, сохранение, удаление, получение данных).
//Реализуйте этот интерфейс в конкретном классе.
public interface WorkBD{
    void saveData(Product product);
    void delData(int n);
    List<Product> getData();
}
