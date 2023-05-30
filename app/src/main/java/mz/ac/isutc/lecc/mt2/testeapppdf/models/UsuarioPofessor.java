package mz.ac.isutc.lecc.mt2.testeapppdf.models;

public class UsuarioPofessor extends Usuario{

    private String disciplina;
    private String telefone;

    public UsuarioPofessor(String id, String userName, String email, String password, String disciplina, String telefone) {
        super(id, userName, email, password);
        this.disciplina = disciplina;
        this.telefone = telefone;
    }

    public UsuarioPofessor() {
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
