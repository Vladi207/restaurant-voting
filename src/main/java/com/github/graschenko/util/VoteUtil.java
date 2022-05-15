package com.github.graschenko.util;

import com.github.graschenko.model.Vote;
import com.github.graschenko.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class VoteUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getRestaurant().id(), vote.getDate());
    }

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream().map(VoteUtil::createTo).toList();
    }
}
