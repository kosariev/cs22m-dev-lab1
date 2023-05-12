package kosariev.cs22m.dev.lab1.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import kosariev.cs22m.dev.lab1.model.Document;
import kosariev.cs22m.dev.lab1.service.DocumentService;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@NoArgsConstructor
@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/documents")
    public List<Document> getAllDocuments() {
        return documentService.getDocuments();
    }

    @GetMapping("/documents/owner/{owner}")
    public List<Document> getAllDocumentsByOwner(@PathVariable("owner") String owner) {
        try {
            return documentService.getDocumentsByOwner(owner);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/documents/signed/{owner}/{isSigned}")
    public List<Document> getAllDocumentsBySigned(@PathVariable("owner") String owner, @PathVariable("isSigned") String isSigned) {
        try {
            return documentService.getDocumentsBySigned(owner, Boolean.parseBoolean(isSigned));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/documents/period/{from}/{to}")
    public List<Document> getAllDocumentsByCreated(@PathVariable("from") String from, @PathVariable("to") String to) {
        try {
            return documentService.getDocumentsByCreated(
                    new SimpleDateFormat("yyyy-MM-dd").parse(from),
                    new SimpleDateFormat("yyyy-MM-dd").parse(to));
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/documents")
    public ResponseEntity<Document> saveDocument(@RequestBody Document document) {
        return new ResponseEntity<>(documentService.saveDocument(document), HttpStatus.CREATED);
    }


    @GetMapping("/document/{id}")
    public Document getDocumentById(@PathVariable("id") Long id) {
        return documentService.getDocumentById(id);
    }

    @PutMapping("/document/{id}")
    public ResponseEntity<Document> updateDocument(@RequestBody Document document, @PathVariable("id") Long id) {
        document.setId(id);
        return new ResponseEntity<>(documentService.updateDocument(document), HttpStatus.CREATED);
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
