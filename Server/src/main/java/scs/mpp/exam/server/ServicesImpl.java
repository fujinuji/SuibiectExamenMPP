package scs.mpp.exam.server;

import scs.mpp.exam.entites.Player;
import scs.mpp.exam.entites.Round;
import scs.mpp.exam.repository.GameRepository;
import scs.mpp.exam.repository.PlayerRepository;
import scs.mpp.exam.repository.RoundRepository;
import scs.mpp.exam.services.Observer;
import scs.mpp.exam.services.Services;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class ServicesImpl implements Services {
    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private RoundRepository roundRepository;

    private Map<String, Observer> loggedUsers = new HashMap<>();
    private Integer round;
    private String currentLetter;
    private String gameId;

    private List<Round> answers = new ArrayList<>();

    private List<String> country = Arrays.asList("albania", "banana", "calonia", "da", "e", "f", "g", "h", "i", "j");
    private List<String> city = Arrays.asList("albania", "banana", "calonia", "da", "e", "f", "g", "h", "i", "j");
    private List<String> sea = Arrays.asList("albania", "banana", "calonia", "da", "e", "f", "g", "h", "i", "j");

    public Map<String, Integer> globalPoints = new HashMap<>();


    public ServicesImpl(PlayerRepository playerRepository, GameRepository gameRepository, RoundRepository roundRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
    }

    @Override
    public Player login(String userName, String password, Observer observer) throws Exception {
        if (loggedUsers.containsKey(userName)) {
            throw new Exception("User already logged in");
        }

        if (loggedUsers.size() == 3) {
            throw new Exception("3 users already logged in");
        }

        Player player = playerRepository.checkPlayer(userName, password);
        loggedUsers.put(userName, observer);
        globalPoints.put(userName, 0);

        if (loggedUsers.size() == 3) {
            round = 1;
            Random rnd = new Random();
            char c = (char) (rnd.nextInt(26) + 'a');
            currentLetter = String.valueOf(c);
            gameId = UUID.randomUUID().toString();

            loggedUsers.forEach((k,v) -> {
                try {
                    v.startGame(currentLetter);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }

        return player;
    }

    @Override
    public void sendQuizResponse(Round round) {
        round.setRound(this.round);
        round.setGameId(gameId);

        answers.add(round);

        if (answers.size() == 3) {
            answers.forEach(answer -> {
                Integer points = 0;

                if (answer.getAnswer1().equals("") || !answer.getAnswer1().startsWith(currentLetter) || !country.contains(answer.getAnswer1())) {
                    points += 0;
                }

                if (answer.getAnswer1().startsWith(currentLetter) && country.contains(answer.getAnswer1()) &&
                        answers.stream().map(x -> x.getAnswer1().equals(answer.getAnswer1())).count() > 1) {
                    points += 3;
                }

                if (answer.getAnswer1().startsWith(currentLetter) && country.contains(answer.getAnswer1()) &&
                        answers.stream().map(x -> x.getAnswer1().equals(answer.getAnswer1())).count() == 1) {
                    points += 10;
                }

                if (answer.getAnswer2().equals("") || !answer.getAnswer2().startsWith(currentLetter) || !city.contains(answer.getAnswer2())) {
                    points += 0;
                }

                if (answer.getAnswer2().startsWith(currentLetter) && city.contains(answer.getAnswer2()) &&
                        answers.stream().map(x -> x.getAnswer2().equals(answer.getAnswer2())).count() > 1) {
                    points += 3;
                }

                if (answer.getAnswer2().startsWith(currentLetter) && city.contains(answer.getAnswer2()) &&
                        answers.stream().map(x -> x.getAnswer2().equals(answer.getAnswer2())).count() == 1) {
                    points += 10;
                }

                if (answer.getAnswer3().equals("") || !answer.getAnswer3().startsWith(currentLetter) || !sea.contains(answer.getAnswer3())) {
                    points += 0;
                }

                if (answer.getAnswer3().startsWith(currentLetter) && sea.contains(answer.getAnswer3()) &&
                        answers.stream().map(x -> x.getAnswer3().equals(answer.getAnswer3())).count() > 1) {
                    points += 3;
                }

                if (answer.getAnswer3().startsWith(currentLetter) && sea.contains(answer.getAnswer3()) &&
                        answers.stream().map(x -> x.getAnswer3().equals(answer.getAnswer3())).count() == 1) {
                    points += 10;
                }

                globalPoints.put(answer.getPlayerName(), globalPoints.get(answer.getPlayerName()) + points);
                answer.setPoints(points);
                roundRepository.saveRound(answer);
            });

            answers.clear();
            if (this.round == 3) {
                List<Map.Entry<String, Integer>> sortedMap = globalPoints.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toList());
                List<String> result = new ArrayList<>();

                for (Map.Entry<String, Integer> data : sortedMap) {
                    result.add(data.getKey());
                    result.add(data.getValue().toString());
                }

                loggedUsers.forEach((k, v) -> {
                    try {
                        v.top(result);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                this.round++;
                Random rnd = new Random();
                char c = (char) (rnd.nextInt(26) + 'a');
                currentLetter = String.valueOf(c);

                loggedUsers.forEach((k, v) -> {
                    try {
                        v.nextRound(globalPoints, currentLetter);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
