import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaveDeMorango extends Sobremesa {
    private static final BigDecimal PRECO_BASE = new BigDecimal(8.50).setScale(2, RoundingMode.HALF_UP);

    public PaveDeMorango() {
        super(0, 0.0, "Pavê de Morango", PRECO_BASE.doubleValue());
    }

    /**
     * Descrição da pizza em string: cabeçalho, detalhe do preço, rodapé.
     * 
     * @return String com o formato descrito acima.
     */
    @Override
    public String toString() {
        StringBuilder desc = new StringBuilder(super.toString());

        desc.append(". R$ " + PRECO_BASE);
        desc.append(rodape());
        return desc.toString();
    }
}