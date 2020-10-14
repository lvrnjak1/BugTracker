package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.TicketPriority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriorityRepository extends CrudRepository<TicketPriority, Long> {
}
