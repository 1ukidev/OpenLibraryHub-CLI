package openlibraryhub.entities;

import java.time.LocalDate;

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
import openlibraryhub.Util;

@Entity
@Table(name = "emprestimos")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmprestimoEntity extends openlibraryhub.entities.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno", nullable = false)
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "livro", nullable = false)
    private LivroEntity livro;

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "data_devolucao", nullable = false)
    private LocalDate dataDevolucao;

    public EmprestimoEntity(AlunoEntity aluno, LivroEntity livro,
                            LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.aluno = aluno;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Id: " + id + '\n' +
               "Aluno: " + aluno.getNome() + '\n' +
               "Livro: " + livro.getTitulo() + '\n' +
               "Data de empréstimo: " + Util.formatDefaultDate(dataEmprestimo) + '\n' +
               "Data de devolução: " + Util.formatDefaultDate(dataDevolucao) + '\n';
    }
}
