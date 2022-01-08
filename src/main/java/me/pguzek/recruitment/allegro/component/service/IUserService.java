package me.pguzek.recruitment.allegro.component.service;

import me.pguzek.recruitment.allegro.component.dto.request.LanguagesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.PagedRepositoriesCriteria;
import me.pguzek.recruitment.allegro.component.dto.request.StargazerSumCriteria;
import me.pguzek.recruitment.allegro.component.dto.response.ByteLanguageNodeResponseDto;
import me.pguzek.recruitment.allegro.component.dto.response.PagedRepositoryResponseDto;
import me.pguzek.recruitment.allegro.component.dto.response.TotalStargazerResponseDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
public interface IUserService {

    Mono<PagedRepositoryResponseDto> getPagedRepositories(@NonNull @Valid PagedRepositoriesCriteria user);

    Mono<TotalStargazerResponseDto> getStargazerSum(@NonNull @Valid StargazerSumCriteria user);

    Flux<ByteLanguageNodeResponseDto> getLanguages(@NonNull @Valid LanguagesCriteria user);
}
