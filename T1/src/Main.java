import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Integer>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        boolean[] marked = new boolean[n + 1];
        int[] edgeTo = new int[n + 1];

        Queue<Integer> queue = new ArrayDeque<>();

        marked[1] = true;
        queue.add(1);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            if (v == n) {
                break;
            }

            for (int w : graph[v]) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.add(w);
                }
            }
        }

        if (!marked[n]) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        List<Integer> path = new ArrayList<>();

        int current = n;

        while (current != 1) {
            path.add(current);
            current = edgeTo[current];
        }

        path.add(1);

        Collections.reverse(path);

        System.out.println(path.size());

        for (int computer : path) {
            System.out.print(computer + " ");
        }

        System.out.println();
    }
}