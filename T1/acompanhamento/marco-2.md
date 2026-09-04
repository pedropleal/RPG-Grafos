\# Marco 2 — Representação Computacional



\## Representação escolhida



Para representar o grafo foi escolhida a \*\*lista de adjacência\*\*.



Nessa representação, cada vértice possui uma lista contendo seus vizinhos.



Para a instância utilizada:



```text

1 -> 2, 3, 4

2 -> 1, 3

3 -> 1, 2

4 -> 1, 5

5 -> 4

```



A lista de adjacência foi escolhida porque o problema pode possuir muitos vértices e arestas, e essa representação permite armazenar e percorrer apenas as conexões existentes.



Ela também é adequada para algoritmos de busca como DFS e BFS.



\## Leitura da entrada



A primeira linha da entrada fornece:



```text

n m

```



onde:



\- `n` representa o número de computadores (vértices);

\- `m` representa o número de conexões (arestas).



Em seguida, são lidas `m` conexões.



Cada linha:



```text

a b

```



indica uma aresta não direcionada entre os computadores `a` e `b`.



Como o grafo é não direcionado, ao ler uma conexão entre `a` e `b`, os dois vértices são adicionados às respectivas listas de adjacência.



\## Instância utilizada



```text

5 5

1 2

1 3

1 4

2 3

5 4

```



Representação:



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



\## Medidas estruturais



Para essa instância:



\- Ordem do grafo: `|V| = 5`

\- Tamanho do grafo: `|E| = 5`



Graus dos vértices:



```text

grau(1) = 3

grau(2) = 2

grau(3) = 2

grau(4) = 2

grau(5) = 1

```



Portanto:



\- grau máximo = `3`

\- grau mínimo = `1`

\- grau médio = `2`



O grau médio pode ser calculado por:



```text

2|E| / |V| = 2(5) / 5 = 2

```



\## Densidade



Para um grafo simples e não direcionado:



```text

D = 2|E| / (|V|(|V|-1))

```



Aplicando à instância:



```text

D = 2(5) / (5(5-1))

D = 10 / 20

D = 0,5

```



Portanto, a densidade do grafo é `0,5`, ou 50%.



\## Conectividade e distância



A instância utilizada é conectada, pois existe caminho entre todos os vértices.



A distância mínima entre os computadores `1` e `5` é:



```text

d(1,5) = 2

```



correspondente ao caminho:



```text

1 -> 4 -> 5

```



A distância conta o número de arestas percorridas. Por isso, a distância é 2, embora o caminho contenha 3 computadores.



\## Validação da representação



A lista de adjacência representa corretamente todas as cinco conexões da instância.



Por exemplo:



```text

1 -> 2, 3, 4

```



indica que o computador `1` está conectado aos computadores `2`, `3` e `4`.



Como o grafo é não direcionado, essas conexões também aparecem nas listas dos respectivos vizinhos.



A representação permite realizar de forma eficiente as buscas DFS e BFS utilizadas nos marcos seguintes.

