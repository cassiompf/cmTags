package gmail.fopypvp174.cmtags.objects;

public class Grupos {

    private String nome;
    private String permissao;
    private String preffix;
    private String suffix;
    private Integer prioridade;

    public Grupos(String nome, String permissao, String preffix, String suffix, Integer prioridade) {
        this.nome = nome;
        this.permissao = permissao;
        this.preffix = preffix;
        this.suffix = suffix;
        this.prioridade = prioridade;
    }

    public String getNome() {
        return nome;
    }

    public String getPermissao() {
        return permissao;
    }

    public String getPreffix() {
        return preffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

}
