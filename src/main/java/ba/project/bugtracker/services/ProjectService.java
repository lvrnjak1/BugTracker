package ba.project.bugtracker.services;

import ba.project.bugtracker.exceptions.custom.EntityNotFoundException;
import ba.project.bugtracker.model.Project;
import ba.project.bugtracker.model.User;
import ba.project.bugtracker.repositories.ProjectRepository;
import ba.project.bugtracker.requests.ProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project projectFromRequest(ProjectRequest projectRequest, User projectManager) {
        return new Project(projectRequest.getName(), projectManager);
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project with id " + projectId + " doesn't exist!")
        );
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public Project findByCode(String code) {
        return projectRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("Project with code " + code + " doesn't exist!")
        );
    }
}
