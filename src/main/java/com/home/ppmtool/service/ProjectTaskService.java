package com.home.ppmtool.service;

import com.home.ppmtool.domain.Backlog;
import com.home.ppmtool.domain.ProjectTask;
import com.home.ppmtool.repository.BacklogRepository;
import com.home.ppmtool.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask createProjectTask(String projectIdentifier, ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        if(projectTask.getStatus()==""||projectTask.getStatus()==null){
            projectTask.setStatus("TODO");
        }
        if( projectTask.getPriority() ==null ){
            projectTask.setPriority(3);
        }
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);
        projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBackLogById(String id){
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
