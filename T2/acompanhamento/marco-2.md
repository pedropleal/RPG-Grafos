# Marco 2 — Representação Computacional

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Representação escolhida

Para representar o grafo foi escolhida a **lista de adjacência**.

Na implementação, ela é um vetor de listas:

```java
static ArrayList<Integer>[] adj;
```

onde `adj[v]` contém os vizinhos do vértice `v`.

A lista de adjacência foi escolhida porque armazena apenas as conexões existentes e permite percorrer os vizinhos de cada vértice de forma eficiente, que é exatamente a operação realizada pela DFS.

## Leitura da entrada

Para cada caso de teste, o programa:

1. lê `n` e cria `n` listas vazias;
2. lê, para cada servidor, a linha `u (k) v1 ... vk`;
3. obtém `k` removendo os parênteses do texto `(k)`;
4. adiciona cada vizinho `w` à lista `adj[u]`.

Trecho correspondente:

```java
int v = sc.nextInt();
String grau = sc.next();
int k = Integer.parseInt(
    grau.replace("(", "").replace(")", "")
);

for (int j = 0; j < k; j++) {
    int w = sc.nextInt();
    adj[v].add(w);
    ...
}
```

Como a entrada já lista cada conexão nas linhas das duas extremidades, a leitura de cada linha monta naturalmente a lista de adjacência do grafo não direcionado: a conexão `0 - 3` aparece em `adj[0]` (linha do servidor `0`) e em `adj[3]` (linha do servidor `3`).

## Conjunto de arestas

Além da lista de adjacência, o programa mantém um conjunto com cada aresta uma única vez:

```java
TreeSet<String> arestas
```

Cada aresta é guardada como `"a b"`, com `a = min(v, w)` e `b = max(v, w)`. Assim:

- a conexão lida como `0 3` e como `3 0` vira sempre `"0 3"`, e o `TreeSet` elimina a duplicata;
- o comparador do `TreeSet` ordena as arestas **numericamente**, primeiro por `a` e depois por `b`.

A ordenação numérica é importante: uma comparação de texto colocaria `"10 11"` antes de `"2 3"`. Com o comparador numérico, as pontes já são encontradas na ordem exigida pela saída.

## Instância utilizada

```text
5
0 (3) 1 2 3
1 (2) 0 2
2 (2) 0 1
3 (2) 0 4
4 (1) 3
```

Representação:

```text
       1
      / \
     0---2
     |
     3
     |
     4
```

Lista de adjacência obtida após a leitura:

```text
0 -> 1, 2, 3
1 -> 0, 2
2 -> 0, 1
3 -> 0, 4
4 -> 3
```

Conjunto de arestas (`TreeSet`), na ordem em que será percorrido:

```text
0 1
0 2
0 3
1 2
3 4
```

## Medidas estruturais

Para essa instância:

- Ordem do grafo: `|V| = 5`
- Tamanho do grafo: `|E| = 5`

Graus dos vértices:

```text
grau(0) = 3
grau(1) = 2
grau(2) = 2
grau(3) = 2
grau(4) = 1
```

Portanto:

- grau máximo = `3`
- grau mínimo = `1`
- grau médio = `2`

O grau médio pode ser calculado por:

```text
2|E| / |V| = 2(5) / 5 = 2
```

## Densidade

Para um grafo simples e não direcionado:

```text
D = 2|E| / (|V|(|V| - 1))
```

Aplicando à instância:

```text
D = 2(5) / (5(5 - 1))
D = 10 / 20
D = 0,5
```

Portanto, a densidade do grafo é `0,5`, ou 50%.

## Conectividade

A instância é **conexa**: existe caminho entre todos os pares de servidores. Portanto, o grafo original possui **1 componente conexa**.

Esse valor é a referência usada pela solução: uma aresta é ponte quando, ao ser ignorada, o número de componentes passa a ser maior que 1.

## Validação da representação

A lista de adjacência representa corretamente as cinco conexões da instância. Por exemplo:

```text
0 -> 1, 2, 3
```

indica que o servidor `0` está conectado aos servidores `1`, `2` e `3`.

A soma dos tamanhos das listas é `3 + 2 + 2 + 2 + 1 = 10 = 2|E|`, pois cada conexão aparece nas listas das suas duas extremidades.

O conjunto de arestas contém exatamente `5` elementos, um para cada conexão.
