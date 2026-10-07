# T2 — UVA 796: Critical Links

## Problema e situação do trabalho

Identificar as pontes de uma rede não direcionada: arestas cuja remoção aumenta o número de componentes conexas. O trabalho mantém o exemplo `0–1–3` com o triângulo `3–2–4–3` e a estratégia de DFS com tempos de descoberta e valores low.

Os Marcos 1, 2 e 3 registram a modelagem, a representação e a estratégia planejada. O [Marco 4](acompanhamento/marco-4.md) documenta a implementação final, suas adaptações e os testes locais. A solução em Java e a apresentação estão disponíveis. A submissão ao UVA e a evidência de Accepted permanecem pendentes.

## Integrantes

Mantidos do registro do T1 neste repositório:

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Linguagem

**Java**, mantendo a linguagem do T1 e as implementações de referência disponibilizadas na disciplina: `DepthFirstPaths` no material A3_BFS_DFS e `CC` no material A4_Conectividade. `Graph` é a referência para a representação do grafo usada por essas buscas.

## Execução

A solução está em [src/Main.java](src/Main.java), sem dependências externas. Requer um JDK. A partir da pasta `T2`, compilar:

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

O programa lê casos até EOF e escreve na saída padrão. [entrada.txt](dados/entrada.txt) contém somente dados de entrada; [saida-esperada.txt](dados/saida-esperada.txt) contém o resultado completo. O catálogo comentado continua disponível em [casos-de-teste.txt](dados/casos-de-teste.txt).

Para repetir os testes com Python 3 e JDK 9+ no PATH: `python testes/validar.py`. O script usa `--release 8`; a execução local foi realizada no JDK 25.0.1. Python serve somente como ferramenta de teste.

## Modelagem

Os servidores são vértices numerados de 0 a n−1, e as conexões bidirecionais são arestas. A representação escolhida é uma lista de adjacência para um grafo não direcionado e não ponderado, com o modelo simples adotado nos marcos. A rede pode ter várias componentes conexas.

Exemplo preservado:

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

Na árvore DFS, uma aresta de pai v para filho w é ponte quando **low[w] > disc[v]**. `disc[v]` registra a descoberta de v; `low[w]` registra o menor tempo de descoberta alcançável pela subárvore de w usando arestas da árvore seguidas de, no máximo, uma aresta de retorno. Se low[w] ≤ disc[v], existe retorno para v ou um ancestral; a igualdade não caracteriza ponte.

## Algoritmo

A implementação usa DFS com `marked`, `parent`, `disc`, `low`, contador de descobertas e lista de pontes. A pilha explícita `stack` e o índice `nextNeighbor` simulam as chamadas e os retornos da recursão prevista no Marco 3, evitando estouro da pilha de chamadas em caminhos longos. Ao finalizar um filho, seu low é propagado ao pai e o critério de ponte é verificado. Uma nova DFS começa em cada vértice ainda não visitado para cobrir todas as componentes.

Os pares encontrados são normalizados com o menor extremo primeiro e ordenados lexicograficamente. O [Marco 3](acompanhamento/marco-3.md) registra as estruturas e o rastreamento manual do exemplo.

## Implementação de referência

| Referência | Uso na adaptação |
|---|---|
| Graph | Representação por adjacência e acesso aos vizinhos |
| DepthFirstPaths — A3 | Busca recursiva, marcação e registro dos pais |
| CC — A4 | Percorrer todas as componentes iniciando buscas nos vértices não marcados |

Essas classes orientaram a adaptação conceitual, sem cópia integral nem importação de algs4. `Main.Graph` representa as adjacências; `BridgeFinder` adapta a DFS e o laço de cobertura de componentes. `FastInput` e a formatação atendem ao UVA. `disc`, `low` e o teste de ponte são a extensão para o problema, não atribuída às implementações das aulas.

A solução **não usa bibliotecas externas nem funções que resolvam automaticamente o problema**. As coleções, a leitura, a inicialização de vetores e a ordenação usam apenas a biblioteca padrão de Java. O código implementa explicitamente a busca e o teste de ponte.

O grupo deverá compreender, rastrear, adaptar e explicar tanto a busca quanto o critério estrutural. A documentação e o rastreamento manual apoiam essa preparação, mas não comprovam por si só o domínio dos integrantes.

## Alterações em relação às referências

A adaptação implementada está detalhada no [Marco 4](acompanhamento/marco-4.md) e inclui:

- interpretar linhas `u (k) ...`, processar múltiplos casos e reinicializar o estado a cada caso;
- evitar duplicar arestas ao ler as listas bidirecionais;
- acrescentar os tempos de descoberta, valores low e controle do pai à DFS;
- identificar e armazenar pontes pelo critério low[filho] > disc[pai];
- cobrir todas as componentes, ordenar os pares e imprimir o formato exigido;
- substituir a recursão por pilha explícita e registrar a identidade da aresta-pai.

## Justificativas

A lista de adjacência permite armazenar e percorrer apenas as conexões presentes. A DFS organiza a exploração em subárvores; os valores low indicam se existe um retorno que preserve a conexão quando a aresta-pai é removida. Isso permite detectar pontes em uma passagem pelo grafo, sem remover cada aresta e repetir a busca. A cobertura de todas as componentes é necessária porque a entrada pode ser desconexa.

## Complexidade

Considerando V vértices, E arestas e B pontes:

| Etapa ou estrutura | Custo |
|---|---|
| Construção das listas e DFS | O(V+E) tempo |
| Ordenação dos pares de pontes | O(B log B) tempo |
| Tempo total | O(V+E+B log B) |
| Grafo | O(V+E) memória |
| Vetores e pilha explícita | O(V) memória |
| Lista de pontes | O(B) memória |
| Memória total | O(V+E) |

São limites assintóticos da implementação. A pilha explícita ocupa até V posições e elimina a dependência da profundidade da pilha de chamadas da JVM.

## Casos especiais e validação

O [catálogo de testes](dados/casos-de-teste.txt) contém o exemplo do grupo, um ciclo sem pontes, um caminho em que todas as arestas são pontes, componentes separadas com vértice isolado e linhas fora de ordem, e uma rede vazia seguida de outro caso. A execução confirmou as saídas esperadas. Também passaram a amostra oficial, 1.100 grafos pequenos enumerados, 300 grafos aleatórios, entrada vazia, um caso com arestas paralelas e um caminho de 100.000 vértices. O [registro local](testes/resultado-local.md) informa o ambiente, o método independente de comparação e como repetir os testes.

Cuidados implementados: n=0 não encerra a leitura; vértices isolados devem ser considerados; a ordem das linhas não determina o identificador do vértice; e cada caso deve terminar com uma linha em branco na saída. A entrada bidirecional não deve gerar arestas duplicadas. A implementação distingue a identidade da aresta-pai, permitindo tratar ocorrências paralelas simétricas sem ignorar todas as conexões com o pai.

## Evidência do Accepted

**Pendente.** Não há submissão nem resultado Accepted registrado para o T2. A pasta `evidencias/` contém apenas `.gitkeep`. Uma imagem ou PDF real do resultado será acrescentado após a submissão aceita, com o respectivo link nesta seção. A evidência do T1 refere-se a outro problema e não comprova o T2.

## Organização

```text
T2/
├── README.md
├── acompanhamento/
│   ├── marco-1.md
│   ├── marco-2.md
│   ├── marco-3.md
│   └── marco-4.md
├── src/
│   └── Main.java
├── evidencias/
│   └── .gitkeep
├── apresentacao/
│   ├── apresentacao.pptx
│   └── apresentacao.pdf
├── dados/
│   ├── casos-de-teste.txt
│   ├── entrada.txt
│   └── saida-esperada.txt
└── testes/
    ├── validar.py
    └── resultado-local.md
```

A pasta `evidencias/` permanece reservada para o resultado real do juiz. A apresentação tem 10 slides, em [PowerPoint editável](apresentacao/apresentacao.pptx) e [PDF](apresentacao/apresentacao.pdf). Os arquivos compilados ficam em `build/`, ignorado pelo Git.

## Acompanhamento

- [Marco 1 — Modelagem](acompanhamento/marco-1.md)
- [Marco 2 — Representação computacional](acompanhamento/marco-2.md)
- [Marco 3 — Estruturas e estratégia de DFS](acompanhamento/marco-3.md)
- [Marco 4 — Implementação final e conclusão](acompanhamento/marco-4.md)
- [Casos de teste e resultados esperados](dados/casos-de-teste.txt): catálogo documental, acompanhado dos arquivos de entrada e saída para execução.

## Base da revisão

Documentação consolidada a partir da conversa GRAFOS, do texto anterior do Marco 3 e dos materiais de Ricardo Carubbi: A2_Tipos_Representação_Computacional_, A3_BFS_DFS e A4_Conectividade. As decisões preservadas são lista de adjacência, DFS, cobertura de todas as componentes, referências `Graph`, `DepthFirstPaths` e `CC`, e adaptação com `disc`/`low` para pontes. O Marco 4 justifica a substituição da recursão planejada por pilha explícita.

O trabalho contou com apoio de IA para organização, implementação, testes e apresentação. Os resultados registrados são de validação local, não de submissão ao juiz. Os integrantes deverão revisar e compreender o código e suas adaptações. Esta versão incorpora os itens exigidos para o README e as restrições de linguagem e implementação informados pelo grupo. A implementação e os testes locais estão concluídos. Accepted permanece pendente; não se afirma aprovação pelo juiz nem pelo professor.

## Referências

- [Enunciado oficial UVA 796](https://onlinejudge.org/external/7/796.pdf)
- [Graph — algs4](https://algs4.cs.princeton.edu/41graph/Graph.java.html)
- [DepthFirstPaths — algs4](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html)
- [CC — algs4](https://algs4.cs.princeton.edu/41graph/CC.java.html)
