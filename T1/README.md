\# T1 — Message Route



\## Problema



O problema escolhido para o T1 foi o \*\*Message Route\*\*, da plataforma CSES.



O problema apresenta uma rede formada por `n` computadores e `m` conexões. O objetivo é encontrar uma rota entre o computador `1` e o computador `n` utilizando o menor número possível de computadores.



Caso exista uma rota, o programa deve informar a quantidade de computadores presentes no caminho e a sequência dos computadores percorridos.



Caso não exista uma rota, deve ser exibido:



```text

IMPOSSIBLE

```



\## Integrantes



\- Pedro Pinheiro Barros Leal

\- Emmanuel Rosendo Parente Dias

&#x20;



\## Linguagem



A linguagem utilizada para a implementação do trabalho é \*\*Java\*\*.



\## Como executar



O código principal está localizado em:



```text

T1/src/Main.java

```



Para compilar:



```bash

javac Main.java

```



Para executar:



```bash

java Main

```



A entrada deve ser fornecida pelo terminal seguindo o formato definido pelo problema.



Exemplo:



```text

5 5

1 2

1 3

1 4

2 3

5 4

```



Saída esperada:



```text

3

1 4 5

```



\## Modelagem



A rede de computadores foi modelada como um grafo.



\- \*\*Vértices:\*\* representam os computadores.

\- \*\*Arestas:\*\* representam as conexões entre os computadores.

\- \*\*Origem:\*\* computador `1`.

\- \*\*Destino:\*\* computador `n`.



O grafo é:



\- simples;

\- não direcionado;

\- não ponderado.



Ele é não direcionado porque as conexões podem ser percorridas nos dois sentidos.



Ele é não ponderado porque as conexões não possuem pesos ou custos associados.



\## Representação do grafo



Foi utilizada uma \*\*lista de adjacência\*\*.



Para a instância:



```text

5 5

1 2

1 3

1 4

2 3

5 4

```



a representação é:



```text

1 -> 2, 3, 4

2 -> 1, 3

3 -> 1, 2

4 -> 1, 5

5 -> 4

```



A lista de adjacência foi escolhida porque permite armazenar apenas as conexões existentes e realizar de forma eficiente a exploração dos vizinhos de cada vértice.



\## Algoritmo escolhido



O algoritmo escolhido para a solução final foi a \*\*BFS (Breadth-First Search / Busca em Largura)\*\*.



A BFS percorre o grafo por níveis a partir do vértice de origem.



Na instância utilizada:



```text

Nível 0: 1

Nível 1: 2, 3, 4

Nível 2: 5

```



Como o grafo é não ponderado, a BFS permite encontrar um caminho mínimo em número de arestas.



O menor caminho da instância é:



```text

1 -> 4 -> 5

```



Esse caminho possui `2` arestas e `3` computadores.



\## DFS e BFS



Durante o desenvolvimento do trabalho também foi estudada a \*\*DFS (Depth-First Search / Busca em Profundidade)\*\*.



A DFS permite verificar alcançabilidade e encontrar caminhos, porém não garante que o caminho encontrado seja o menor.



A BFS foi escolhida para a solução final porque percorre o grafo por níveis e garante o menor caminho em número de arestas em um grafo não ponderado.



\## Implementação de referência



Foram estudadas implementações de referência de algoritmos de busca em grafos, incluindo DFS e BFS.



Na BFS, os principais elementos utilizados são:



```text

marked\[]  -> indica os vértices já visitados

edgeTo\[]  -> registra os predecessores

distTo\[]  -> registra as distâncias

fila      -> controla a ordem de processamento

```



A estrutura `edgeTo\[]` permite reconstruir o caminho após o destino ser encontrado.



\## Alterações realizadas



A implementação de referência foi adaptada para atender ao formato do problema Message Route.



As principais adaptações foram:



\- leitura de `n` computadores e `m` conexões;

\- utilização de vértices numerados de `1` até `n`;

\- construção de um grafo não direcionado;

\- utilização do computador `1` como origem;

\- utilização do computador `n` como destino;

\- execução da BFS;

\- armazenamento dos predecessores;

\- reconstrução do menor caminho;

\- impressão da quantidade de computadores do caminho;

\- impressão de `IMPOSSIBLE` quando o destino não é alcançável.



\## Justificativas



A lista de adjacência foi escolhida por permitir uma representação eficiente para as restrições do problema.



A BFS foi escolhida porque o objetivo é encontrar o menor caminho entre dois vértices em um grafo não ponderado.



Durante a BFS, quando um vértice `w` é descoberto a partir de um vértice `v`, podemos registrar:



```text

edgeTo\[w] = v

distTo\[w] = distTo\[v] + 1

```



Dessa forma, `edgeTo\[]` permite reconstruir o caminho e `distTo\[]` representa a distância da origem.



\## Complexidade



Utilizando BFS com lista de adjacência, a complexidade de tempo é:



```text

O(V + E)

```



onde:



\- `V` é o número de vértices;

\- `E` é o número de arestas.



No problema:



```text

V = n

E = m

```



Portanto, a complexidade de tempo é:



```text

O(n + m)

```



O espaço extra utilizado pela BFS é:



```text

O(V)

```



devido à fila e aos vetores auxiliares.



Considerando também o armazenamento do grafo através da lista de adjacência, o espaço total é:



```text

O(V + E)

```



\## Testes



Os casos de teste utilizados no desenvolvimento estão armazenados em:



```text

T1/dados/casos-de-teste.txt

```



Entre os casos considerados estão:



\- existência de caminho entre `1` e `n`;

\- inexistência de caminho;

\- caminhos com diferentes comprimentos;

\- casos pequenos para validação manual.



Exemplo principal:



```text

Entrada:

5 5

1 2

1 3

1 4

2 3

5 4



Saída:

3

1 4 5

```



\## Acompanhamento



O desenvolvimento do trabalho foi dividido em quatro marcos:



\- \[Marco 1 — Modelagem](acompanhamento/marco-1.md)

\- \[Marco 2 — Representação Computacional](acompanhamento/marco-2.md)

\- \[Marco 3 — Aplicação Básica de DFS](acompanhamento/marco-3.md)

\- \[Marco 4 — Aplicação Básica de BFS e Conclusão](acompanhamento/marco-4.md)



## Evidência do Accepted

A solução foi submetida ao problema **Message Route** na plataforma CSES e obteve o resultado **Accepted**.

A evidência da submissão está disponível em:

`T1/evidencias/accepted.png`


\## Uso de Inteligência Artificial



Durante o desenvolvimento do trabalho, ferramentas de Inteligência Artificial foram utilizadas como apoio para organização da documentação, revisão de conceitos e esclarecimento de dúvidas.



As decisões de modelagem, implementação, testes e validação da solução foram acompanhadas e revisadas pelos integrantes do trabalho.



\## Estrutura do T1



```text

T1/

├── README.md

├── acompanhamento/

│   ├── marco-1.md

│   ├── marco-2.md

│   ├── marco-3.md

│   └── marco-4.md

├── src/

│   └── Main.java

├── dados/

│   └── casos-de-teste.txt

├── evidencias/

│   └── accepted.png

└── apresentacao/

&#x20;   └── apresentacao.pdf

```

