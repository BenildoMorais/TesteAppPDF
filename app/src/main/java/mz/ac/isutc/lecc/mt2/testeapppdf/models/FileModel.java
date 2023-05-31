package mz.ac.isutc.lecc.mt2.testeapppdf.models;

public class FileModel {
    private String fileName, fileUrl, username, disciplina, id;

    public FileModel(String id, String fileName, String fileUrl,String username, String disciplina) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.username = username;
        this.disciplina = disciplina;
        this.id = id;
    }

    public FileModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
