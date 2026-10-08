# Marco 1 — Modelagem

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Problema

O problema escolhido foi o **Critical Links**, da plataforma UVA (problema 796).

Existe uma rede formada por `n` servidores ligados por conexões. Uma conexão é **crítica** quando, ao ser removida, faz com que dois servidores que antes conseguiam se comunicar deixem de conseguir.

O objetivo é identificar todas as conexões críticas da rede.

## Entrada

A entrada contém vários casos de teste e termina no fim do arquivo (EOF).

Cada caso começa com uma linha contendo:

- `n`: número de servidores.

As próximas `n` linhas têm o formato:

```text
u (k) v1 v2 ... vk
```

onde:

- `u` é o número do servidor;
- `k` é a quantidade de conexões de `u`;
- `v1 ... vk` são os servidores conectados a `u`.

Os servidores são numerados de `0` até `n - 1`. Cada conexão aparece duas vezes na entrada: na linha de cada uma das suas extremidades.

## Saída

Para cada caso de teste, deve ser exibido:

- a quantidade de conexões críticas, no formato `X critical links`;
- uma linha `a - b` para cada conexão crítica, com `a < b`;
- as conexões em ordem crescente, primeiro pelo vértice `a` e depois pelo vértice `b`;
- uma linha em branco ao final do caso.

## Modelagem como grafo

A rede foi modelada como um grafo:

- **Vértices:** representam os servidores;
- **Arestas:** representam as conexões entre servidores;
- **Conexão crítica:** representa uma **ponte** do grafo.

Uma **ponte** é uma aresta cuja remoção aumenta o número de componentes conexas do grafo.

O grafo é:

- **não direcionado**, porque uma conexão permite comunicação nos dois sentidos;
- **não ponderado**, porque as conexões não possuem custo;
- **possivelmente desconexo**, porque a rede pode conter servidores ou grupos isolados.

## Instância utilizada

Nos marcos foi utilizada a seguinte instância com 5 servidores:

```text
5
0 (3) 1 2 3
1 (2) 0 2
2 (2) 0 1
3 (2) 0 4
4 (1) 3
```

Conexões da instância:

```text
0 - 1
0 - 2
0 - 3
1 - 2
3 - 4
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

As conexões `0 - 1`, `0 - 2` e `1 - 2` formam um ciclo. Por isso, nenhuma delas é crítica: se uma for removida, os servidores continuam ligados pelas outras duas.

Já as conexões `0 - 3` e `3 - 4` não fazem parte de nenhum ciclo. Se `0 - 3` for removida, os servidores `3` e `4` ficam separados dos demais. Se `3 - 4` for removida, o servidor `4` fica isolado.

Saída esperada para essa instância:

```text
2 critical links
0 - 3
3 - 4

```

Essa instância está registrada como **CASO 9** em [`../dados/casos-de-teste.txt`](../dados/casos-de-teste.txt).
