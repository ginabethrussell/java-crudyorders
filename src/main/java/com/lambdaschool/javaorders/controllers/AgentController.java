package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController
{
    @Autowired
    private AgentServices agentServices;

    // http://localhost:2019/agents/agent{id}
    // Returns the agent and their customers with the given agent id
    @GetMapping(value = "/agent/{id}", produces = "application/json")
    public ResponseEntity<?> getAgentById(@PathVariable long id)
    {
        Agent agent = agentServices.getAgentById(id);
        return new ResponseEntity<>(agent,
            HttpStatus.OK);
    }
}
