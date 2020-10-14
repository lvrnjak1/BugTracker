package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.TicketType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {
}
