package scs.mpp.exam.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mian {
    public static void main(String[] args) throws Exception {
        //PlayerRepository playerRepository = new PlayerRepository();
        //playerRepository.checkPlayer("aa", "aa");

        List<OJJ> anwsers = new ArrayList<>();
        anwsers.add(new OJJ("aaaaa"));
        anwsers.add(new OJJ("333"));
        anwsers.add(new OJJ("aa33aaa"));
        anwsers.add(new OJJ("aa22aaa"));
        anwsers.add(new OJJ("aaaaas"));

        OJJ answer = new OJJ("aaaaa");

        List<OJJ> lista = anwsers.stream().filter(x -> x.getA().equals(answer.getA())).collect(Collectors.toList());
        System.out.println(lista);
    }
}
