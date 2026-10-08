# Marco 3 — Estruturas e estratégia de DFS

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

> **Registro da evolução:** a estratégia com `disc[]` e `low[]` descrita nas primeiras seções foi a apresentada inicialmente neste marco e está preservada como registro. Por sugestão do professor, ela foi substituída na implementação final pela remoção temporária de cada aresta seguida de recontagem de componentes com DFS. A mudança está documentada na seção [Mudança de estratégia](#mudança-de-estratégia-remoção-de-arestas--dfs).

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

## Mudança de estratégia: remoção de arestas + DFS

Por sugestão do professor, a estratégia com `disc[]` e `low[]` foi substituída por uma estratégia mais simples, construída diretamente sobre a definição do Marco 1:

> Uma aresta e é ponte se ω(G − e) > ω(G).

Em vez de calcular `disc` e `low`, a solução final **ignora temporariamente cada aresta e reconta as componentes conexas com DFS**, usando a lógica da classe `CC`.

Motivos da mudança:

- reaproveita diretamente a DFS e a contagem de componentes de `CC`, já estudadas no material A4_Conectividade;
- não exige os conceitos adicionais de tempo de descoberta, aresta de retorno e `low`;
- a correção decorre diretamente da definição de ponte, o que facilita explicar e verificar a solução;
- o custo maior, O(E·(V+E)), foi suficiente para o problema: a solução recebeu Accepted no UVA 796 (ver [Marco 4](marco-4.md)).

### Estruturas usadas na estratégia final

| Estrutura | Finalidade |
|---|---|
| Lista de adjacência `adj[]` | Vizinhos de cada vértice |
| `marked[]` | Vértices já visitados na contagem atual (como em `CC`) |
| `count` | Quantidade de componentes conexas (como em `CC`) |
| Conjunto de arestas | Cada aresta uma única vez, em ordem crescente |
| Lista de pontes | Arestas cuja remoção aumentou `count` |

Deixam de ser necessários: `parent[]`, `disc[]`, `low[]` e o contador de tempo.

### Algoritmo

1. Contar as componentes do grafo original: `original = contarComponentes(n, -1, -1)`. Os valores `-1` não correspondem a nenhum vértice, então nenhuma aresta é ignorada.
2. Para cada aresta `(a, b)` do conjunto, em ordem crescente:
   1. contar as componentes com a DFS ignorando `(a, b)` nos dois sentidos;
   2. se o resultado for maior que `original`, `(a, b)` é ponte.

Na DFS, a aresta testada é ignorada assim:

```java
if ((v == a && w == b) ||
    (v == b && w == a)) {
    continue;
}
```

### Rastreamento manual do exemplo preservado

Listas de adjacência (Marco 2):

```text
0 -> 1
1 -> 0, 3
2 -> 3, 4
3 -> 1, 2, 4
4 -> 2, 3
```

Contagem original, a partir do vértice 0:

```text
dfs(0): marca 0
  w = 1 -> dfs(1): marca 1
    w = 0 já marcado
    w = 3 -> dfs(3): marca 3
      w = 1 já marcado
      w = 2 -> dfs(2): marca 2
        w = 3 já marcado
        w = 4 -> dfs(4): marca 4
          w = 2 já marcado
          w = 3 já marcado
      w = 4 já marcado
```

Todos os vértices são marcados na primeira DFS: `original = 1`.

Teste de cada aresta:

| Aresta ignorada | DFS iniciadas e vértices marcados | Componentes | Ponte? |
|:---:|---|:---:|:---:|
| (0,1) | dfs(0): {0}; dfs(1): {1, 3, 2, 4} | 2 | **sim** |
| (1,3) | dfs(0): {0, 1}; dfs(2): {2, 3, 4} | 2 | **sim** |
| (2,3) | dfs(0): {0, 1, 3, 4, 2} | 1 | não |
| (2,4) | dfs(0): {0, 1, 3, 2, 4} | 1 | não |
| (3,4) | dfs(0): {0, 1, 3, 2, 4} | 1 | não |

Ao ignorar (0,1), o único vizinho de 0 é descartado e a primeira DFS marca apenas {0}. O laço de `CC` inicia então uma nova DFS em 1, que alcança os demais vértices. Ao ignorar (2,3), o vértice 2 continua alcançável por 3 → 4 → 2, pois está no triângulo.

Resultado:

```text
2 critical links
0 - 1
1 - 3

```

As pontes são as mesmas encontradas pelo rastreamento com `disc` e `low`: (0,1) e (1,3).

### Custos da estratégia final

Cada contagem custa O(V+E), e ela é feita uma vez para o grafo original e uma vez para cada uma das E arestas. O tempo total é O(E·(V+E)); a memória continua O(V+E). A implementação, os testes e o resultado no juiz estão no [Marco 4](marco-4.md).

## Referências

- Ricardo Carubbi: A2_Tipos_Representação_Computacional_ (representação), A3_BFS_DFS (marked, edgeTo e DepthFirstPaths) e A4_Conectividade (componentes e CC).
- [Graph](https://algs4.cs.princeton.edu/41graph/Graph.java.html), [DepthFirstPaths](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html) e [CC](https://algs4.cs.princeton.edu/41graph/CC.java.html), de Sedgewick e Wayne.
