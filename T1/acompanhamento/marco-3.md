\# Marco 3 — Aplicação Básica de DFS



\## Aplicação da DFS



Neste marco foi aplicada a \*\*DFS (Depth-First Search / Busca em Profundidade)\*\* sobre a instância utilizada nos marcos anteriores.



A DFS explora um caminho o máximo possível antes de retornar para explorar outros caminhos.



Para a execução manual, foi considerada a ordem de vizinhos apresentada na lista de adjacência.



\## Grafo utilizado



```text

&#x20;      2

&#x20;     / \\

&#x20;    1---3

&#x20;    |

&#x20;    4

&#x20;    |

&#x20;    5

```



Lista de adjacência:



```text

1 -> 2, 3, 4

2 -> 1, 3

3 -> 1, 2

4 -> 1, 5

5 -> 4

```



\## Execução manual



A busca começa no vértice `1`.



Seguindo a ordem adotada para os vizinhos:



```text

1 -> 2 -> 3

&#x20;         |

&#x20;       retorna

&#x20;         

1 -> 4 -> 5

```



A ordem de descoberta dos vértices foi:



```text

1, 2, 3, 4, 5

```



A ordem de finalização foi:



```text

3, 2, 5, 4, 1

```



\## Estados de visita



Durante a execução da DFS, podemos considerar três estados:



\- não visitado;

\- visitando;

\- finalizado.



Quando um vértice é encontrado pela primeira vez, ele passa para o estado de visitando. Depois que todos os seus vizinhos são analisados, ele é considerado finalizado.



O vetor `marked\[]` da implementação de referência é utilizado para indicar se um vértice já foi visitado.



\## Árvore DFS



As arestas utilizadas para descobrir novos vértices formam a seguinte árvore DFS:



```text

&#x20;       1

&#x20;      / \\

&#x20;     2   4

&#x20;     |   |

&#x20;     3   5

```



A aresta `1-3` existe no grafo original, mas não faz parte da árvore DFS, pois o vértice `3` foi descoberto anteriormente através do vértice `2`.



\## Predecessores



Os predecessores obtidos nessa execução são:



```text

pred(1) = -

pred(2) = 1

pred(3) = 2

pred(4) = 1

pred(5) = 4

```



Na implementação de referência, essa informação é armazenada pelo vetor `edgeTo\[]`.



Assim:



```text

edgeTo\[2] = 1

edgeTo\[3] = 2

edgeTo\[4] = 1

edgeTo\[5] = 4

```



O predecessor representa o vértice pelo qual um determinado vértice foi descoberto.



\## Tempos de descoberta e término



Foi utilizado um contador de tempo para registrar a entrada e a finalização de cada vértice.



```text

tempo 1  -> descobre 1

tempo 2  -> descobre 2

tempo 3  -> descobre 3

tempo 4  -> finaliza 3

tempo 5  -> finaliza 2

tempo 6  -> descobre 4

tempo 7  -> descobre 5

tempo 8  -> finaliza 5

tempo 9  -> finaliza 4

tempo 10 -> finaliza 1

```



Tabela:



| Vértice | Descoberta | Término | Predecessor |

|---------:|-----------:|--------:|------------:|

| 1 | 1 | 10 | - |

| 2 | 2 | 5 | 1 |

| 3 | 3 | 4 | 2 |

| 4 | 6 | 9 | 1 |

| 5 | 7 | 8 | 4 |



O tempo de descoberta indica quando o vértice foi encontrado pela primeira vez.



O tempo de término indica quando todos os seus vizinhos já foram analisados e sua exploração foi finalizada.



\## Pré-ordem e pós-ordem



A pré-ordem registra os vértices na ordem em que são descobertos:



```text

Pré-ordem = \[1, 2, 3, 4, 5]

```



A pós-ordem registra os vértices na ordem em que são finalizados:



```text

Pós-ordem = \[3, 2, 5, 4, 1]

```



Essas ordens dependem da ordem utilizada para percorrer os vizinhos durante a DFS.



\## Alcançabilidade



A DFS permite verificar se o computador de destino pode ser alcançado a partir da origem.



Na instância utilizada, o vértice `5` é alcançável a partir do vértice `1`.



Utilizando os predecessores, também é possível reconstruir um caminho entre a origem e o destino.



\## Aplicabilidade ao problema



A DFS é útil para verificar a alcançabilidade e encontrar um caminho entre os computadores `1` e `n`.



Entretanto, a DFS \*\*não garante que o caminho encontrado seja o menor caminho\*\* em um grafo não ponderado.



Como o problema Message Route exige uma rota com o menor número possível de computadores, a DFS é apenas parcialmente aplicável à solução.



Por esse motivo, a BFS será utilizada como algoritmo principal no Marco 4.



\## Relação com a implementação de referência



Na implementação de referência estudada:



\- `marked\[]` indica quais vértices já foram visitados;

\- `edgeTo\[]` registra os predecessores;

\- a DFS realiza a exploração em profundidade;

\- os conceitos de pré-ordem e pós-ordem representam, respectivamente, a ordem de descoberta e a ordem de finalização dos vértices.



A implementação foi utilizada como referência para compreender e adaptar os conceitos necessários ao problema.

