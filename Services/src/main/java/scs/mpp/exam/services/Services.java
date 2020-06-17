package scs.mpp.exam.services;

import scs.mpp.exam.entites.Player;
import scs.mpp.exam.entites.Round;

public interface Services {
    Player login(String userName, String password, Observer observer) throws Exception;
    void sendQuizResponse(Round round);
}
