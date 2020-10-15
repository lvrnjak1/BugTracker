package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.TicketPriority;
import ba.project.bugtracker.model.enums.TicketPriorityName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketPriorityRepository extends CrudRepository<TicketPriority, Long> {
    Optional<TicketPriority> findByPriority(TicketPriorityName priorityName);
}
