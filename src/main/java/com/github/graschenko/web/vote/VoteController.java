package com.github.graschenko.web.vote;

import com.github.graschenko.model.Vote;
import com.github.graschenko.service.VoteService;
import com.github.graschenko.to.VoteTo;
import com.github.graschenko.util.VoteUtil;
import com.github.graschenko.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class VoteController {

    static final String REST_URL = "/api/votes";

    private final VoteService voteService;

    @GetMapping("/by-date")
    public VoteTo getByDate(@AuthenticationPrincipal AuthUser authUser,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate date) {
        log.info("vote for user {} by date {}", authUser.id(), date);
        return voteService.getByDate(authUser.id(), date);
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get All Votes for user {}", authUser.id());
        return voteService.getAll(authUser.id());
    }

    @PostMapping
    public ResponseEntity<VoteTo> createOrUpdate(@AuthenticationPrincipal AuthUser authUser,
                                                 @RequestParam int restaurantId) {
        log.info("vote for restaurant: {}", restaurantId);
        Vote created = voteService.createOrUpdate(authUser.id(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();
        return ResponseEntity.created(uriOfNewResource).body(VoteUtil.createTo(created));
    }
}
