import java.math.BigDecimal;

public class Sobremesa extends Comida {
    public Sobremesa(int maxAdicionais, double precoPorAdicional, String descricao, double precoBase) {
        super(maxAdicionais, precoPorAdicional, descricao, precoBase);
    }

    @Override
    protected BigDecimal valorExtras() {
        return BigDecimal.ZERO;
    }
}