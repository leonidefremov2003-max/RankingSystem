package org.example;

import java.util.*;

public class RankingSystem {

    private TreeMap<Integer, Set<Player>> playerRankings;

    public RankingSystem() {
        this.playerRankings = new TreeMap<>(Collections.reverseOrder());
    }

    public void addPlayer(Player player) {
        int rating = player.getRating();
        playerRankings.computeIfAbsent(rating
                , k -> new TreeSet<>(Comparator.comparing(Player::getName)))
                .add(player);
        System.out.println("Добавлен игрок: " + player);
    }

    public void updatePlayerRating(int playerId, int newRating) throws PlayerNotFoundException {
        Player foundPlayer = null;
        Integer oldRating = null;
        for (Map.Entry<Integer, Set<Player>> entry : playerRankings.entrySet()) {
            for (Player p : entry.getValue()) {
                if (p.getId() == playerId) {
                    foundPlayer = p;
                    oldRating = entry.getKey();
                    break;
                }
            }
            if (foundPlayer != null) break;
        }
        if (foundPlayer == null) {
            throw new PlayerNotFoundException("Игрок с ID : " + playerId + ". Не найден в системе");
        }

        Set<Player> oldSet = playerRankings.get(oldRating);
        oldSet.remove(foundPlayer);
        if (oldSet.isEmpty()) {
            playerRankings.remove(oldRating);
        }

        foundPlayer.setRating(newRating);
        playerRankings.computeIfAbsent(newRating, k -> new TreeSet<>(Comparator.comparing(Player::getName))).add(foundPlayer);

        System.out.println("Обновлен рейтинг игрока " + foundPlayer.getName() +
                ": " + oldRating + " -> " + newRating);
    }


    public List<Player> getTopPlayers(int n) {
        int totalPlayers = 0;
        for (Set<Player> set : playerRankings.values()) {
            totalPlayers += set.size();
        }


        if (n <= 0 || n > totalPlayers) {
            throw new IllegalArgumentException(
                    "Невозможно получить топ-" + n + " игроков. Всего игроков в системе: " + totalPlayers);
        }

        List<Player> topPlayers = new ArrayList<>();
        int count = 0;


        for (Map.Entry<Integer, Set<Player>> entry : playerRankings.entrySet()) {

            for (Player p : entry.getValue()) {
                topPlayers.add(p);
                count++;
                if (count == n) {
                    return topPlayers;
                }
            }
        }

        return topPlayers;
    }


    public int getPlayerRank(int playerId) throws PlayerNotFoundException {
        int rank = 0;


        for (Map.Entry<Integer, Set<Player>> entry : playerRankings.entrySet()) {

            for (Player p : entry.getValue()) {
                rank++;
                if (p.getId() == playerId) {
                    return rank;
                }
            }
        }

        throw new PlayerNotFoundException("Игрок с ID " + playerId + " не найден в системе");
    }


    public void printTopPlayers(int n) {
        System.out.println("\nТоп " + n + " игроков:");
        try {
            List<Player> top = getTopPlayers(n);
            int position = 1;
            for (Player p : top) {
                System.out.println(position + ". " + p.getName() + " - " + p.getRating());
                position++;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
