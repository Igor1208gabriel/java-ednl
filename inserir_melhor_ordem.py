def gerarSequencia(comeco, fim):
    if comeco > fim:
        return []
    meio = (comeco + fim) // 2
    return [meio] + gerarSequencia(comeco, meio - 1) + gerarSequencia(meio + 1, fim)

comeco, fim = map(int,input().split())
sequencia = gerarSequencia(comeco,fim)
print(', '.join(map(str, sequencia)))
