\# Marco 4 — Aplicação Básica de BFS e Conclusão



\## Aplicação da BFS



Neste marco foi aplicada a \*\*BFS (Breadth-First Search / Busca em Largura)\*\*.



Diferentemente da DFS, a BFS explora o grafo por níveis. Primeiro são visitados os vértices mais próximos da origem e depois os vértices dos níveis seguintes.



Como o problema Message Route exige um caminho mínimo em um grafo não ponderado, a BFS foi escolhida como algoritmo da solução final.



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



\## Execução manual e fila



A BFS começa no vértice `1`.



Inicialmente:



```text

Fila: \[1]

```



O vértice `1` é retirado da fila e seus vizinhos `2`, `3` e `4` são descobertos:



```text

Fila: \[2, 3, 4]

```



O vértice `2` é processado. Nenhum novo vértice é descoberto:



```text

Fila: \[3, 4]

```



O vértice `3` também não encontra nenhum novo vértice:



```text

Fila: \[4]

```



O vértice `4` é processado e descobre o vértice `5`:



```text

Fila: \[5]

```



Após o processamento do vértice `5`:



```text

Fila: \[]

```



Assim, todos os vértices alcançáveis a partir da origem foram processados.



\## Níveis



A BFS organiza os vértices de acordo com sua distância da origem:



```text

Nível 0: 1

Nível 1: 2, 3, 4

Nível 2: 5

```



Representação:



```text

&#x20;            1          Nível 0

&#x20;         /  |  \\

&#x20;        2   3   4      Nível 1

&#x20;                |

&#x20;                5      Nível 2

```



Cada nível representa a distância, em número de arestas, até a origem.



Portanto:



```text

distTo\[1] = 0

distTo\[2] = 1

distTo\[3] = 1

distTo\[4] = 1

distTo\[5] = 2

```



\## Predecessores e distâncias



Durante a BFS são armazenadas duas informações importantes:



\- `edgeTo\[]`: indica o predecessor do vértice;

\- `distTo\[]`: indica a distância, em número de arestas, entre a origem e o vértice.



Para a instância utilizada:



| Vértice | edgeTo | distTo |

|--------:|-------:|-------:|

| 1 | - | 0 |

| 2 | 1 | 1 |

| 3 | 1 | 1 |

| 4 | 1 | 1 |

| 5 | 4 | 2 |



Por exemplo:



```text

edgeTo\[5] = 4

```



indica que o vértice `5` foi descoberto através do vértice `4`.



Já:



```text

distTo\[5] = 2

```



indica que o vértice `5` está a duas arestas da origem `1`.



\## Reconstrução do caminho



Os predecessores permitem reconstruir o menor caminho começando pelo destino.



Para o vértice `5`:



```text

edgeTo\[5] = 4

edgeTo\[4] = 1

```



Voltando pelos predecessores:



```text

5 <- 4 <- 1

```



Invertendo a sequência:



```text

1 -> 4 -> 5

```



A distância é:



```text

d(1,5) = 2 arestas

```



Porém, a saída do problema pede a quantidade de computadores presentes no caminho.



O caminho:



```text

1 -> 4 -> 5

```



possui 3 computadores.



Portanto:



```text

k = 3

```



e a saída esperada é:



```text

3

1 4 5

```



\## Comparação entre DFS e BFS



A DFS e a BFS conseguem verificar se existe caminho entre a origem e o destino, mas possuem comportamentos diferentes.



\### DFS



\- realiza busca em profundidade;

\- segue um caminho o máximo possível antes de retornar;

\- permite verificar alcançabilidade;

\- pode encontrar um caminho;

\- não garante o menor caminho.



\### BFS



\- realiza busca em largura;

\- percorre o grafo por níveis;

\- permite verificar alcançabilidade;

\- calcula as distâncias a partir da origem;

\- garante um caminho mínimo em número de arestas em grafos não ponderados.



Por isso, embora a DFS seja útil para analisar a conectividade, a \*\*BFS é mais adequada ao problema Message Route\*\*.



\## Escolha do algoritmo



A BFS foi escolhida porque o problema exige encontrar uma rota com o menor número possível de computadores entre `1` e `n`.



Como o grafo é não ponderado, a BFS percorre os vértices em ordem crescente de distância da origem.



Dessa forma, quando um vértice é descoberto pela primeira vez, a BFS já encontrou uma forma de chegar até ele com o menor número de arestas.



\## Implementação de referência e adaptação



Foi utilizada como referência a implementação `BreadthFirstPaths`.



Os principais elementos aproveitados conceitualmente foram:



```text

marked\[]  -> controla os vértices já visitados

edgeTo\[]  -> armazena os predecessores

distTo\[]  -> armazena as distâncias

Queue     -> controla a ordem de processamento da BFS

```



Para o problema Message Route, a implementação precisa ser adaptada para:



\- ler `n` computadores e `m` conexões;

\- representar os computadores de `1` até `n`;

\- iniciar a BFS no computador `1`;

\- utilizar o computador `n` como destino;

\- verificar se `n` foi alcançado;

\- reconstruir o caminho utilizando os predecessores;

\- imprimir `IMPOSSIBLE` caso não exista caminho;

\- caso exista, imprimir a quantidade de computadores e a sequência do caminho.



\## Complexidade



\### Complexidade de tempo



Utilizando BFS com lista de adjacência:



```text

O(V + E)

```



A BFS processa os vértices e percorre as arestas presentes nas listas de adjacência.



No problema:



```text

V = n = número de computadores

E = m = número de conexões

```



Portanto:



```text

O(n + m)

```



\### Complexidade de espaço



A BFS utiliza estruturas auxiliares como fila e vetores de visitados, predecessores e distâncias.



O espaço extra utilizado pela busca é:



```text

O(V)

```



ou, no problema:



```text

O(n)

```



Considerando também o armazenamento do próprio grafo através da lista de adjacência, o espaço total é:



```text

O(V + E)

```



\## Testes



A solução deve ser validada com diferentes tipos de entrada.



\### Caso 1 — caminho existente



Entrada:



```text

5 5

1 2

1 3

1 4

2 3

5 4

```



Saída esperada:



```text

3

1 4 5

```



\### Caso 2 — destino inalcançável



Exemplo de grafo com componentes separadas:



```text

1 -- 2



3 -- 4

```



Partindo de `1` com destino `4`, não existe caminho.



Saída:



```text

IMPOSSIBLE

```



Também devem ser considerados casos pequenos e diferentes configurações de caminhos para validar a implementação.



\## Submissão e Accepted



Após a implementação final, a solução deverá ser submetida ao problema Message Route.



A evidência da submissão aceita deverá ser armazenada em:



```text

T1/evidencias/accepted.png

```



O resultado `Accepted` comprovará que a implementação atende aos casos de teste da plataforma.



\## Conclusão



A análise realizada nos quatro marcos mostrou que a rede pode ser representada de forma eficiente através de uma lista de adjacência.



A DFS foi útil para estudar alcançabilidade, predecessores, árvore de busca e ordens de visita, mas não garante o menor caminho.



A BFS, por outro lado, percorre o grafo por níveis e garante o menor caminho em número de arestas para este grafo não ponderado.



Por isso, a BFS foi escolhida como algoritmo final para resolver o problema Message Route.

