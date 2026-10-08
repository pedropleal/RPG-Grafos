# Marco 4 — Implementação final e conclusão

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

> **Atenção:** o enunciado oficial do Marco 4 ainda precisa ser conferido. Este documento segue os itens já registrados para este marco (explicar as classes e os trechos implementados, as adaptações em relação às referências, os testes e a conclusão) e deve ser revisado quando o enunciado for confirmado.

## Solução consolidada

A solução está em [src/Main.java](../src/Main.java), em Java 8, em um único arquivo e sem dependências externas. Ela lê as redes do UVA 796 até EOF e, para cada rede, **ignora temporariamente cada aresta e reconta as componentes conexas com DFS**. Uma aresta é ponte quando a contagem aumenta. Uma rede com n=0 gera `0 critical links` e não encerra a leitura.

O grafo dos Marcos 1–3 permanece inalterado: 0–1–3 com o triângulo 3–2–4–3. O programa encontra (0,1) e (1,3), conforme os rastreamentos manuais do [Marco 3](marco-3.md).

Essa estratégia substituiu a DFS com `disc`/`low` planejada inicialmente no Marco 3, por sugestão do professor. Uma versão com `disc`/`low` e pilha explícita chegou a ser implementada (commit `04f7a3e`) e está preservada no histórico do Git, mas não é a versão final.

## Códigos implementados

Toda a solução está no arquivo `Main.java`, na classe `Main`.

| Membro | Responsabilidade no código |
|---|---|
| `adj` | Lista de adjacência: vetor de `ArrayList<Integer>` |
| `marked` | Vértices já visitados na contagem atual |
| `count` | Quantidade de componentes conexas na contagem atual |
| `dfs(v, a, b)` | DFS recursiva a partir de `v`, ignorando a aresta `(a, b)` nos dois sentidos |
| `contarComponentes(n, a, b)` | Reinicia `marked`, inicia uma DFS em cada vértice não marcado e retorna `count` |
| `main` | Ler cada caso, montar o grafo e o conjunto de arestas, testar cada aresta e imprimir o resultado |

## Trechos do código e funcionamento

Os trechos desta seção foram extraídos de [Main.java](../src/Main.java).

### 1. DFS ignorando uma aresta

```java
static void dfs(int v, int a, int b) {
    marked[v] = true;

    for (int w : adj[v]) {

        // Ignora temporariamente a aresta (a,b)
        if ((v == a && w == b) ||
            (v == b && w == a)) {
            continue;
        }

        if (!marked[w]) {
            dfs(w, a, b);
        }
    }
}
```

É a DFS de `CC`, com dois parâmetros a mais. A aresta `(a, b)` não é removida do grafo: ela é apenas pulada durante esta busca, nos dois sentidos. Nas demais buscas, ela volta a ser considerada.

### 2. Contar componentes

```java
static int contarComponentes(int n, int a, int b) {
    marked = new boolean[n];
    count = 0;

    for (int v = 0; v < n; v++) {
        if (!marked[v]) {
            dfs(v, a, b);
            count++;
        }
    }

    return count;
}
```

É o laço do construtor de `CC`: cada DFS iniciada em um vértice ainda não marcado corresponde a uma nova componente. Como `marked` é recriado a cada chamada, cada contagem é independente.

### 3. Ler a entrada e montar o conjunto de arestas

```java
for (int i = 0; i < n; i++) {
    int v = sc.nextInt();
    String grau = sc.next();
    int k = Integer.parseInt(
        grau.replace("(", "").replace(")", "")
    );

    for (int j = 0; j < k; j++) {
        int w = sc.nextInt();
        adj[v].add(w);

        int a = Math.min(v, w);
        int b = Math.max(v, w);
        arestas.add(a + " " + b);
    }
}
```

O texto `(k)` tem os parênteses removidos. Cada vizinho é adicionado apenas à lista da própria linha, pois a entrada já traz a conexão nas duas extremidades. Cada aresta é normalizada como `"a b"` com `a < b`, e o `TreeSet` descarta a segunda ocorrência.

O `TreeSet` usa um comparador que converte os dois números e compara primeiro `a` e depois `b`. Assim, as arestas são percorridas em ordem numérica crescente, e não em ordem de texto.

### 4. Testar cada aresta

```java
int original = contarComponentes(n, -1, -1);
ArrayList<String> pontes = new ArrayList<>();

for (String aresta : arestas) {
    String[] p = aresta.split(" ");
    int a = Integer.parseInt(p[0]);
    int b = Integer.parseInt(p[1]);

    int depois = contarComponentes(n, a, b);

    if (depois > original) {
        pontes.add(a + " - " + b);
    }
}
```

A primeira contagem usa `-1, -1`, valores que não correspondem a nenhum vértice; por isso nenhuma aresta é ignorada. Em seguida, cada aresta é testada pela definição ω(G − e) > ω(G). Como o conjunto já está ordenado e cada par tem `a < b`, as pontes entram em `pontes` na ordem exigida pela saída.

### 5. Imprimir o resultado

```java
System.out.println(pontes.size() + " critical links");

for (String ponte : pontes) {
    System.out.println(ponte);
}

System.out.println();
```

A última linha imprime a linha em branco exigida ao final de cada caso.

## Reutilização e alterações em relação às referências

Não houve importação da biblioteca algs4 nem cópia integral de suas classes. A estratégia se baseia principalmente na lógica de DFS e contagem de componentes da classe `CC.java`, disponibilizada pelo professor Ricardo Carubbi (material A4_Conectividade).

| Referência da disciplina | Elementos aproveitados | Implementação final e justificativa |
|---|---|---|
| CC, A4 | `marked[]`, DFS recursiva, laço sobre todos os vértices e contador `count` | `dfs` e `contarComponentes` mantêm essa lógica. A DFS recebe `a` e `b` para ignorar a aresta testada. Não são mantidos `id[]` e `size[]`, pois basta a quantidade de componentes. |
| Graph | Grafo não direcionado e listas de adjacência | Substituído por um vetor de `ArrayList<Integer>`, preenchido diretamente a partir das linhas da entrada. |
| DepthFirstPaths, A3 | Exploração em profundidade e marcação de visitados | A busca usada é a mesma DFS recursiva com `marked[]`. O registro de pais (`edgeTo`) não é necessário. |
| Sem classe equivalente | Leitura e formatação próprias do UVA | `main` interpreta `u (k) ...`, normaliza e ordena as arestas com `TreeSet` e formata a saída. |

`Scanner`, `ArrayList`, `TreeSet`, `Integer.parseInt`, `Integer.compare` e `Math.min`/`Math.max` são recursos da biblioteca padrão do Java. Nenhum deles calcula pontes ou resolve automaticamente o problema: a busca e o critério de ponte estão explícitos no código.

## Execução e validação

Na pasta `T2`, com um JDK instalado:

```text
javac -encoding UTF-8 -d build src/Main.java
```

Em bash ou cmd:

```text
java -cp build Main < dados/entrada.txt
```

Em PowerShell:

```powershell
Get-Content dados/entrada.txt | java -cp build Main
```

Para repetir os testes, com Python 3:

```text
python testes/validar.py --java CAMINHO/java --javac CAMINHO/javac
```

Resultados com Eclipse Temurin **1.8.0_504** (detalhes em [testes/resultado-local.md](../testes/resultado-local.md)):

| Verificação | Resultado |
|---|---|
| Catálogo do grupo, 6 redes (`entrada.txt` × `saida-esperada.txt`) | ✅ idêntico |
| Catálogo comentado, 11 casos (`casos-de-teste.txt`) | ✅ 11/11 |
| Amostra oficial UVA | ✅ idêntico |
| Entrada vazia | ✅ nenhuma saída |
| 1.100 grafos simples exaustivos, 0 a 5 vértices | ✅ concordância com o oráculo |
| 300 grafos aleatórios, semente 796 | ✅ concordância com o oráculo |

As comparações verificam o texto completo, inclusive a linha em branco de cada caso.

Com `-Xlint:all`, o `javac` emite apenas os avisos `rawtypes` e `unchecked` na linha `adj = new ArrayList[n];`, comuns ao criar vetores de listas genéricas, sem efeito na execução.

### Limitações conhecidas

- **Arestas paralelas:** cada par é guardado uma única vez; ignorar o par remove todas as suas ocorrências. Com uma conexão `0–1` duplicada, a versão final aponta `0 - 1` como ponte. O modelo adotado nos marcos é de grafo simples.
- **Recursão profunda:** a DFS é recursiva. Em um caminho com 100.000 vértices, a execução local gerou `StackOverflowError`.

Essas limitações não impediram o Accepted no UVA 796.

## Complexidade

Com V vértices e E arestas:

| Etapa ou estrutura | Custo |
|---|---|
| Leitura e montagem do conjunto de arestas (`TreeSet`) | O(E log E) tempo |
| Uma contagem de componentes por DFS | O(V+E) tempo |
| Contagens: original + uma por aresta | O((E+1)·(V+E)) tempo |
| **Tempo total** | **O(E·(V+E))** |
| Lista de adjacência | O(V+E) memória |
| `marked[]` e pilha de recursão | O(V) memória |
| Conjunto de arestas e lista de pontes | O(E) memória |
| **Memória total** | **O(V+E)** |

A abordagem com `disc`/`low` do Marco 3 custaria O(V+E+B log B), com B pontes. A estratégia adotada é mais custosa, mas é mais simples, reaproveita diretamente `CC` e foi suficiente para o problema.

## Resultado no juiz

| Item | Valor |
|---|---|
| Plataforma | VJudge |
| Problema | UVA 796 — Critical Links |
| Linguagem | Java 8 (Java 1.8.0) |
| Veredito | **Accepted** |
| Tempo | 990 ms |
| RunID remoto | 31332405 |

> **Pendente:** conferir na submissão do VJudge se o código enviado no RunID 31332405 é exatamente o de `src/Main.java`, e adicionar o print do resultado em `../evidencias/`.

## Conclusão

O problema Critical Links foi modelado como a busca de **pontes** em um grafo não direcionado, não ponderado e possivelmente desconexo.

A primeira estratégia, registrada no Marco 3, foi a DFS com `disc` e `low`. Por sugestão do professor, ela foi substituída por uma estratégia baseada diretamente na definição de ponte: ignorar cada aresta e verificar, com a DFS e a contagem de componentes de `CC`, se o número de componentes conexas aumenta.

A implementação final passou em todas as verificações locais com Java 8 e recebeu **Accepted** no UVA 796, com 990 ms. O grupo deverá revisar o código e conseguir explicar a DFS, a contagem de componentes e o critério ω(G − e) > ω(G).

## Referências

- Ricardo Carubbi: A2_Tipos_Representação_Computacional_, A3_BFS_DFS e A4_Conectividade.
- [Graph](https://algs4.cs.princeton.edu/41graph/Graph.java.html), [DepthFirstPaths](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html) e [CC](https://algs4.cs.princeton.edu/41graph/CC.java.html), Sedgewick e Wayne.
- [Enunciado oficial UVA 796](https://onlinejudge.org/external/7/796.pdf).
