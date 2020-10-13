package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
