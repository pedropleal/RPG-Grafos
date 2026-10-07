"""Valida Main.java com Python padrao. Nao integra a solucao enviada ao UVA."""
import argparse
import itertools
import random
import subprocess
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]


def components(n, edges, removed=None):
    adj = [[] for _ in range(n)]
    for index, (u, v) in enumerate(edges):
        if index != removed:
            adj[u].append(v)
            adj[v].append(u)
    seen = set()
    count = 0
    for root in range(n):
        if root in seen:
            continue
        count += 1
        seen.add(root)
        stack = [root]
        while stack:
            for neighbor in adj[stack.pop()]:
                if neighbor not in seen:
                    seen.add(neighbor)
                    stack.append(neighbor)
    return count


def oracle(n, edges):
    baseline = components(n, edges)
    return sorted((min(u, v), max(u, v)) for i, (u, v) in enumerate(edges)
                  if components(n, edges, i) > baseline)


def encode(n, edges, rng):
    adj = [[] for _ in range(n)]
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)
    vertices = list(range(n))
    rng.shuffle(vertices)
    lines = [str(n)]
    for u in vertices:
        rng.shuffle(adj[u])
        lines.append(f'{u} ({len(adj[u])}) ' + ' '.join(map(str, adj[u])))
    return '\n'.join(lines) + '\n'


def expected(bridges):
    return f'{len(bridges)} critical links\n' + ''.join(
        f'{u} - {v}\n' for u, v in bridges) + '\n'


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--java', default='java')
    parser.add_argument('--javac', default='javac')
    args = parser.parse_args()
    build = ROOT / 'build'
    build.mkdir(exist_ok=True)
    subprocess.run([args.javac, '--release', '8', '-Xlint:all', '-encoding', 'UTF-8',
                    '-d', str(build), str(ROOT / 'src/Main.java')], check=True)

    def check(label, input_text, output_text):
        result = subprocess.run([args.java, '-cp', str(build), 'Main'],
                                input=input_text, capture_output=True, text=True,
                                timeout=60, check=True)
        if result.stdout != output_text:
            (build / 'falha-entrada.txt').write_text(input_text, encoding='utf-8')
            (build / 'falha-saida.txt').write_text(result.stdout, encoding='utf-8')
            raise AssertionError(f'{label}: saida diferente da esperada')
        if result.stderr:
            raise AssertionError(result.stderr)
        print(f'OK: {label}')

    check('catalogo do grupo (6 redes)',
          (ROOT / 'dados/entrada.txt').read_text(encoding='utf-8'),
          (ROOT / 'dados/saida-esperada.txt').read_text(encoding='utf-8'))
    check('amostra oficial UVA (2 redes)',
          '8\n0 (1) 1\n1 (3) 2 0 3\n2 (2) 1 3\n3 (3) 1 2 4\n'
          '4 (1) 3\n7 (1) 6\n6 (1) 7\n5 (0)\n0\n',
          '3 critical links\n0 - 1\n3 - 4\n6 - 7\n\n0 critical links\n\n')
    check('entrada vazia', '', '')
    rng = random.Random(796)
    inputs, outputs = [], []
    exhaustive = 0
    for n in range(6):
        possible = list(itertools.combinations(range(n), 2))
        for mask in range(1 << len(possible)):
            edges = [e for i, e in enumerate(possible) if mask & (1 << i)]
            inputs.append(encode(n, edges, rng))
            outputs.append(expected(oracle(n, edges)))
            exhaustive += 1
    check(f'{exhaustive} grafos simples exaustivos de 0 a 5 vertices',
          '\n'.join(inputs), ''.join(outputs))
    inputs, outputs = [], []
    for _ in range(300):
        n = rng.randint(6, 16)
        density = rng.random()
        edges = [e for e in itertools.combinations(range(n), 2)
                 if rng.random() < density]
        inputs.append(encode(n, edges, rng))
        outputs.append(expected(oracle(n, edges)))
    check('300 grafos aleatorios com semente 796',
          ''.join(inputs), ''.join(outputs))
    check('arestas paralelas: identidade da aresta-pai',
          '3\n0 (2) 1 1\n1 (3) 0 0 2\n2 (1) 1\n',
          '1 critical links\n1 - 2\n\n')
    n = 100000
    edges = [(v, v + 1) for v in range(n - 1)]
    check('caminho com 100000 vertices, sem recursao',
          encode(n, edges, rng), expected(edges))
    print('Todas as verificacoes locais passaram. Isso nao equivale a Accepted.')


if __name__ == '__main__':
    main()
