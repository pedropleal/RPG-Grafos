# Marco 4 — Implementação, Testes e Conclusão

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

> **Atenção:** o enunciado oficial do Marco 4 ainda precisa ser conferido. Este documento reúne a implementação, os testes, a complexidade e o resultado obtido, e deve ser revisado quando o enunciado for confirmado.

## Implementação

O código final está em [`../src/Main.java`](../src/Main.java), na versão submetida ao VJudge.

Estruturas principais:

| Estrutura | Função |
|---|---|
| `ArrayList<Integer>[] adj` | lista de adjacência do grafo |
| `boolean[] marked` | vértices já visitados pela DFS |
| `int count` | quantidade de componentes conexas |
| `TreeSet<String> arestas` | cada aresta uma única vez, como `"a b"` com `a < b`, em ordem numérica |
| `ArrayList<String> pontes` | pontes encontradas, já no formato `a - b` |

Métodos:

- `dfs(v, a, b)`: DFS recursiva a partir de `v` que marca os vértices alcançáveis, ignorando a aresta `(a, b)` nos dois sentidos;
- `contarComponentes(n, a, b)`: reinicia `marked[]` e conta as componentes conexas, ignorando a aresta `(a, b)`;
- `main`: lê os casos até o fim da entrada, monta o grafo, testa cada aresta e imprime o resultado.

Fluxo de cada caso de teste:

```text
ler n
criar adj[0..n-1]
para cada linha "v (k) w1 ... wk":
    adicionar wi em adj[v]
    adicionar a aresta (min(v, wi), max(v, wi)) ao TreeSet

original = contarComponentes(n, -1, -1)

para cada aresta (a, b) do TreeSet, em ordem crescente:
    se contarComponentes(n, a, b) > original:
        adicionar "a - b" em pontes

imprimir "<quantidade> critical links"
imprimir cada ponte
imprimir linha em branco
```

Como o `TreeSet` já percorre as arestas em ordem numérica crescente e cada aresta é guardada com `a < b`, a lista de pontes sai na ordem exigida pela saída sem nenhuma ordenação adicional.

## Testes

Os casos estão em [`../dados/casos-de-teste.txt`](../dados/casos-de-teste.txt).

Ambiente de execução local:

- compilação: `javac 1.8.0_504` (Eclipse Temurin);
- execução: `java 1.8.0_504` (Eclipse Temurin).

As saídas foram comparadas exatamente com as saídas esperadas, incluindo a linha em branco ao final de cada caso. Como o `println` no Windows gera `\r\n`, as quebras de linha foram convertidas para `\n` antes da comparação.

| Caso | Descrição | Resultado |
|:----:|---|:---:|
| 1 | Exemplo do enunciado (dois casos na mesma entrada) | ✅ passou |
| 2 | Grafo sem vértices (`n = 0`) | ✅ passou |
| 3 | Ciclo (triângulo): nenhuma ponte | ✅ passou |
| 4 | Caminho (árvore): todas as arestas são pontes | ✅ passou |
| 5 | Somente vértices isolados | ✅ passou |
| 6 | Dois ciclos ligados por uma única aresta | ✅ passou |
| 7 | Estrela com linhas fora de ordem (ordenação e `a < b`) | ✅ passou |
| 8 | Uma única aresta | ✅ passou |
| 9 | Exemplo de 5 vértices usado nos marcos | ✅ passou |

**Resultado: 9 de 9 casos passaram.**

Na compilação, o `javac` emite um único aviso, de conversão não verificada, na linha `adj = new ArrayList[n];`. Esse aviso é comum ao criar vetores de listas genéricas em Java e não afeta a execução.

## Complexidade

Sejam `V` o número de vértices e `E` o número de arestas.

### Complexidade de tempo

- Leitura e montagem do grafo: cada conexão é lida duas vezes e inserida no `TreeSet`, em `O(E log E)`.
- Uma contagem de componentes por DFS: `O(V + E)`.
- A contagem é feita uma vez para o grafo original e uma vez para cada uma das `E` arestas.

Total:

```text
O(E log E + (E + 1) · (V + E)) = O(E · (V + E))
```

### Complexidade de espaço

- lista de adjacência: `O(V + E)`;
- `marked[]`: `O(V)`;
- conjunto de arestas e lista de pontes: `O(E)`;
- pilha de recursão da DFS: até `O(V)`.

Total:

```text
O(V + E)
```

### Comparação com a abordagem inicial

A abordagem com `disc[]` e `low[]`, registrada no [Marco 3](marco-3.md), encontra todas as pontes com uma única DFS, em `O(V + E)`. A estratégia adotada é mais custosa, mas é mais simples, reaproveita diretamente a lógica de `CC.java` e foi suficiente para o problema.

## Resultado no juiz

| Item | Valor |
|---|---|
| Plataforma | VJudge |
| Problema | UVA 796 — Critical Links |
| Linguagem | Java 1.8.0 |
| Veredito | **Accepted** |
| Tempo | 990 ms |
| RunID remoto | 31332405 |

> **Pendente:** adicionar o print da submissão em `../evidencias/`.

## Conclusão

O problema Critical Links foi modelado como a busca de **pontes** em um grafo não direcionado, não ponderado e possivelmente desconexo.

A primeira abordagem estudada foi a DFS com `disc[]` e `low[]`. Por sugestão do professor, ela foi substituída por uma estratégia baseada diretamente na definição de ponte: ignorar cada aresta e verificar, com a DFS e a contagem de componentes da classe `CC.java` do algs4, se o número de componentes conexas aumenta.

A implementação passou nos 9 casos de teste locais com Java 8 e recebeu **Accepted** no UVA 796, com tempo de 990 ms.
