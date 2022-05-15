package com.github.graschenko.web.vote;

import com.github.graschenko.model.Vote;
import com.github.graschenko.to.VoteTo;
import com.github.graschenko.web.MatcherFactory;
import com.github.graschenko.web.restaurant.RestaurantTestData;
import com.github.graschenko.web.user.UserTestData;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {
    public static MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;
    public static final int ADMIN_VOTE_ID = 3;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now().minusDays(1));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.now());
    public static final Vote vote3 = new Vote(ADMIN_VOTE_ID, LocalDate.now().minusDays(1));

    static {
        vote1.setUser(UserTestData.user);
        vote1.setRestaurant(RestaurantTestData.kfc);
        vote2.setUser(UserTestData.user);
        vote2.setRestaurant(RestaurantTestData.king);
        vote3.setUser(UserTestData.user);
        vote3.setRestaurant(RestaurantTestData.king);
    }

    public static VoteTo getNew() {
        return new VoteTo(RestaurantTestData.KFC_ID, LocalDate.now());
    }

    public static VoteTo getUpdated() {
        return new VoteTo(RestaurantTestData.MC_ID, LocalDate.now());
    }

    public static final List<Vote> userVotes = List.of(vote2, vote1);
}
