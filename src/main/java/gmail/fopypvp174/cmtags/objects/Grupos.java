package gmail.fopypvp174.cmtags.objects;

public class Grupos {

    private String nome;
    private String permissao;
    private String tag;
    private Integer prioridade;

    public Grupos(String nome, String permission, String tag, Integer priority) {
        this.nome = nome;
        this.permissao = permission;
        this.tag = tag;
        this.prioridade = priority;
    }

    public String getNome() {
        return nome;
    }

    public String getPermissao() {
        return permissao;
    }

    public String getTag() {
        return tag;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

}
