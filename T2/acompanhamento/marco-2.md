# Marco 2 — Representação computacional

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Representação escolhida

Será usada lista de adjacência, preservando a decisão anterior. Ela armazena os vizinhos e permite percorrer o grafo em O(V+E), com memória O(V+E). Aqui V e E, nas expressões de custo, são as quantidades de vértices e arestas.

Para o [grafo do Marco 1](marco-1.md):

```text
Adj[0] = [1]
Adj[1] = [0, 3]
Adj[2] = [3, 4]
Adj[3] = [1, 2, 4]
Adj[4] = [2, 3]
```

A ordem crescente dos vizinhos serve para reproduzir o rastreamento manual. Não é uma exigência da DFS nem do formato de entrada.

## Entrada correspondente

```text
5
0 (1) 1
1 (2) 0 3
2 (2) 3 4
3 (3) 1 2 4
4 (2) 2 3
```

A leitura deverá interpretar o número entre parênteses e associar cada lista ao identificador da linha. Como as conexões aparecem nos dois sentidos, não se deve adicionar novamente os dois sentidos para cada ocorrência. Se for utilizado `Graph.addEdge(u,v)`, que insere ambos os sentidos, cada aresta não direcionada deverá ser inserida uma única vez; para este modelo simples, considerar apenas u<v é uma opção.

## Medidas e conferência

| Medida | Valor |
|---|---|
| Ordem | 5 vértices |
| Tamanho | 5 arestas |
| Graus de 0, 1, 2, 3, 4 | 1, 2, 2, 3, 2 |
| Soma dos graus | 10 = 2 × 5 |
| Grau mínimo / máximo / médio | 1 / 3 / 2 |
| Densidade do grafo simples | 2E/(V(V−1)) = 0,5 |
| Componentes conexas | 1 |

As listas são simétricas e contêm dez entradas, correspondentes às cinco arestas. A representação preserva a cadeia 0–1–3 e o triângulo 3–2–4–3. A solução deverá também aceitar listas vazias e redes com várias componentes.

## Representação na implementação final

> Seção acrescentada após a implementação, para relacionar o planejamento acima com o [`Main.java`](../src/Main.java) final.

A lista de adjacência é um vetor de listas:

```java
static ArrayList<Integer>[] adj;
```

Cada linha `u (k) v1 ... vk` é lida assim: o texto `(k)` tem os parênteses removidos para obter `k`, e cada vizinho `w` é adicionado apenas em `adj[u]` (`adj[v].add(w)` no código). Como a entrada já lista cada conexão nas linhas das duas extremidades, as listas resultantes são simétricas sem duplicar entradas. Para a entrada acima, elas ficam exatamente como `Adj[0..4]` mostrado no início deste marco.

Além das listas, o programa guarda cada aresta uma única vez em um conjunto ordenado:

```java
TreeSet<String> arestas
```

Cada aresta é armazenada como `"a b"`, com `a = min(u, w)` e `b = max(u, w)`. As duas ocorrências de uma conexão na entrada geram o mesmo texto, e o `TreeSet` mantém apenas uma. O comparador do `TreeSet` compara os números, primeiro `a` e depois `b`, e não o texto; assim `"2 3"` vem antes de `"10 11"`.

Para o grafo do Marco 1, o conjunto percorrido pela solução é:

```text
0 1
1 3
2 3
2 4
3 4
```

Esse conjunto define a ordem em que cada aresta é testada e, consequentemente, a ordem de impressão das pontes.

## Referências

A2_Tipos_Representação_Computacional_, de Ricardo Carubbi, sobre lista de adjacência; [Graph do algs4](https://algs4.cs.princeton.edu/41graph/Graph.java.html) como referência de representação; [UVA 796](https://onlinejudge.org/external/7/796.pdf) para leitura das listas.
