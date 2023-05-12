package kosariev.cs22m.dev.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kosariev.cs22m.dev.lab1.model.Document;

import java.util.Date;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByOwnerAndSignedIsNull(String owner);
    List<Document> findAllByOwnerAndSignedIsNotNull(String owner);
    List<Document> findAllByOwner(String owner);
    List<Document> findAllByCreatedBetween(Date from, Date to);
}
