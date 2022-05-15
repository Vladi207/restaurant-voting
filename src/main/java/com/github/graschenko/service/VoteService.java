package com.github.graschenko.service;

import com.github.graschenko.model.Restaurant;
import com.github.graschenko.model.Vote;
import com.github.graschenko.repository.RestaurantRepository;
import com.github.graschenko.repository.UserRepository;
import com.github.graschenko.repository.VoteRepository;
import com.github.graschenko.to.VoteTo;
import com.github.graschenko.util.DateTimeUtil;
import com.github.graschenko.util.RepositoryUtil;
import com.github.graschenko.util.VoteUtil;
import com.github.graschenko.util.exception.IllegalRequestDataException;
import com.github.graschenko.web.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.graschenko.web.ExceptionInfoHandler.EXCEPTION_UPDATE_VOTE;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteTo getByDate(int userId, @Nullable LocalDate date) {
        LocalDate voteDate = date == null ? LocalDate.now() : date;
        return voteRepository.getByDate(userId, voteDate).map(VoteUtil::createTo).orElseThrow(
                () -> new IllegalRequestDataException("Vote for date =" + voteDate + " not found")
        );
    }

    public List<VoteTo> getAll(int userId) {
        return VoteUtil.getTos(voteRepository.getAll(userId));
    }

    @Transactional
    public Vote createOrUpdate(int userId, int restaurantId) {
        Restaurant restaurant = RepositoryUtil.findById(restaurantRepository, restaurantId);

        Vote vote = voteRepository.getByDate(userId, LocalDate.now())
                .orElse(new Vote(userRepository.getById(userId), restaurant, LocalDate.now()));

        if (vote.isNew()) {
            log.info("new vote from user {} for {}", userId, restaurantId);
            return voteRepository.save(vote);
        }

        if (LocalTime.now().isBefore(DateTimeUtil.getTimeLimit())) {
            if (vote.getRestaurant().id() != restaurantId) {
                vote.setRestaurant(restaurant);
                log.info("vote from user {} for restaurant {} was changed", userId, restaurantId);
                return vote;
            }
            log.info("vote from user {} for restaurant {} not changed", userId, restaurantId);
            return vote;
        } else {
            throw new IllegalRequestDataException(EXCEPTION_UPDATE_VOTE + DateTimeUtil.toString(DateTimeUtil.getTimeLimit()));
        }

    }
}
