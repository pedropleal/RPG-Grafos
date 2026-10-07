# Marco 2 — Representação computacional

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

## Referências

A2_Tipos_Representação_Computacional_, de Ricardo Carubbi, sobre lista de adjacência; [Graph do algs4](https://algs4.cs.princeton.edu/41graph/Graph.java.html) como referência de representação; [UVA 796](https://onlinejudge.org/external/7/796.pdf) para leitura das listas.
