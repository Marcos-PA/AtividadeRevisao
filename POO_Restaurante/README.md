# Xulambs Pizza & Burger

Projeto de um sistema para ~~uma pizzaria~~ restaurante simpático.
Esta versão inclui o uso da interface Comparable e comparadores para adicionar este comportamento ao Pedido (ordenado por data) e à Comida(ordenada por valor).
## Requisitos atuais

1) Agora o restaurante vende pizzas e sanduíches.
2) Os pedidos podem ser locais ou para entrega.
3) Os pedidos devem ser registrados para clientes.

- **PIZZA**
  - pode ser pequena, média ou grande.
  - a média custa R$25. A pequena é 12% mais barata e a grande é 10% mais cara.
  - pode conter até 8 ingredientes adicionais e cada adicional custa R$4.
- **SANDUÍCHE**
  - um sanduíche custa R$13.
  - se for incluído no combo com refrigerante, o valor é R$16.
  - pode conter até 5 ingredientes adicionais e cada adicional custa R$2,50.
  - pode ser vegano, o que não altera o preço, mas altera a descrição.
- **PEDIDO**
  - Um pedido local pode conter um número ilimitado de comidas. Cobra-se 10% de taxa de serviço.
  - Um pedido para entrega contém até 10 comidas. Há cobrança de taxa de entrega: R$5 até 5km, R$8 até 10km, R$10 acima disso.
  - O pedido deve detalhar os itens, a taxa e o preço a pagar.

4) Precisamos de relatórios:
  - Total vendido pelo restaurante
  - Total vendido em um dia
  - Valor médio de um pedido
  - Pedidos de um dia escolhido
  - Cliente que gastou mais
## Dependência

- JUnit Jupiter para testes.