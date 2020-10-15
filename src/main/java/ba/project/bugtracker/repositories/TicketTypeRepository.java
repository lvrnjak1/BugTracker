package ba.project.bugtracker.repositories;

import ba.project.bugtracker.model.TicketType;
import ba.project.bugtracker.model.enums.TicketTypeName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketTypeRepository extends CrudRepository<TicketType, Long> {
    Optional<TicketType> findByType(TicketTypeName typeName);
}
