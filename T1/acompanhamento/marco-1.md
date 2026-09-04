\# Marco 1 — Modelagem



\## Problema



O problema escolhido foi o \*\*Message Route\*\*, da plataforma CSES.



Existe uma rede formada por `n` computadores e `m` conexões. O objetivo é determinar se existe um caminho entre o computador `1` e o computador `n`.



Caso exista, deve ser exibido um caminho com o menor número possível de computadores. Caso contrário, deve ser exibido `IMPOSSIBLE`.



\## Entrada



A primeira linha contém:



\- `n`: número de computadores;

\- `m`: número de conexões.



As próximas `m` linhas possuem dois inteiros `a` e `b`, indicando uma conexão entre os computadores `a` e `b`.



\## Saída



Caso exista caminho entre `1` e `n`, deve ser exibido:



\- a quantidade de computadores presentes no caminho;

\- a sequência de computadores do caminho.



Caso não exista caminho, a saída deve ser:



```text

IMPOSSIBLE

