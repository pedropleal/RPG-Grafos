# T2 — UVA 796: Critical Links

## Problema e situação do trabalho

Identificar as pontes de uma rede não direcionada: arestas cuja remoção aumenta o número de componentes conexas. O trabalho mantém o exemplo `0–1–3` com o triângulo `3–2–4–3` e a estratégia de DFS com tempos de descoberta e valores low.

Os Marcos 1, 2 e 3 estão organizados como documentação de modelagem, representação e estratégia. O [Marco 4](acompanhamento/marco-4.md) permanece **pendente de elaboração**. Não há implementação de T2, execução de solução, submissão Accepted ou evidência de aceite nesta entrega.

## Integrantes

Mantidos do registro do T1 neste repositório:

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

As referências de implementação são em Java, linguagem usada no T1. A implementação de T2 será elaborada posteriormente; por isso, ainda não há comandos de compilação ou execução.

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

A revisão contou com apoio de IA para organização e conferência conceitual. Os resultados dos exemplos são esperados por análise do grafo, não resultados de uma solução submetida. Os requisitos completos de avaliação dos marcos não estavam disponíveis para conferência; não se afirma aprovação pelo professor.

## Referências

- [Enunciado oficial UVA 796](https://onlinejudge.org/external/7/796.pdf)
- [Graph — algs4](https://algs4.cs.princeton.edu/41graph/Graph.java.html)
- [DepthFirstPaths — algs4](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html)
- [CC — algs4](https://algs4.cs.princeton.edu/41graph/CC.java.html)
