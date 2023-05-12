package kosariev.cs22m.dev.lab1.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "DOCUMENT")
@Entity
public class Document {
    public enum Type {
        VACATION, JOB, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "integer auto_increment")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String body;

    @Temporal(TemporalType.DATE)
    private Date created;

    @Temporal(TemporalType.DATE)
    private Date signed;

    private String owner;

}


