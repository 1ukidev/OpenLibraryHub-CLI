package openlibraryhub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "turmas")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TurmaEntity extends openlibraryhub.entities.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    public TurmaEntity(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Id: " + id + '\n' +
               "Nome: " + nome + '\n';
    }
}
