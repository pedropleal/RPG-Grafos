# T2 — Critical Links (UVA 796)

## Integrantes

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Problema

O problema escolhido para o T2 foi o **UVA 796 — Critical Links**.

Uma rede é formada por `n` servidores e por conexões entre eles. Uma conexão é **crítica** quando a sua remoção desconecta servidores que antes conseguiam se comunicar.

O objetivo é encontrar todas as conexões críticas da rede. Em termos de grafos, isso significa encontrar todas as **pontes** de um grafo não direcionado.

## Linguagem

A linguagem utilizada é **Java 8**.

## Entrada

A entrada contém vários casos de teste e termina no fim do arquivo (EOF).

Cada caso começa com o número de servidores `n`. Em seguida, vêm `n` linhas no formato:

```text
u (k) v1 v2 ... vk
```

onde `u` é o servidor, `k` é a quantidade de conexões dele e `v1 ... vk` são os servidores conectados a `u`. Os servidores são numerados de `0` a `n - 1`.

## Saída

Para cada caso, o programa deve imprimir:

```text
X critical links
a - b
...
```

- `X` é a quantidade de conexões críticas;
- cada conexão aparece com o menor vértice primeiro (`a < b`);
- as conexões aparecem em ordem crescente;
- cada caso termina com uma linha em branco.

## Como executar

O código principal está em:

```text
T2/src/Main.java
```

Esse é o código submetido ao VJudge que recebeu Accepted.

A partir da pasta `T2/src`, para compilar:

```bash
javac Main.java
```

Para executar:

```bash
java Main
```

Para executar com um arquivo de entrada:

```bash
java Main < entrada.txt
```

Exemplo de entrada:

```text
8
0 (1) 1
1 (3) 2 0 3
2 (2) 1 3
3 (3) 1 2 4
4 (1) 3
7 (1) 6
6 (1) 7
5 (0)

0
```

Saída esperada:

```text
3 critical links
0 - 1
3 - 4
6 - 7

0 critical links

```

Outros casos de teste estão em [`dados/casos-de-teste.txt`](dados/casos-de-teste.txt).

## Modelagem

A rede foi modelada como um grafo:

- **Vértices:** servidores;
- **Arestas:** conexões entre os servidores;
- **Conexão crítica:** ponte do grafo.

O grafo é:

- não direcionado, porque as conexões funcionam nos dois sentidos;
- não ponderado, porque as conexões não têm custo associado;
- possivelmente desconexo, porque a rede pode ter servidores ou grupos isolados.

## Estratégia adotada

A estratégia final se baseia na definição de ponte:

> Uma aresta é ponte quando a sua remoção aumenta o número de componentes conexas do grafo.

O algoritmo:

1. conta as componentes conexas do grafo original com DFS;
2. para cada aresta `(a, b)`:
   1. ignora temporariamente a aresta;
   2. reconta as componentes conexas com DFS;
   3. se a quantidade aumentou, a aresta é uma ponte;
3. ordena as pontes encontradas e imprime o resultado.

## Implementação de referência

A estratégia se baseia principalmente na lógica de DFS e contagem de componentes da classe `CC.java` do algs4, disponibilizada pelo professor Ricardo Carubbi.

Da `CC.java` foram reaproveitados:

- o vetor `marked[]`, que indica os vértices já visitados;
- a DFS recursiva que marca todos os vértices alcançáveis;
- o laço sobre todos os vértices que inicia uma nova DFS em cada vértice não marcado e incrementa `count`.

Adaptações feitas no `Main.java`:

- a DFS recebe os parâmetros `a` e `b` e ignora a aresta `(a, b)` nos dois sentidos;
- a contagem original usa `a = -1` e `b = -1`, para não ignorar nenhuma aresta;
- não são usados `id[]` e `size[]`, pois basta a quantidade de componentes;
- o grafo é um vetor de `ArrayList<Integer>` em vez da classe `Graph` do algs4;
- as arestas são guardadas uma única vez em um `TreeSet`, como `"a b"` com `a < b`, com comparador numérico. Assim, as pontes já são encontradas na ordem exigida pela saída;
- a leitura segue o formato `u (k) v1 ... vk` do problema.

## Evolução da solução

A primeira abordagem estudada foi a DFS com os vetores `disc[]` e `low[]`, apresentada no Marco 3.

Por sugestão do professor, essa abordagem foi substituída por uma mais simples: ignorar cada aresta e recontar as componentes conexas. Essa estratégia reaproveita diretamente a DFS e a contagem de componentes já estudadas, sem exigir os conceitos adicionais de tempo de descoberta e *low-link*.

O registro da abordagem inicial foi mantido em [`acompanhamento/marco-3.md`](acompanhamento/marco-3.md).

## Complexidade

Com lista de adjacência, cada contagem de componentes por DFS custa:

```text
O(V + E)
```

Como a contagem é repetida uma vez para cada aresta, a complexidade total de tempo é:

```text
O(E · (V + E))
```

onde:

- `V` é a quantidade de vértices (servidores);
- `E` é a quantidade de arestas (conexões).

A abordagem com `disc[]` e `low[]` resolveria o problema em `O(V + E)`, mas a estratégia adotada foi suficiente para as restrições do problema, como mostra o resultado abaixo.

## Testes

Os casos de teste estão em [`dados/casos-de-teste.txt`](dados/casos-de-teste.txt) e foram executados localmente com o `Main.java`:

- compilação: `javac 1.8.0_504` (Eclipse Temurin);
- execução: `java 1.8.0_504` (Eclipse Temurin).

As saídas foram comparadas exatamente com as esperadas, incluindo a linha em branco ao final de cada caso. As quebras de linha `\r\n` geradas no Windows foram convertidas para `\n` antes da comparação.

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

A compilação gera apenas um aviso de conversão não verificada em `adj = new ArrayList[n];`, comum ao criar vetores de listas genéricas em Java, sem efeito na execução.

## Resultado

| Item | Valor |
|---|---|
| Plataforma | VJudge |
| Problema | UVA 796 — Critical Links |
| Linguagem | Java 1.8.0 |
| Veredito | **Accepted** |
| Tempo | 990 ms |
| RunID remoto | 31332405 |

> **Pendente:** adicionar o print em `evidencias/`.

## Estrutura

```text
T2/
├── README.md
├── acompanhamento/
│   ├── marco-1.md
│   ├── marco-2.md
│   ├── marco-3.md
│   └── marco-4.md
├── apresentacao/        (pendente: apresentação do T2)
├── dados/
│   └── casos-de-teste.txt
├── evidencias/          (pendente: print do Accepted)
└── src/
    └── Main.java
```
