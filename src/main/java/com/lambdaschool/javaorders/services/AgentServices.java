package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Agent;

public interface AgentServices
{
    Agent save(Agent agent);

    Agent getAgentById(long id);

    void deleteUnassignedAgent(long id);
}
