import java.util.*;

public class Main {

    static ArrayList<Integer>[] adj;
    static boolean[] marked;
    static int count;

    static void dfs(int v, int a, int b) {
        marked[v] = true;

        for (int w : adj[v]) {

            if ((v == a && w == b) ||
                (v == b && w == a)) {
                continue;
            }

            if (!marked[w]) {
                dfs(w, a, b);
            }
        }
    }

    static int contarComponentes(int n, int a, int b) {
        marked = new boolean[n];
        count = 0;

        for (int v = 0; v < n; v++) {
            if (!marked[v]) {
                dfs(v, a, b);
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int n = sc.nextInt();

            adj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }

            TreeSet<String> arestas = new TreeSet<>(
                (x, y) -> {
                    String[] p = x.split(" ");
                    String[] q = y.split(" ");
                    int a = Integer.parseInt(p[0]);
                    int b = Integer.parseInt(q[0]);
                    if (a != b) return Integer.compare(a, b);
                    return Integer.compare(
                        Integer.parseInt(p[1]),
                        Integer.parseInt(q[1])
                    );
                }
            );

            for (int i = 0; i < n; i++) {
                int v = sc.nextInt();
                String grau = sc.next();
                int k = Integer.parseInt(
                    grau.replace("(", "").replace(")", "")
                );

                for (int j = 0; j < k; j++) {
                    int w = sc.nextInt();
                    adj[v].add(w);

                    int a = Math.min(v, w);
                    int b = Math.max(v, w);
                    arestas.add(a + " " + b);
                }
            }

            int original = contarComponentes(n, -1, -1);
            ArrayList<String> pontes = new ArrayList<>();

            for (String aresta : arestas) {
                String[] p = aresta.split(" ");
                int a = Integer.parseInt(p[0]);
                int b = Integer.parseInt(p[1]);

                int depois = contarComponentes(n, a, b);

                if (depois > original) {
                    pontes.add(a + " - " + b);
                }
            }

            System.out.println(pontes.size() + " critical links");

            for (String ponte : pontes) {
                System.out.println(ponte);
            }

            System.out.println();
        }

        sc.close();
    }
}
