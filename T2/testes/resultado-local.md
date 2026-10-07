# Validação local do T2

Execução realizada em 07/10/2026 com OpenJDK 25.0.1, compilando com `--release 8`. Foram emitidos somente avisos do compilador sobre a obsolescência das opções source/target 8. A execução ocorreu no JDK 25; não foi executada uma JVM 8.

| Verificação | Resultado observado |
|---|---|
| Catálogo do grupo, 6 redes | Saída idêntica a dados/saida-esperada.txt |
| Amostra oficial UVA, 2 redes | Saída idêntica à esperada |
| Entrada vazia | Nenhuma saída |
| 1.100 grafos simples rotulados, 0 a 5 vértices | Concordância com o oráculo por remoção de arestas |
| 300 grafos aleatórios, 6 a 16 vértices, semente 796 | Concordância com o mesmo oráculo |
| Conexões paralelas 0–1 e conexão simples 1–2 | Somente 1–2 é ponte |
| Caminho com 100.000 vértices | 99.999 pontes ordenadas, sem recursão |

Os testes embaralham linhas e vizinhos. A comparação verifica o texto completo, inclusive a linha em branco de cada caso. Os casos exaustivos incluem vértices isolados, redes desconexas, ciclos e grafos completos. O script também coloca vários casos em uma mesma execução, verificando a reinicialização do estado.

O [script reproduzível](validar.py) usa somente a biblioteca padrão de Python e chama o compilador e a JVM. Python é ferramenta de teste; a solução do problema continua em Java. O oráculo usa contagem de componentes após remover cada aresta, sem reutilizar disc/low.

Com JDK 9+ e Python 3 no PATH, executar na pasta T2: `python testes/validar.py`. Para instalações fora do PATH, informar `--java CAMINHO_JAVA --javac CAMINHO_JAVAC`.

Não houve submissão ao juiz. Este registro não é evidência de Accepted.
