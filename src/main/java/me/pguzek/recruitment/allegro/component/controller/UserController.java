package me.pguzek.recruitment.allegro.component.controller;

import lombok.RequiredArgsConstructor;
import me.pguzek.recruitment.allegro.component.dto.request.LanguagesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.PagedRepositoriesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.StargazerSumCriteria;
import me.pguzek.recruitment.allegro.component.dto.response.ByteLanguageNodeResponseDto;
import me.pguzek.recruitment.allegro.component.dto.response.PagedRepositoryResponseDto;
import me.pguzek.recruitment.allegro.component.dto.response.TotalStargazerResponseDto;
import me.pguzek.recruitment.allegro.component.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
@Validated
public class UserController {

    private final IUserService userService;

    @GetMapping("{user}/repositories")
    @ResponseBody
    public Mono<PagedRepositoryResponseDto> getPagedRepositories(@Valid PagedRepositoriesCriteria criteria) {
        return userService.getPagedRepositories(criteria);
    }

    @GetMapping("{user}/stargazers")
    @ResponseBody
    public Mono<TotalStargazerResponseDto> getStargazerSum(@Valid StargazerSumCriteria criteria) {
        return userService.getStargazerSum(criteria);
    }

    @GetMapping("{user}/languages")
    @ResponseBody
    public Flux<ByteLanguageNodeResponseDto> getLanguages(@Valid LanguagesCriteria criteria) {
        return userService.getLanguages(criteria);
    }
}
