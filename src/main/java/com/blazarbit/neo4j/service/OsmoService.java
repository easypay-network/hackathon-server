package com.blazarbit.neo4j.service;

import com.blazarbit.neo4j.model.Token;
import com.blazarbit.neo4j.repository.OsmoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class OsmoService {
    private OsmoRepository osmoRepository;

    public List<Token> findAll(){
        return osmoRepository.findAll();
    }

    public Token findTokenByDenom(String denomName)
    {
        log.info("Search tokens for denom: {}", denomName);

        Token token = osmoRepository.findTokenByDenom(denomName);

        log.info(token);

        return token;
    }
}
