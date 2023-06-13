import java.math.BigDecimal;
import java.math.RoundingMode;

public class TortaDeLimao extends Sobremesa {
    private static final BigDecimal PRECO_BASE = new BigDecimal(12.0).setScale(2, RoundingMode.HALF_UP);

    public TortaDeLimao() {
        super(0, 0.0, "Torta de Limão", PRECO_BASE.doubleValue());
    }

     /**
     * Descrição da sobresa em string: cabeçalho, detalhe do preço, rodapé.
     * @return String com o formato descrito acima.
     */
    @Override
    public String toString(){
        StringBuilder desc = new StringBuilder(super.toString());
        
        desc.append(". R$ "+PRECO_BASE);
        desc.append(rodape());
        return desc.toString();
    }
}