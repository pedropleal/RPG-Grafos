import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * UVA 796 - Critical Links.
 * Referencias conceituais da disciplina: Graph, DepthFirstPaths e CC (algs4).
 * Adaptacao propria em arquivo unico, sem dependencia da biblioteca algs4.
 * A DFS usa uma pilha explicita para simular as chamadas e seus retornos.
 */
public class Main {
    private static final class Edge {
        final int to;
        final int id;

        Edge(int to, int id) {
            this.to = to;
            this.id = id;
        }
    }

    // Papel de Graph: listas de adjacencia, com uma identidade por aresta.
    private static final class Graph {
        final List<List<Edge>> adj;
        private int edges;

        Graph(int vertices) {
            adj = new ArrayList<>(vertices);
            for (int v = 0; v < vertices; v++) {
                adj.add(new ArrayList<Edge>());
            }
        }

        void addEdge(int u, int v) {
            adj.get(u).add(new Edge(v, edges));
            adj.get(v).add(new Edge(u, edges));
            edges++;
        }
    }

    // Adapta a exploracao de DepthFirstPaths e a cobertura de componentes de CC.
    private static final class BridgeFinder {
        final List<int[]> bridges = new ArrayList<>();
        final boolean[] marked;
        final int[] parent;
        final int[] disc;
        final int[] low;
        private int time;

        BridgeFinder(Graph graph) {
            int n = graph.adj.size();
            marked = new boolean[n];
            parent = new int[n];
            disc = new int[n];
            low = new int[n];
            Arrays.fill(parent, -1);

            int[] parentEdge = new int[n];
            int[] nextNeighbor = new int[n];
            int[] stack = new int[n];
            Arrays.fill(parentEdge, -1);

            for (int root = 0; root < n; root++) {
                if (marked[root]) {
                    continue;
                }
                int top = 0;
                stack[top] = root;
                discover(root);

                while (top >= 0) {
                    int v = stack[top];
                    if (nextNeighbor[v] < graph.adj.get(v).size()) {
                        Edge edge = graph.adj.get(v).get(nextNeighbor[v]++);
                        if (edge.id == parentEdge[v]) {
                            continue;
                        }
                        int w = edge.to;
                        if (!marked[w]) {
                            parent[w] = v;
                            parentEdge[w] = edge.id;
                            discover(w);
                            stack[++top] = w;
                        } else {
                            low[v] = Math.min(low[v], disc[w]);
                        }
                    } else {
                        // Equivale ao retorno de dfs(v): todos os vizinhos vistos.
                        top--;
                        int p = parent[v];
                        if (p != -1) {
                            low[p] = Math.min(low[p], low[v]);
                            if (low[v] > disc[p]) {
                                bridges.add(new int[] {Math.min(p, v), Math.max(p, v)});
                            }
                        }
                    }
                }
            }

            // Ordenacao padrao apenas organiza a saida; nao encontra pontes.
            bridges.sort((a, b) -> {
                int first = Integer.compare(a[0], b[0]);
                return first != 0 ? first : Integer.compare(a[1], b[1]);
            });
        }

        private void discover(int v) {
            marked[v] = true;
            disc[v] = low[v] = time++;
        }
    }

    // A entrada valida do UVA contem apenas inteiros nao negativos e separadores.
    private static final class FastInput {
        private final BufferedInputStream in = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = in.read();
                if (c == -1) {
                    return -1;
                }
            } while (c < '0' || c > '9');
            int value = 0;
            do {
                value = value * 10 + c - '0';
                c = in.read();
            } while (c >= '0' && c <= '9');
            return value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastInput input = new FastInput();
        int n;
        while ((n = input.nextInt()) != -1) {
            Graph graph = new Graph(n);
            for (int i = 0; i < n; i++) {
                int u = input.nextInt();
                int degree = input.nextInt();
                for (int j = 0; j < degree; j++) {
                    int v = input.nextInt();
                    // A entrada simetrica lista a mesma conexao nos dois extremos.
                    if (u < v) {
                        graph.addEdge(u, v);
                    }
                }
            }
            BridgeFinder finder = new BridgeFinder(graph);
            StringBuilder output = new StringBuilder();
            output.append(finder.bridges.size()).append(" critical links\n");
            for (int[] bridge : finder.bridges) {
                output.append(bridge[0]).append(" - ").append(bridge[1]).append('\n');
            }
            output.append('\n');
            System.out.print(output);
        }
    }
}
