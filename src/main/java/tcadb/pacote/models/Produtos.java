package tcadb.pacote.models;


public class Produtos {

    private Integer codigoP;
    private String nome;
    private double preco;


    public Integer getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(Integer codigoP) {
        this.codigoP = codigoP;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "codigoP=" + codigoP +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}
