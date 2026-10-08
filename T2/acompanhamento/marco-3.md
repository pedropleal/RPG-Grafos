# Marco 3 — Aplicação de DFS na Busca de Pontes

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Grafo utilizado

```text
       1
      / \
     0---2
     |
     3
     |
     4
```

Lista de adjacência:

```text
0 -> 1, 2, 3
1 -> 0, 2
2 -> 0, 1
3 -> 0, 4
4 -> 3
```

Nas execuções manuais abaixo, os vizinhos são percorridos na ordem da lista de adjacência.

## Abordagem inicial: DFS com `disc[]` e `low[]`

> Esta seção registra a primeira abordagem apresentada neste marco. Ela foi mantida para documentar a evolução da solução, embora não seja a utilizada no código final.

A primeira abordagem estudada para encontrar pontes foi uma DFS com dois vetores:

- `disc[v]`: tempo de descoberta do vértice `v`;
- `low[v]`: menor tempo de descoberta alcançável a partir da subárvore de `v`, usando no máximo uma aresta de retorno.

Durante a DFS, para cada vizinho `w` do vértice `v`:

- se `w` não foi visitado, a DFS continua em `w` e, ao retornar, `low[v] = min(low[v], low[w])`;
- se `w` já foi visitado e não é o pai de `v`, a aresta é de retorno e `low[v] = min(low[v], disc[w])`.

Uma aresta da árvore DFS `(v, w)` é ponte quando:

```text
low[w] > disc[v]
```

ou seja, quando a subárvore de `w` não tem nenhum caminho alternativo para voltar até `v` ou a um ancestral de `v`.

### Execução manual

A DFS começa no vértice `0`, com o tempo iniciando em `1`:

```text
descobre 0 (disc = 1)
  descobre 1 (disc = 2)
    0 é pai de 1 -> ignora
    descobre 2 (disc = 3)
      0 já visitado e não é pai -> low[2] = min(3, disc[0]) = 1
      1 é pai de 2 -> ignora
    retorna a 1: low[1] = min(2, low[2]) = 1
    aresta 1-2: low[2] = 1 > disc[1] = 2? não
  retorna a 0: low[0] = min(1, low[1]) = 1
  aresta 0-1: low[1] = 1 > disc[0] = 1? não
  2 já visitado -> low[0] = min(1, disc[2]) = 1
  descobre 3 (disc = 4)
    0 é pai de 3 -> ignora
    descobre 4 (disc = 5)
      3 é pai de 4 -> ignora
    retorna a 3: low[3] = min(4, low[4]) = 4
    aresta 3-4: low[4] = 5 > disc[3] = 4? sim -> PONTE
  retorna a 0: low[0] = min(1, low[3]) = 1
  aresta 0-3: low[3] = 4 > disc[0] = 1? sim -> PONTE
```

Tabela final:

| Vértice | `disc` | `low` |
|--------:|-------:|------:|
| 0 | 1 | 1 |
| 1 | 2 | 1 |
| 2 | 3 | 1 |
| 3 | 4 | 4 |
| 4 | 5 | 5 |

Pontes encontradas: `0 - 3` e `3 - 4`.

Essa abordagem encontra todas as pontes com uma única DFS, em `O(V + E)`.

## Mudança de abordagem

Por sugestão do professor, a abordagem com `disc[]` e `low[]` foi substituída por uma estratégia mais simples, construída diretamente sobre a definição de ponte:

> Uma aresta é ponte quando a sua remoção aumenta o número de componentes conexas do grafo.

Motivos da mudança:

- a nova estratégia reaproveita a DFS e a contagem de componentes conexas já estudadas na classe `CC.java` do algs4;
- não exige os conceitos adicionais de tempo de descoberta, aresta de retorno e `low`;
- a correção decorre diretamente da definição de ponte, o que facilita a explicação e a verificação;
- o custo maior, `O(E · (V + E))`, é suficiente para o problema: a solução recebeu **Accepted** no UVA 796.

## Estratégia adotada: ignorar cada aresta e recontar componentes

O algoritmo:

1. conta as componentes conexas do grafo original;
2. para cada aresta `(a, b)` do conjunto de arestas:
   1. executa novamente a contagem, mas a DFS ignora a aresta `(a, b)`;
   2. se a quantidade de componentes aumentou, `(a, b)` é ponte.

Na DFS, a aresta é ignorada nos dois sentidos:

```java
if ((v == a && w == b) ||
    (v == b && w == a)) {
    continue;
}
```

Para a contagem original, o programa usa `a = -1` e `b = -1`, valores que não correspondem a nenhum vértice. Assim, nenhuma aresta é ignorada.

### Contagem original

```text
dfs(0): marca 0
  w = 1 -> dfs(1): marca 1
    w = 0 já marcado
    w = 2 -> dfs(2): marca 2
      w = 0 já marcado
      w = 1 já marcado
  w = 2 já marcado
  w = 3 -> dfs(3): marca 3
    w = 0 já marcado
    w = 4 -> dfs(4): marca 4
      w = 3 já marcado
```

Todos os vértices foram marcados a partir de `0`. Portanto:

```text
original = 1 componente
```

### Remoção de cada aresta

| Aresta ignorada | Vértices alcançados a partir de `0` | Componentes | Ponte? |
|:---------------:|:------------------------------------|:-----------:|:------:|
| `0 - 1` | 0, 2, 1, 3, 4 | 1 | não |
| `0 - 2` | 0, 1, 2, 3, 4 | 1 | não |
| `0 - 3` | 0, 1, 2 | 2 (`{0, 1, 2}` e `{3, 4}`) | **sim** |
| `1 - 2` | 0, 1, 2, 3, 4 | 1 | não |
| `3 - 4` | 0, 1, 2, 3 | 2 (`{0, 1, 2, 3}` e `{4}`) | **sim** |

Ao ignorar `0 - 1`, o vértice `1` ainda é alcançado pelo caminho `0 -> 2 -> 1`. O mesmo ocorre com as demais arestas do ciclo `0 - 1 - 2`.

Ao ignorar `0 - 3`, a DFS a partir de `0` não alcança `3`. O laço da contagem inicia uma nova DFS em `3`, que alcança `4`, formando a segunda componente.

### Resultado

```text
2 critical links
0 - 3
3 - 4

```

O resultado é o mesmo da abordagem com `disc[]` e `low[]`, e foi confirmado executando o `Main.java` em Java 8 (CASO 9 de [`../dados/casos-de-teste.txt`](../dados/casos-de-teste.txt)).

## Relação com a implementação de referência

A classe `CC.java` do algs4 conta componentes conexas com:

- `marked[]`, que indica os vértices já visitados;
- uma DFS recursiva que marca todos os vértices alcançáveis;
- um laço sobre todos os vértices que inicia uma nova DFS em cada vértice ainda não marcado, incrementando `count`.

O `Main.java` mantém essa mesma lógica em `dfs` e `contarComponentes`. As adaptações foram:

- a DFS recebe dois parâmetros extras, `a` e `b`, para ignorar a aresta testada;
- não são usados os vetores `id[]` e `size[]` da `CC.java`, pois a solução precisa apenas da quantidade de componentes;
- o grafo é representado por um vetor de `ArrayList<Integer>` em vez da classe `Graph` do algs4;
- a contagem é repetida uma vez para cada aresta.
