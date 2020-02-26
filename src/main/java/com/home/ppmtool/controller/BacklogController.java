package com.home.ppmtool.controller;

import com.home.ppmtool.domain.ProjectTask;
import com.home.ppmtool.service.MapValidationErrorService;
import com.home.ppmtool.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPtToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,@PathVariable String backlog_id){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap !=null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.createProjectTask(backlog_id,projectTask);
        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
        return projectTaskService.findBackLogById(backlog_id);
    }
}
