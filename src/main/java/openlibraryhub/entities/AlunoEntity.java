package openlibraryhub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alunos")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlunoEntity extends openlibraryhub.entities.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "turma", nullable = false)
    private TurmaEntity turma;

    public AlunoEntity(String nome, TurmaEntity turma) {
        this.nome = nome;
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "Id: " + id + '\n' +
               "Nome: " + nome + '\n' +
               "Turma: " + turma.getNome() + '\n'; 
    }
}
