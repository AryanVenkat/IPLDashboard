package io.java.lucid.ipldashboard.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;

import io.java.lucid.ipldashboard.data.model.Match;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(MatchInput matchInput) throws Exception {
        Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());

        String firstInningsTeam, secondInningsTeam;
        if ("bat".equalsIgnoreCase(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            // Other team becomes SecondInnings
            secondInningsTeam = (matchInput.getToss_winner().equals(matchInput.getTeam1())) ? matchInput.getTeam2()
                    : matchInput.getTeam1();

        } else {
            secondInningsTeam = matchInput.getToss_winner();
            // Other team becomes SecondInnings
            firstInningsTeam =  (matchInput.getToss_winner().equals(matchInput.getTeam1())) ? matchInput.getTeam2()
            : matchInput.getTeam1();
        }

        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        log.debug("Match data after processing: {}", match);

        return match;
    }

}
