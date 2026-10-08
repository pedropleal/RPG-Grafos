# T2 — UVA 796: Critical Links

## Problema e situação do trabalho

Identificar as pontes de uma rede não direcionada: arestas cuja remoção aumenta o número de componentes conexas. O trabalho mantém o exemplo do grupo, `0–1–3` com o triângulo `3–2–4–3`.

A estratégia final **ignora temporariamente cada aresta e reconta as componentes conexas com DFS**, seguindo a lógica da classe `CC` do algs4. A DFS com `disc`/`low`, estudada inicialmente no Marco 3, foi substituída por sugestão do professor.

A solução recebeu **Accepted** no UVA 796 pelo VJudge (RunID 31332405, Java 8, 990 ms). Ainda é preciso conferir na submissão se o código enviado é exatamente o de [`src/Main.java`](src/Main.java) e adicionar o print do resultado.

## Integrantes

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Linguagem

**Java 8**, mantendo a linguagem do T1 e as implementações de referência disponibilizadas na disciplina, principalmente `CC` (material A4_Conectividade). `Graph` e `DepthFirstPaths` (material A3_BFS_DFS) são as referências para a representação e para a DFS.

## Execução

A solução está em [src/Main.java](src/Main.java), em um único arquivo e sem dependências externas. Requer um JDK. A partir da pasta `T2`, compilar:

```text
javac -encoding UTF-8 -d build src/Main.java
```

Em bash ou cmd, executar:

```text
java -cp build Main < dados/entrada.txt
```

Em PowerShell:

```powershell
Get-Content dados/entrada.txt | java -cp build Main
```

O programa lê casos até EOF e escreve na saída padrão. [entrada.txt](dados/entrada.txt) contém somente dados de entrada; [saida-esperada.txt](dados/saida-esperada.txt) contém o resultado completo. O catálogo comentado está em [casos-de-teste.txt](dados/casos-de-teste.txt).

Para repetir os testes com Python 3: `python testes/validar.py`. Se o JDK não estiver no PATH, informar `--java CAMINHO_JAVA --javac CAMINHO_JAVAC`. O script funciona com o JDK 8 e com JDKs mais novos (neste caso, compila com `--release 8`). Python serve somente como ferramenta de teste.

## Modelagem

Os servidores são vértices numerados de 0 a n−1, e as conexões bidirecionais são arestas. O grafo é não direcionado, não ponderado e pode ter várias componentes conexas. A representação é uma lista de adjacência.

Exemplo do grupo:

```text
        0
        |
        1
        |
        3
       / \
      2---4

V = {0, 1, 2, 3, 4}
E = {(0,1), (1,3), (2,3), (3,4), (2,4)}
```

## Propriedade estrutural

Uma aresta e é ponte quando sua remoção aumenta o número de componentes conexas: **ω(G − e) > ω(G)**. No exemplo, as pontes são (0,1) e (1,3). As arestas do triângulo não são pontes porque têm caminhos alternativos.

A solução final testa essa definição diretamente, aresta por aresta.

## Algoritmo

1. Contar as componentes conexas do grafo original com DFS: `original = contarComponentes(n, -1, -1)`.
2. Para cada aresta `(a, b)`, em ordem crescente:
   1. contar novamente as componentes, com a DFS ignorando `(a, b)` nos dois sentidos;
   2. se a contagem for maior que `original`, `(a, b)` é ponte.
3. Imprimir a quantidade de pontes, cada ponte no formato `a - b` e uma linha em branco.

As arestas ficam em um `TreeSet`, normalizadas com o menor extremo primeiro e comparadas numericamente. Por isso as pontes já são encontradas na ordem exigida pela saída.

O [Marco 3](acompanhamento/marco-3.md) registra o rastreamento manual do exemplo. O [Marco 4](acompanhamento/marco-4.md) explica os trechos do código.

## Implementação de referência

| Referência | Uso na adaptação |
|---|---|
| CC — A4 | DFS recursiva com `marked[]`, laço sobre todos os vértices e contador `count` |
| Graph | Representação por adjacência e acesso aos vizinhos |
| DepthFirstPaths — A3 | Exploração em profundidade e marcação de visitados |

Essas classes orientaram a adaptação, sem cópia integral nem importação de algs4. A estratégia se baseia principalmente na lógica de DFS e contagem de componentes da classe `CC.java`, disponibilizada pelo professor Ricardo Carubbi.

A solução **não usa bibliotecas externas nem funções que resolvam automaticamente o problema**. As coleções, a leitura e a ordenação usam apenas a biblioteca padrão de Java. O código implementa explicitamente a busca e o teste de ponte.

## Alterações em relação às referências

A adaptação está detalhada no [Marco 4](acompanhamento/marco-4.md) e inclui:

- a DFS recebe os parâmetros `a` e `b` e ignora a aresta `(a, b)` nos dois sentidos;
- a contagem original usa `a = -1` e `b = -1`, para não ignorar nenhuma aresta;
- não são usados `id[]` e `size[]` de `CC`, pois basta a quantidade de componentes;
- o grafo é um vetor de `ArrayList<Integer>`, em vez da classe `Graph`;
- interpretar linhas `u (k) ...`, processar múltiplos casos e reinicializar o estado a cada caso;
- guardar cada aresta uma única vez em um `TreeSet`, com comparador numérico;
- repetir a contagem de componentes para cada aresta e imprimir o formato exigido.

## Evolução da solução

A primeira estratégia, apresentada no [Marco 3](acompanhamento/marco-3.md), foi a DFS com `disc[]` e `low[]`, que detecta as pontes em uma única passagem pelo grafo pelo critério `low[w] > disc[v]`. Uma versão com essa estratégia e pilha explícita chegou a ser implementada (commit `04f7a3e`) e está preservada no histórico do Git.

Por sugestão do professor, ela foi substituída por uma estratégia mais simples, baseada diretamente na definição de ponte: ignorar cada aresta e verificar se o número de componentes aumenta. Essa estratégia:

- reaproveita diretamente a DFS e a contagem de componentes de `CC`;
- não exige os conceitos de tempo de descoberta, aresta de retorno e `low`;
- tem correção que decorre diretamente da definição de ponte;
- foi suficiente para o problema, com Accepted no UVA 796.

## Justificativas

A lista de adjacência permite armazenar e percorrer apenas as conexões presentes. A DFS com contagem de componentes, já estudada em `CC`, resolve diretamente a pergunta "remover esta aresta aumenta o número de componentes?". A cobertura de todas as componentes é necessária porque a entrada pode ser desconexa.

## Complexidade

Considerando V vértices e E arestas:

| Etapa ou estrutura | Custo |
|---|---|
| Leitura e conjunto de arestas (`TreeSet`) | O(E log E) tempo |
| Uma contagem de componentes por DFS | O(V+E) tempo |
| Contagem original + uma por aresta | O((E+1)·(V+E)) tempo |
| **Tempo total** | **O(E·(V+E))** |
| Lista de adjacência | O(V+E) memória |
| `marked[]` e pilha de recursão | O(V) memória |
| Conjunto de arestas e lista de pontes | O(E) memória |
| **Memória total** | **O(V+E)** |

A abordagem com `disc`/`low` custaria O(V+E+B log B), com B pontes. A estratégia adotada é mais custosa, mas foi suficiente para o problema.

## Casos especiais e validação

Testes executados localmente com **Eclipse Temurin 1.8.0_504** (`javac` e `java`), sobre a versão final de `Main.java`. Detalhes em [testes/resultado-local.md](testes/resultado-local.md).

| Verificação | Resultado |
|---|---|
| Catálogo do grupo, 6 redes (`entrada.txt` × `saida-esperada.txt`) | ✅ idêntico |
| Catálogo comentado, 11 casos ([casos-de-teste.txt](dados/casos-de-teste.txt)) | ✅ 11/11 |
| Amostra oficial UVA | ✅ idêntico |
| Entrada vazia | ✅ nenhuma saída |
| 1.100 grafos simples exaustivos, 0 a 5 vértices | ✅ concordância com o oráculo |
| 300 grafos aleatórios, semente 796 | ✅ concordância com o oráculo |

As comparações verificam o texto completo, inclusive a linha em branco ao final de cada caso. O oráculo, usado só nos testes, foi escrito em Python de forma independente do código Java.

Cuidados verificados: n=0 não encerra a leitura; vértices isolados são considerados; a ordem das linhas não determina o identificador do vértice; as pontes saem com `a < b` e em ordem numérica; cada caso termina com uma linha em branco.

Limitações conhecidas da versão final:

- **Arestas paralelas:** cada par é guardado uma única vez. Com uma conexão `0–1` duplicada, `0 - 1` é apontada como ponte. O modelo adotado nos marcos é de grafo simples.
- **Recursão profunda:** a DFS é recursiva. Um caminho com 100.000 vértices gerou `StackOverflowError` localmente.

Essas limitações não impediram o Accepted. Na compilação com `-Xlint:all`, o `javac` emite apenas os avisos `rawtypes` e `unchecked` em `adj = new ArrayList[n];`, sem efeito na execução.

## Resultado no juiz

| Item | Valor |
|---|---|
| Plataforma | VJudge |
| Problema | UVA 796 — Critical Links |
| Linguagem | Java 8 (Java 1.8.0) |
| Veredito | **Accepted** |
| Tempo | 990 ms |
| RunID remoto | 31332405 |

**Pendências:**

- conferir na submissão do VJudge se o código do RunID 31332405 é exatamente o de [`src/Main.java`](src/Main.java);
- adicionar o print do resultado em `evidencias/`. A pasta contém apenas `.gitkeep`. A evidência do T1 refere-se a outro problema e não comprova o T2.

## Organização

```text
T2/
├── README.md
├── .gitattributes
├── .gitignore
├── acompanhamento/
│   ├── marco-1.md
│   ├── marco-2.md
│   ├── marco-3.md
│   └── marco-4.md
├── apresentacao/
│   └── .gitkeep
├── dados/
│   ├── casos-de-teste.txt
│   ├── entrada.txt
│   └── saida-esperada.txt
├── evidencias/
│   └── .gitkeep
├── src/
│   └── Main.java
└── testes/
    ├── resultado-local.md
    └── validar.py
```

As pastas `evidencias/` e `apresentacao/` permanecem reservadas para o print real do juiz e para a apresentação. Os arquivos compilados ficam em `build/`, ignorado pelo Git.

## Acompanhamento

- [Marco 1 — Modelagem](acompanhamento/marco-1.md)
- [Marco 2 — Representação computacional](acompanhamento/marco-2.md)
- [Marco 3 — Estruturas e estratégia de DFS](acompanhamento/marco-3.md): estratégia inicial com `disc`/`low` e mudança para remoção de arestas + DFS
- [Marco 4 — Implementação final e conclusão](acompanhamento/marco-4.md)
- [Casos de teste e resultados esperados](dados/casos-de-teste.txt)

## Base da revisão

Documentação consolidada a partir da conversa GRAFOS, do texto anterior do Marco 3 e dos materiais de Ricardo Carubbi: A2_Tipos_Representação_Computacional_, A3_BFS_DFS e A4_Conectividade. As decisões preservadas são lista de adjacência, DFS, cobertura de todas as componentes e as referências `Graph`, `DepthFirstPaths` e `CC`. A estratégia de pontes passou de `disc`/`low` para remoção de arestas + recontagem de componentes, por sugestão do professor.

O trabalho contou com apoio de IA para organização, testes e documentação. Os integrantes deverão revisar e compreender o código e suas adaptações.

## Referências

- [Enunciado oficial UVA 796](https://onlinejudge.org/external/7/796.pdf)
- [Graph — algs4](https://algs4.cs.princeton.edu/41graph/Graph.java.html)
- [DepthFirstPaths — algs4](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html)
- [CC — algs4](https://algs4.cs.princeton.edu/41graph/CC.java.html)
