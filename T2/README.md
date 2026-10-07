# T2 — UVA 796: Critical Links

## Problema e situação do trabalho

Identificar as pontes de uma rede não direcionada: arestas cuja remoção aumenta o número de componentes conexas. O trabalho mantém o exemplo `0–1–3` com o triângulo `3–2–4–3` e a estratégia de DFS com tempos de descoberta e valores low.

Os Marcos 1, 2 e 3 estão organizados como documentação de modelagem, representação e estratégia. O [Marco 4](acompanhamento/marco-4.md) permanece **pendente de elaboração**. Não há implementação de T2, execução de solução, submissão Accepted ou evidência de aceite nesta entrega.

## Integrantes

Mantidos do registro do T1 neste repositório:

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Linguagem

**Java**, mantendo a linguagem do T1 e as implementações de referência disponibilizadas na disciplina: `DepthFirstPaths` no material A3_BFS_DFS e `CC` no material A4_Conectividade. `Graph` é a referência para a representação do grafo usada por essas buscas.

## Execução

**Pendente de implementação.** A pasta `src/` ainda não contém um programa executável. Os comandos de compilação e execução serão registrados e verificados quando o código real estiver disponível.

A futura solução deverá ler da entrada padrão os casos no formato do UVA 796 até o fim da entrada e escrever na saída padrão. O arquivo de casos é um catálogo com rótulos e resultados esperados; somente seus blocos de entrada deverão ser fornecidos ao programa.

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

A estratégia prevista é DFS recursiva com `marked`, `parent`/`edgeTo`, `disc`, `low`, contador de descobertas e lista de pontes. Após explorar um filho, seu valor low é propagado ao pai e o critério de ponte é verificado. Uma nova DFS começa em cada vértice ainda não visitado para cobrir todas as componentes.

Os pares encontrados serão normalizados com o menor extremo primeiro e ordenados lexicograficamente. O [Marco 3](acompanhamento/marco-3.md) registra as estruturas e o rastreamento manual do exemplo.

## Implementação de referência

| Referência | Uso na adaptação |
|---|---|
| Graph | Representação por adjacência e acesso aos vizinhos |
| DepthFirstPaths — A3 | Busca recursiva, marcação e registro dos pais |
| CC — A4 | Percorrer todas as componentes iniciando buscas nos vértices não marcados |

Essas classes são referências para estudo e adaptação; não constituem uma solução pronta para Critical Links. `disc`, `low` e o teste de ponte são a extensão prevista pelo grupo, não atribuída às implementações das aulas.

As implementações de referência da disciplina poderão ser utilizadas. **Não serão utilizadas bibliotecas externas nem funções que resolvam automaticamente o problema.** As referências do algs4 serão estudadas e adaptadas conforme o material da disciplina; não se prevê adicionar uma biblioteca externa como dependência da solução.

O grupo deverá compreender, rastrear, adaptar e explicar tanto a busca quanto o critério estrutural. A documentação e o rastreamento manual apoiam essa preparação, mas não comprovam por si só o domínio dos integrantes.

## Alterações previstas em relação às referências

Ainda não há alterações de código implementadas. A adaptação planejada inclui:

- interpretar linhas `u (k) ...`, processar múltiplos casos e reinicializar o estado a cada caso;
- evitar duplicar arestas ao ler as listas bidirecionais;
- acrescentar os tempos de descoberta, valores low e controle do pai à DFS;
- identificar e armazenar pontes pelo critério low[filho] > disc[pai];
- cobrir todas as componentes, ordenar os pares e imprimir o formato exigido.

## Justificativas

A lista de adjacência permite armazenar e percorrer apenas as conexões presentes. A DFS organiza a exploração em subárvores; os valores low indicam se existe um retorno que preserve a conexão quando a aresta-pai é removida. Isso permite detectar pontes em uma passagem pelo grafo, sem remover cada aresta e repetir a busca. A cobertura de todas as componentes é necessária porque a entrada pode ser desconexa.

## Complexidade prevista

Considerando V vértices, E arestas e B pontes:

| Etapa ou estrutura | Custo |
|---|---|
| Construção das listas e DFS | O(V+E) tempo |
| Ordenação dos pares de pontes | O(B log B) tempo |
| Tempo total | O(V+E+B log B) |
| Grafo | O(V+E) memória |
| Vetores e pilha recursiva | O(V) memória |
| Lista de pontes | O(B) memória |
| Memória total | O(V+E) |

São custos da estratégia planejada, não medições de uma implementação. A profundidade da recursão poderá chegar a V e deverá ser considerada na implementação em Java.

## Casos especiais e validação

O [catálogo de testes](dados/casos-de-teste.txt) contém o exemplo do grupo, um ciclo sem pontes, um caminho em que todas as arestas são pontes, componentes separadas com vértice isolado e linhas fora de ordem, e uma rede vazia seguida de outro caso. As saídas são esperadas por análise manual; a execução da solução está pendente.

Cuidados previstos: n=0 não encerra a leitura; vértices isolados devem ser considerados; a ordem das linhas não determina o identificador do vértice; e cada caso deve terminar com uma linha em branco na saída. A entrada bidirecional não deve gerar arestas duplicadas. Caso o modelo seja ampliado para arestas paralelas, será necessário distinguir a identidade da aresta-pai, em vez de ignorar todas as conexões com o pai.

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
│   └── .gitkeep
├── evidencias/
│   └── .gitkeep
├── apresentacao/
│   └── .gitkeep
└── dados/
    └── casos-de-teste.txt
```

Os arquivos `.gitkeep` apenas preservam as pastas no Git. `src/` aguarda o código real; `evidencias/`, uma evidência real de submissão; `apresentacao/`, a apresentação final. Não representam entregas concluídas.

## Acompanhamento

- [Marco 1 — Modelagem](acompanhamento/marco-1.md)
- [Marco 2 — Representação computacional](acompanhamento/marco-2.md)
- [Marco 3 — Estruturas e estratégia de DFS](acompanhamento/marco-3.md)
- [Marco 4 — Pendente](acompanhamento/marco-4.md)
- [Casos de teste e resultados esperados](dados/casos-de-teste.txt): catálogo documental; copiar apenas os blocos de entrada ao testar futuramente.

## Base da revisão

Documentação consolidada a partir da conversa GRAFOS, do texto anterior do Marco 3 e dos materiais de Ricardo Carubbi: A2_Tipos_Representação_Computacional_, A3_BFS_DFS e A4_Conectividade. As decisões preservadas são lista de adjacência, DFS recursiva, cobertura de todas as componentes, referências `Graph`, `DepthFirstPaths` e `CC`, e adaptação com `disc`/`low` para pontes.

A revisão contou com apoio de IA para organização e conferência conceitual. Os resultados dos exemplos são esperados por análise do grafo, não resultados de uma solução submetida. Esta versão incorpora os itens exigidos para o README e as restrições de linguagem e implementação informados pelo grupo. Execução e Accepted permanecem pendentes; não se afirma conclusão da entrega nem aprovação pelo professor.

## Referências

- [Enunciado oficial UVA 796](https://onlinejudge.org/external/7/796.pdf)
- [Graph — algs4](https://algs4.cs.princeton.edu/41graph/Graph.java.html)
- [DepthFirstPaths — algs4](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html)
- [CC — algs4](https://algs4.cs.princeton.edu/41graph/CC.java.html)
