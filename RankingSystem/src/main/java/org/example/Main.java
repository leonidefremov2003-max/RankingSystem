package org.example;


public class Main {
   public static void main(String[] args) {
       System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));


       // 1. Создаём экземпляр системы ранжирования
       RankingSystem system = new RankingSystem();

       // 2. Добавляем игроков
       system.addPlayer(new Player(1, "Alice", 1500));
       system.addPlayer(new Player(2, "Bob", 1400));
       system.addPlayer(new Player(3, "Charlie", 1550));
       system.addPlayer(new Player(4, "Diana", 1500)); // Одинаковый рейтинг с Alice для демонстрации

       // 3. Выводим топ-3
       system.printTopPlayers(3);

       // 4. Обновляем рейтинг Bob
       try {
           system.updatePlayerRating(2, 1600); // Bob получает 1600
       } catch (PlayerNotFoundException e) {
           System.out.println("Ошибка: " + e.getMessage());
       }

       // 5. Снова выводим топ-3
       system.printTopPlayers(3);

       // 6. Получаем ранг Alice
       try {
           int rank = system.getPlayerRank(1); // ID=1 это Alice
           System.out.println("\nТекущий ранг Alice: " + rank);
       } catch (PlayerNotFoundException e) {
           System.out.println("Ошибка: " + e.getMessage());
       }

       // 7. Пытаемся получить ранг несуществующего игрока (ID=99)
       System.out.println("\nПопытка получить ранг несуществующего игрока:");
       try {
           int rank = system.getPlayerRank(99);
           System.out.println("Ранг: " + rank);
       } catch (PlayerNotFoundException e) {
           System.out.println("Ошибка: " + e.getMessage());
       }

       // 8. Попытка запросить топ-10, когда игроков всего 4
       System.out.println("\nПопытка запросить топ-10:");
       system.printTopPlayers(10);

    }
}

// РАБОТА ПРОГРАММЫ:
// > Task :org.example.Main.main()
//Добавлен игрок: Alice (ID: 1, Рейтинг: 1500)
//Добавлен игрок: Bob (ID: 2, Рейтинг: 1400)
//Добавлен игрок: Charlie (ID: 3, Рейтинг: 1550)
//Добавлен игрок: Diana (ID: 4, Рейтинг: 1500)
//
//Топ 3 игроков:
//1. Charlie - 1550
//2. Alice - 1500
//3. Diana - 1500
//Обновлен рейтинг игрока Bob: 1400 -> 1600
//
//Топ 3 игроков:
//1. Bob - 1600
//2. Charlie - 1550
//3. Alice - 1500
//
//Текущий ранг Alice: 3
//
//Попытка получить ранг несуществующего игрока:
//Ошибка: Игрок с ID 99 не найден в системе
//
//Попытка запросить топ-10:
//
//Топ 10 игроков:
//Невозможно получить топ-10 игроков. Всего игроков в системе: 4
//
//  BUILD SUCCESSFUL in 1s
