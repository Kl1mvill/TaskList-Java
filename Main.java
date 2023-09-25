import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {
    // Список для хранения дел
    static List<String> todoList = new ArrayList<>();
    // Создаем экземпляр класса Scanner
    static Scanner in = new Scanner(System.in);
    
    // Функция для очистки консоли.
    public static void clear_console() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    // Функция читает файл "todolist.txt" и возвращает список дел.
    // Если файла нет возвращаем пустой список. 
    public static List<String> load_todo_list() {
        try (Scanner scanner = new Scanner(new File("todolist.txt"))) {
            while (scanner.hasNextLine()) {
                todoList.add(scanner.nextLine());
            }
            return todoList;
        } catch (FileNotFoundException e) {
            return todoList;
        }
    }
    
    // Записываем список в файл с переводом строк. 
    public static void save_list_file(List<String> todo_list) throws IOException {
        FileWriter writer = new FileWriter("todolist.txt");
        
        for(String todoItem: todo_list) {
            writer.write(todoItem + System.lineSeparator());
        }
        writer.close();
    }

    // Функция красиво выводит список дел. Если список пустой печатаем "Nothing to do".
    public static void show_todo_list(List<String> todo_list) {
        if (todo_list.isEmpty()) {
            System.out.println("Nothing to do");
        } else {
            ListIterator<String> it = todo_list.listIterator();

            while (it.hasNext()) {
                System.out.println(it.nextIndex() + ". " + it.next());
            }
        }
    }

    // Добавляет задачу, которую вводит пользователь, в список дел. 
    public static void add_todo_item(List<String> todo_list) {
        System.out.print("Task text: ");
        todo_list.add(in.nextLine());
    }

    // Удаление элемента из списка. Сначала выводим список. Спрашиваем какой элемент удалить. 
    // Удаляем по индексу. Если такого индекса нет выводим "You named a non-existent case".
    public static void removing_from_list(List<String> todo_list) {
        show_todo_list(todo_list);
        
        System.out.print("\nWhat would you like to delete? ");
        int item_number = in.nextInt();

        if (item_number <= todo_list.size()) {
            todo_list.remove(item_number);
        } else {
            System.out.print("You named a non-existent case");
        }
    }

    // функция изменяет элемент списка дел. Спрашиваем какой элемент изменить.
    // Пользователь вводит изменненую задачу. Изменяем элемент по индексу. Если такого индекса нет выводим "You named a non-existent case".
    public static void changing_element(List<String> todo_list) {
        show_todo_list(todo_list);

        System.out.print("\nWhich element do you want to change? ");
        int changeable_element = in.nextInt();

        if (changeable_element <= todo_list.size()) {
            System.out.print("Enter the change: ");

            todo_list.set(changeable_element, in.next());
        } else {
            System.out.print("You named a non-existent case");
        }
    }

    // Вывод меню для управления кодом. Выводим список функций. Пользователь выбирает. Очищаем консоль.
    // С помощью оператора switch запускаем нужнуюю пользователю функцию.
    // Если он выбрал не существующую функцию то выводим "Command not found!".
    public static void show_menu(List<String> todo_list) throws IOException, InterruptedException {
        System.out.print("-----------------------------------");
        System.out.println("\n1. Show\n2. Add\n3. Delete\n4. Edit\n5. Exit");

        System.out.print("Choose option index: ");
        String command = in.next();
        clear_console();

        switch (command) {
            case "1" -> show_todo_list(todo_list);
            case "2" -> add_todo_item(todo_list);
            case "3" -> removing_from_list(todo_list);
            case "4" -> changing_element(todo_list);
            case "5" -> {
                save_list_file(todo_list);
                System.exit(0);
            }
            default -> System.out.println("Command not found!");
        }
    }

    // Запускаем код. Достаем список из файла(подробнее смотрите функцию load_todo_list)
    // Запускаем бессконечный цикл в котором запускаем функцию show_menu.
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> todo_list = load_todo_list();

        while (true) {
            show_menu(todo_list);
        }
    }
}
