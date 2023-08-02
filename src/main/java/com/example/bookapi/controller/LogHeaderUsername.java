package com.example.bookapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogHeaderUsername {

    private final Logger LOG = LoggerFactory.getLogger(LogHeaderUsername.class);

    public void printUsername(String firstname, String lastname) {
        LOG.info(firstname + " " + lastname + " " + Thread.currentThread().getId());
    }
}
