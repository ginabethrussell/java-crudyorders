package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Agent;
import com.lambdaschool.javaorders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value="agentServices")
public class AgentServiceImpl implements AgentServices
{
    @Autowired
    private AgentRepository agentRepository;

    @Transactional
    @Override
    public Agent save(Agent agent)
    {
        return agentRepository.save(agent);
    }

    @Override
    public Agent getAgentById(long id)
    {
        Agent agent = agentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " Not Found!"));
        return agent;
    }

    @Override
    public void deleteUnassignedAgent(long id)
    {
       Agent unassignedAgent = agentRepository.findById(id)
           .orElseThrow(()-> new EntityNotFoundException("Agent " + id + " Not Found"));
       if (unassignedAgent.getCustomers().size() > 0 )
       {
           throw new EntityNotFoundException("Found a customer for agent " + id);
       }else
       {
           agentRepository.deleteById(id);
       }
    }
}
