# Marco 1 — Modelagem

**Problema:** UVA 796 — Critical Links

**Integrantes:**

- Pedro Pinheiro Barros Leal
- Emmanuel Rosendo Parente Dias

## Problema

No UVA 796 — Critical Links, servidores são vértices e conexões bidirecionais são arestas. O objetivo é encontrar todas as pontes. Formalmente, uma aresta e é ponte se ω(G − e) > ω(G), onde ω conta componentes conexas.

O grafo é não direcionado e não ponderado. Adota-se a modelagem de grafo simples do exemplo anterior. A rede pode ser desconexa; não existe um par fixo de origem e destino para resolver o problema.

## Entrada e saída

Cada caso começa com n, seguido de n linhas no formato `u (k) v1 ... vk`. Os vértices vão de 0 a n−1, e as linhas podem estar fora de ordem. Ler casos até o fim da entrada; n=0 é uma rede vazia, não um terminador.

A saída informa `B critical links` e os pares `u - v`, com o menor extremo primeiro, em ordem crescente pelo primeiro e depois pelo segundo extremo. Após cada caso, imprimir uma linha em branco.

## Instância preservada

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

A instância é conexa. Remover (0,1) separa {0} de {1,2,3,4}; remover (1,3) separa {0,1} de {2,3,4}. Em ambos os casos, o número de componentes passa de 1 para 2. No triângulo, cada aresta tem um caminho alternativo, portanto sua remoção mantém a conectividade.

Resultado esperado por análise manual:

```text
2 critical links
0 - 1
1 - 3

```

Esse resultado foi confirmado executando o `Main.java` final (CASO 1 de [`../dados/casos-de-teste.txt`](../dados/casos-de-teste.txt)).

## Referências

[Enunciado oficial](https://onlinejudge.org/external/7/796.pdf) e material A4_Conectividade, de Ricardo Carubbi, sobre componentes conexas. O exemplo acima é o exemplo do grupo, não a amostra oficial do juiz.
