public enum Ingrediente {
    BACON(3.0, "bacon"),
    CHEDDAR(2.0, "cheddar"),
    BOLONHESA(4.5,"bolonhesa"),
    PALMITO(4.0, "palmito"),
    CALABRESA(3.0, "calabresa");

    double precoUnitario;
    String descricao;

    Ingrediente(double precoUnitario, String desc){
        this.precoUnitario = precoUnitario;
        this.descricao = desc;
    }

    double precoAcumulado(int quantos){
        return this.precoUnitario * quantos;
    }

    double getPreco(){
        return this.precoUnitario;
    }

    @Override
    public String toString(){
        return this.descricao+ " - R$ "+this.precoUnitario;
    }
    
}
