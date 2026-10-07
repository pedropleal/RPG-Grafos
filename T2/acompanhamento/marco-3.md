# Marco 3 — Estruturas e estratégia de DFS

## Escopo

Apresentar as classes de referência e as estruturas previstas para o código, conforme a orientação registrada na conversa GRAFOS. Este marco descreve a estratégia e seu rastreamento manual; não registra uma implementação concluída.

## Classes de referência

| Classe do algs4 | Papel previsto |
|---|---|
| Graph | Representar o grafo não direcionado e acessar os vizinhos por adj(v) |
| DepthFirstPaths | Referência para DFS recursiva, marcação de visitas e registro dos pais |
| CC | Referência para iniciar uma DFS em cada vértice ainda não visitado, cobrindo todas as componentes |

`DepthFirstPaths` e `CC` são as referências de adaptação apresentadas nos materiais anteriores. Elas não detectam pontes diretamente. A extensão com `disc`, `low` e o critério de ponte é uma adaptação para o problema, não atribuída aos códigos do professor.

## Estruturas previstas

| Estrutura | Finalidade |
|---|---|
| Lista de adjacência | Armazenar os vizinhos de cada vértice |
| marked[] | Indicar os vértices já visitados |
| parent[] / edgeTo[] | Registrar o pai na árvore DFS; são alternativas de nome para o mesmo papel |
| disc[] | Registrar o instante de descoberta |
| low[] | Menor disc alcançável a partir da subárvore por arestas da árvore seguidas de, no máximo, uma aresta de retorno |
| Contador de tempo | Numerar as descobertas |
| Lista de pontes | Guardar os pares de extremos das pontes |
| Pilha de recursão | Manter as chamadas da DFS em andamento |

Esses vetores e estruturas não são classes do algs4. O contador de descoberta também não é o contador de componentes da classe CC.

## Adaptação prevista

Ao descobrir v, inicializar disc[v] e low[v] com o tempo corrente. Depois de explorar um filho w, atualizar low[v] com low[w]. Para um vizinho já visitado que não é o pai, considerar disc[w].

A aresta da árvore (v,w) é ponte quando **low[w] > disc[v]**. A igualdade significa que há retorno para v e, portanto, não caracteriza ponte. O teste vale também para arestas que saem da raiz; não se usa aqui a regra especial de raiz para vértices de articulação.

Percorrer todos os vértices e iniciar uma DFS nos ainda não marcados, como em CC. Normalizar os extremos e ordenar os pares encontrados. No modelo simples, ignora-se o vizinho pai; se a implementação vier a admitir arestas paralelas, deverá distinguir a identidade da aresta-pai.

## Rastreamento manual do exemplo preservado

Usar as listas do [Marco 2](marco-2.md), começando em 0 e contando descobertas a partir de 0.

- Descobertas: 0, 1, 3, 2, 4.
- Arestas da árvore: (0,1), (1,3), (3,2), (2,4).
- Retorno de 4 para o ancestral 3: low[4] passa a 2; esse valor se propaga para 2.
- Finalizações: 4, 2, 3, 1, 0.

| Vértice | Pai | disc | low final |
|---|---|---|---|
| 0 | −1 (raiz) | 0 | 0 |
| 1 | 0 | 1 | 1 |
| 2 | 3 | 3 | 2 |
| 3 | 1 | 2 | 2 |
| 4 | 2 | 4 | 2 |

No retorno de 4 para 2, 2>3 é falso. No retorno de 2 para 3, 2>2 é falso. No retorno de 3 para 1, 2>1 identifica (1,3); no retorno de 1 para 0, 1>0 identifica (0,1). A aresta (3,4) está fora da árvore e fecha o ciclo. As únicas pontes são (0,1) e (1,3).

## Custos previstos

Com V vértices, E arestas e B pontes, a DFS custa O(V+E); ordenar a saída custa O(B log B). O total previsto é O(V+E+B log B). O grafo ocupa O(V+E), os vetores e a pilha O(V), e a lista de pontes O(B). A memória total é O(V+E).

## Referências

- Ricardo Carubbi: A2_Tipos_Representação_Computacional_ (representação), A3_BFS_DFS (marked, edgeTo e DepthFirstPaths) e A4_Conectividade (componentes e CC).
- [Graph](https://algs4.cs.princeton.edu/41graph/Graph.java.html), [DepthFirstPaths](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html) e [CC](https://algs4.cs.princeton.edu/41graph/CC.java.html), de Sedgewick e Wayne.

O desenvolvimento do Marco 4 permanece separado e pendente.
