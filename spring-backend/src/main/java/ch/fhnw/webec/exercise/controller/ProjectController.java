package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.Project;
import ch.fhnw.webec.exercise.repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/{id}")
    public Project showProject(@PathVariable String id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public Project addProject(@Valid @RequestBody Project project) {
        project.setId(UUID.randomUUID().toString());
        return projectRepository.save(project);
    }

    @PostMapping("/{id}/edit")
    public Project editProject(@PathVariable String id, @Valid @RequestBody Project project) {
        project.setId(id);
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return projectRepository.save(project);
    }

    @PostMapping("/{id}/delete")
    public void deleteProject(@PathVariable String id) {
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        projectRepository.deleteById(id);
    }
}
