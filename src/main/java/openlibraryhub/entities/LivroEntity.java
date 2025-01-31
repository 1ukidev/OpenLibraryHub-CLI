package openlibraryhub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livros")
@Getter @Setter @NoArgsConstructor
public class LivroEntity extends openlibraryhub.entities.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 255)
    private String autor;

    @Column(name = "secao", nullable = false, length = 255)
    private String secao;

    @Column(name = "paginas", nullable = false)
    private Integer paginas;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "estoque", nullable = false)
    private Integer estoque;

    public LivroEntity(String titulo, String autor, String secao,
                       Integer paginas, Integer ano, Integer estoque) {
        this.titulo = titulo;
        this.autor = autor;
        this.secao = secao;
        this.paginas = paginas;
        this.ano = ano;
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Id: " + id + '\n' +
               "Título: " + titulo + '\n' +
               "Autor: " + autor + '\n' +
               "Seção: " + secao + '\n' +
               "Páginas: " + paginas + '\n' +
               "Ano: " + ano + '\n' +
               "Estoque: " + estoque + '\n';
    }
}
