# Validação local do T2

Execução realizada em 08/10/2026 com **Eclipse Temurin 1.8.0_504** (`javac 1.8.0_504` e `java 1.8.0_504`), sobre a versão final de [`src/Main.java`](../src/Main.java): remoção temporária de cada aresta e recontagem de componentes conexas com DFS.

Comando utilizado, na pasta `T2`:

```text
python testes/validar.py --java CAMINHO/java --javac CAMINHO/javac
```

Com `-Xlint:all`, o compilador emitiu apenas dois avisos, ambos na linha `adj = new ArrayList[n];` (`rawtypes` e `unchecked`). São avisos comuns ao criar vetores de listas genéricas em Java e não afetam a execução.

## Resultados

| Verificação | Resultado observado |
|---|---|
| Catálogo do grupo, 6 redes (`dados/entrada.txt`) | Saída idêntica a `dados/saida-esperada.txt` |
| Catálogo comentado, 11 casos (`dados/casos-de-teste.txt`) | 11 de 11 idênticos à saída esperada |
| Amostra oficial UVA, 2 redes | Saída idêntica à esperada |
| Entrada vazia | Nenhuma saída |
| 1.100 grafos simples rotulados, 0 a 5 vértices | Concordância com o oráculo |
| 300 grafos aleatórios, 6 a 16 vértices, semente 796 | Concordância com o oráculo |

Os testes embaralham linhas e vizinhos. A comparação verifica o texto completo, inclusive a linha em branco de cada caso. Os casos exaustivos incluem vértices isolados, redes desconexas, ciclos e grafos completos. Vários casos são colocados em uma mesma execução, verificando a reinicialização do estado entre casos.

O oráculo do [script](validar.py) remove cada aresta e conta componentes. Ele foi escrito em Python, de forma independente do código Java, e serve apenas aos testes. Python é ferramenta de teste; a solução do problema é em Java.

## Limitações conhecidas da versão final

Dois testes do script anterior foram removidos, porque validavam a versão com `disc`/`low` e pilha explícita, substituída nesta versão:

| Teste removido | Comportamento observado na versão final |
|---|---|
| Arestas paralelas `0–1` duplicada e `1–2` | Responde `0 - 1` e `1 - 2`. Cada par é guardado uma única vez, e ignorar o par remove as duas ocorrências. O modelo do problema adotado nos marcos é de grafo simples. |
| Caminho com 100.000 vértices | `StackOverflowError` na DFS recursiva. Além disso, o custo O(E·(V+E)) seria alto para esse tamanho. |

Essas limitações não impediram o Accepted da versão final no UVA 796 (ver [README](../README.md#resultado-no-juiz)).

## Registro anterior

O registro de 07/10/2026 (commit `04f7a3e`) referia-se à versão com `disc`/`low` e pilha explícita, executada no OpenJDK 25.0.1 com `--release 8`. Essa versão foi substituída pela estratégia atual e está preservada no histórico do Git.

Este registro não é evidência de Accepted.
