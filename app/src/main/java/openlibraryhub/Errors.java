package openlibraryhub;

public class Errors {
    // General
    public static final String INVALID_DB_MESSAGE = "Por favor, defina as variáveis de ambiente OLH_DB_URL, OLH_DB_USER, OLH_DB_PASSWORD.\n";
    public static final String INVALID_OPTION_MESSAGE = "Opção inválida!\n";
    public static final String INVALID_INPUT_MESSAGE = "Entrada inválida. Por favor, tente novamente.\n";
    public static final String EMPTY_INPUT_MESSAGE = "Entrada vazia detectada. Por favor, tente novamente.\n";
    public static final String UNEXPECTED_ERROR_MESSAGE = "Erro inesperado!\n";
    public static final String INVALID_DATE_MESSAGE ="Data inválida!";

    // DAO
    public static final String NULL_ID = "Id inválido!";
    public static final String NULL_NAME = "Nome inválido!";

    // Books
    public static final String NULL_BOOK = "Livro inválido!";
    public static final String NULL_TITLE = "Título inválido!";
    public static final String NULL_AUTHOR = "Autor inválido!";
    public static final String NULL_SECTION = "Seção inválida!";
    public static final String NULL_PAGES = "Quantidade de páginas inválidas!";
    public static final String NULL_YEAR = "Ano inválido!";
    public static final String NULL_STOCK = "Quantidade em estoque inválido!";

    // Students
    public static final String NULL_STUDENT = "Estudante inválido!";

    // Classes
    public static final String NULL_CLASS = "Turma inválida!";

    // Loans
    public static final String NULL_LOAN = "Empréstimo inválido!";
    public static final String NULL_LOAN_DATE = "Data de empréstimo inválida!";
    public static final String NULL_RETURN_DATE = "Data de devolução inválida!";
}
