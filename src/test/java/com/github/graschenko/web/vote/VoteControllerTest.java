package com.github.graschenko.web.vote;

import ch.qos.logback.core.util.TimeUtil;
import com.github.graschenko.repository.VoteRepository;
import com.github.graschenko.to.VoteTo;
import com.github.graschenko.util.DateTimeUtil;
import com.github.graschenko.util.VoteUtil;
import com.github.graschenko.web.AbstractControllerTest;
import com.github.graschenko.web.ExceptionInfoHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.graschenko.util.DateTimeUtil.getTimeLimit;
import static com.github.graschenko.util.DateTimeUtil.setTimeLimit;
import static com.github.graschenko.web.restaurant.RestaurantTestData.*;
import static com.github.graschenko.web.user.UserTestData.*;
import static com.github.graschenko.web.vote.VoteTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-date")
                .param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.createTo(vote1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VoteUtil.getTos(userVotes)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        VoteTo newVote = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(KFC_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo created = voteRepository.getByDate(ADMIN_ID, LocalDate.now()).map(VoteUtil::createTo).orElse(null);
        VOTE_TO_MATCHER.assertMatch(created, newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfter() throws Exception {
        LocalTime time = LocalTime.now();
        setTimeLimit(time);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(KFC_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(ExceptionInfoHandler.EXCEPTION_UPDATE_VOTE + DateTimeUtil.toString(getTimeLimit()))));
    }

}