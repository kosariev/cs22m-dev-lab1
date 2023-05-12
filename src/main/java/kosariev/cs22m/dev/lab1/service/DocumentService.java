package kosariev.cs22m.dev.lab1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import kosariev.cs22m.dev.lab1.model.Document;
import kosariev.cs22m.dev.lab1.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }

    public List<Document> getDocumentsByCreated(Date from, Date to) {
        return documentRepository.findAllByCreatedBetween(from, to);
    }

    public List<Document> getDocumentsByOwner(String owner) {
        return documentRepository.findAllByOwner(owner);
    }

    public List<Document> getDocumentsBySigned(String owner, boolean sign) {
        if (sign) {
            return documentRepository.findAllByOwnerAndSignedIsNotNull(owner);
        }
        return documentRepository.findAllByOwnerAndSignedIsNull(owner);
    }

    public Document updateDocument(Document document) {
        Optional<Document> documentElement = documentRepository.findById(document.getId());
        if (documentElement.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            Document newDocument = documentElement.get();

            if (document.getTitle() != null) {
                newDocument.setTitle(document.getTitle());
            }
            if (document.getBody() != null) {
                newDocument.setBody(document.getBody());
            }
            if (document.getSigned() != null) {
                if (documentElement.get().getCreated().after(document.getSigned())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sign date cannot be less then created");
                } else {
                    newDocument.setSigned(document.getSigned());
                }
            }
            if (document.getType() != null) {
                if (ObjectUtils.containsConstant(Document.Type.values(), document.getType().toString(), false)) {
                    newDocument.setType(document.getType());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown new document type");
                }
            }
            documentRepository.save(newDocument);
            return newDocument;
        }
    }

    public Document getDocumentById(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return document.get();
        }
    }

    public void deleteDocument(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            documentRepository.delete(document.get());
        }
    }
}
