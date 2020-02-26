package com.home.ppmtool.controller;

import com.home.ppmtool.domain.Project;
import com.home.ppmtool.service.MapValidationErrorService;
import com.home.ppmtool.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;
        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectIdentifier){
        Project project = projectService.findProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<Project>(project,HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @GetMapping("/delete/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier){
        projectService.deleteProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<String >("Project delete successfully",HttpStatus.OK);
    }

}
