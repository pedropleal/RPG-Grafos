# Marco 4 — Implementação final e conclusão

## Solução consolidada

A solução está em [src/Main.java](../src/Main.java), em Java, sem dependências externas. Ela lê as redes do UVA 796 até EOF, executa a DFS em todas as componentes e imprime as pontes ordenadas. Uma rede com n=0 gera `0 critical links` e não encerra a leitura.

O grafo dos Marcos 1–3 permanece inalterado: 0–1–3 com o triângulo 3–2–4–3. O programa encontra (0,1) e (1,3), conforme o rastreamento manual.

## Reutilização e alterações em relação às referências

Não houve importação da biblioteca algs4 nem cópia integral de suas classes. Foram reutilizadas as ideias e estruturas das implementações da disciplina, reescritas em classes internas de `Main` para permitir a submissão em um único arquivo.

| Referência da disciplina | Elementos aproveitados | Implementação final e justificativa |
|---|---|---|
| Graph | Grafo não direcionado e listas de adjacência | `Main.Graph` usa listas da biblioteca padrão. Cada `Edge` contém vizinho e identidade, permitindo ignorar exatamente a aresta de chegada. Métodos não necessários ao problema foram omitidos. |
| DepthFirstPaths, A3 | Marcação de visitados, registro de pais e exploração em profundidade | `BridgeFinder` mantém `marked` e `parent`, equivalente ao papel de `edgeTo`. Acrescenta `disc`, `low`, contador e lista de pontes. A reconstrução de caminhos não é necessária. |
| CC, A4 | Laço sobre todos os vértices com nova DFS nos não marcados | O construtor de `BridgeFinder` aplica esse laço. Não mantém identificadores, tamanhos ou contagem de componentes, pois a saída solicita arestas críticas. |
| Sem classe equivalente reutilizada | Leitura e formatação próprias do UVA | `FastInput` lê inteiros e ignora separadores, inclusive parênteses. `main` constrói cada rede, executa a busca e formata a saída. |

`ArrayList`, `Arrays.fill`, `Math.min`, `Integer.compare`, `BufferedInputStream` e a ordenação de listas são recursos da biblioteca padrão do Java. Nenhum deles calcula pontes ou resolve automaticamente o problema. A busca e o critério estrutural estão explícitos no código.

## Alteração da recursão planejada

O Marco 3 previa DFS recursiva. A versão final simula as chamadas com `stack[]` e guarda em `nextNeighbor[]` o próximo vizinho a examinar. Isso preserva a exploração em profundidade e o momento do retorno, sem depender da profundidade da pilha de chamadas do Java.

Ao descobrir um filho, ele é empilhado e recebe seus tempos. Quando todos os vizinhos de v foram examinados, v é desempilhado: esse evento corresponde ao retorno de `dfs(v)`. Somente nesse momento seu low final é propagado ao pai e a aresta-pai é testada. O teste com um caminho de 100.000 vértices verificou esse comportamento sem recursão.

Essa é uma alteração da implementação planejada, não uma mudança de algoritmo ou do grafo de exemplo. Os Marcos 1–3 permanecem como registro do planejamento.

## Busca e critério estrutural

1. Na primeira visita, marcar v e atribuir `disc[v] = low[v] = time++`.
2. Para uma aresta de v a w ainda não visitado, registrar o pai e a identidade da aresta e explorar w.
3. Para um vizinho marcado, ignorando apenas a aresta-pai, atualizar `low[v] = min(low[v], disc[w])`.
4. Ao finalizar um filho v de p, atualizar `low[p] = min(low[p], low[v])`.
5. Se `low[v] > disc[p]`, registrar a ponte com seus extremos normalizados.

Se a subárvore do filho alcança o pai ou algum ancestral por uma aresta de retorno, existe caminho alternativo. Se não alcança, remover a aresta-pai separa essa subárvore. Por isso o teste usa `>` e não `>=`. A raiz segue o mesmo teste para pontes, sem a regra especial de raiz usada para vértices de articulação.

No exemplo, a ordem de descoberta é 0,1,3,2,4. A conexão de 4 a 3 reduz low[4] a 2, e o retorno propaga esse valor para 2. Em ordem de índice 0,1,2,3,4:

```text
parent = [-1, 0, 3, 1, 2]
disc   = [ 0, 1, 3, 2, 4]
low    = [ 0, 1, 2, 2, 2]
```

Assim, 2>1 identifica (1,3), e 1>0 identifica (0,1). As arestas do triângulo não são pontes.

## Adaptações de entrada e saída

- As linhas são associadas ao identificador lido, mesmo fora de ordem.
- A entrada é simétrica: cada conexão aparece nos dois extremos. Inserir apenas quando u<v e adicionar os dois sentidos em `Graph` evita duplicação.
- Cada aresta recebe identidade própria. Isso também trata corretamente ocorrências paralelas simétricas, embora a modelagem didática seja simples.
- Cada caso cria um grafo e um `BridgeFinder` novos, reinicializando o estado.
- `FastInput` pressupõe a entrada válida do enunciado: inteiros não negativos separados por espaços e parênteses. Não é um validador de entradas malformadas.
- As pontes são ordenadas pelo primeiro e depois pelo segundo extremo. A saída contém uma linha em branco ao final de cada caso.

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

Para repetir os testes, com Python 3 e JDK 9 ou posterior no PATH:

```text
python testes/validar.py
```

O script compila com `--release 8`, compara saídas exatas e usa um oráculo independente: remover cada aresta e verificar se aumenta o número de componentes. O oráculo serve apenas aos testes, não integra a solução.

Passaram as seis redes do catálogo, as duas redes da amostra oficial, entrada vazia, todos os 1.100 grafos simples rotulados de 0 a 5 vértices, 300 grafos aleatórios com semente 796, um caso de arestas paralelas e um caminho com 100.000 vértices. Veja o [registro local](../testes/resultado-local.md).

## Complexidade

Com V vértices, E arestas e B pontes, a construção e a busca custam O(V+E). `nextNeighbor` garante que cada entrada da lista seja examinada uma vez. A ordenação custa O(B log B), resultando em O(V+E+B log B). Grafo, vetores, pilha explícita e saída usam O(V+E) de memória por caso.

## Apresentação e conclusão

A apresentação está disponível em [PowerPoint editável](../apresentacao/apresentacao.pptx) e [PDF](../apresentacao/apresentacao.pdf). Ela cobre modelagem, referências, adaptações, rastreamento, validação e complexidade.

A implementação e a validação local estão concluídas. O grupo deverá revisar o código e conseguir explicar a busca, a simulação do retorno e a desigualdade estrita. A submissão ao UVA e a evidência de Accepted permanecem pendentes; os testes locais não substituem o julgamento da plataforma.

## Referências

- Ricardo Carubbi: A2_Tipos_Representação_Computacional_, A3_BFS_DFS e A4_Conectividade.
- [Graph](https://algs4.cs.princeton.edu/41graph/Graph.java.html), [DepthFirstPaths](https://algs4.cs.princeton.edu/41graph/DepthFirstPaths.java.html) e [CC](https://algs4.cs.princeton.edu/41graph/CC.java.html), Sedgewick e Wayne.
- [Enunciado oficial UVA 796](https://onlinejudge.org/external/7/796.pdf).
